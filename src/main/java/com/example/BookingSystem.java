package com.example;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

public class BookingSystem {
    private final TimeProvider timeProvider; //aktuella tiden
    private final RoomRepository roomRepository; // integration med rumsdata, hämta data osv
    private final NotificationService notificationService; //notifs relaterad till bokning

    public BookingSystem(TimeProvider timeProvider,
                         RoomRepository roomRepository,
                         NotificationService notificationService) {
        this.timeProvider = timeProvider;
        this.roomRepository = roomRepository;
        this.notificationService = notificationService;
    }

    public boolean bookRoom(String roomId, LocalDateTime startTime, LocalDateTime endTime) { //rumsbokningarna
        if (startTime == null || endTime == null || roomId == null) {
            throw new IllegalArgumentException("Bokning kräver giltiga start- och sluttider samt rum-id");
        }

        if (startTime.isBefore(timeProvider.getCurrentTime())) {
            throw new IllegalArgumentException("Kan inte boka tid i dåtid");
        }

        if (endTime.isBefore(startTime)) {
            throw new IllegalArgumentException("Sluttid måste vara efter starttid");
        }

        //hämtar rum via roomrepo
        Room room = roomRepository.findById(roomId)
                .orElseThrow(() -> new IllegalArgumentException("Rummet existerar inte"));

        if (!room.isAvailable(startTime, endTime)) { //kollar tillgänglighet, om ej ledigt false.
            return false;
        }

        //skapa, spara bokning i rum
        Booking booking = new Booking(UUID.randomUUID().toString(), roomId, startTime, endTime);
        room.addBooking(booking);
        roomRepository.save(room);

        // skicka notifs
        try {
            notificationService.sendBookingConfirmation(booking);
        } catch (NotificationException e) {
            // Fortsätt även om notifieringen misslyckas
        }

        return true; //lyckad bokning
    }


    public List<Room> getAvailableRooms(LocalDateTime startTime, LocalDateTime endTime) {
        if (startTime == null || endTime == null) { //kollar giltig start o slut
            throw new IllegalArgumentException("Måste ange både start- och sluttid");
        }

        if (endTime.isBefore(startTime)) {
            throw new IllegalArgumentException("Sluttid måste vara efter starttid");
        }

        return roomRepository.findAll().stream() //kollar igenom tillgängliga rum
                .filter(room -> room.isAvailable(startTime, endTime))
                .collect(Collectors.toList());
    }

    public boolean cancelBooking(String bookingId) {
        if (bookingId == null) {
            throw new IllegalArgumentException("Boknings-id kan inte vara null");
        }

        //leta rum med bokningen
        Optional<Room> roomWithBooking = roomRepository.findAll().stream()
                .filter(room -> room.hasBooking(bookingId))
                .findFirst();

        if (roomWithBooking.isEmpty()) {
            return false; //bokning ej hittad
        }

        Room room = roomWithBooking.get();
        Booking booking = room.getBooking(bookingId);

        //kontrollera om bokning inte har startat eller avslutats
        if (booking.getStartTime().isBefore(timeProvider.getCurrentTime())) {
            throw new IllegalStateException("Kan inte avboka påbörjad eller avslutad bokning");
        }

        //ta bort bokning o uppdatera rum
        room.removeBooking(bookingId);
        roomRepository.save(room);

        //skicka notif om avbokning
        try {
            notificationService.sendCancellationConfirmation(booking);
        } catch (NotificationException e) {
            // Fortsätt även om notifieringen misslyckas
        }

        return true;
    }
}

// Stödklasser och interface som behövs:

