package com.example;

import net.bytebuddy.asm.Advice;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class BookingSystemTest {

    private BookingSystem bookingSystem;
    private RoomRepository roomRepository;
    private TimeProvider timeProvider;
    private NotificationService notificationService;
    private Room mockRoom;
    private LocalDateTime mockTime;

    @BeforeEach
    void setUp() {
        timeProvider = mock(TimeProvider.class);
        notificationService = mock(NotificationService.class);
        roomRepository = mock(RoomRepository.class);
        bookingSystem = new BookingSystem(timeProvider, roomRepository, notificationService);
        mockTime = LocalDateTime.of(2025,1,1,12,0);

        when(timeProvider.getCurrentTime()).thenReturn(LocalDateTime.of(2025,1,1,12,0));
        mockRoom = mock(Room.class);
        when(mockRoom.getId()).thenReturn("room1");
        when(roomRepository.findById("room1")).thenReturn(Optional.of(mockRoom));
    }

    @Test
    void bookRoomSuccessTest() throws NotificationException{
        //String roomId = "room 1";
        LocalDateTime startTime = LocalDateTime.of(2025,1,1,14,0);
        LocalDateTime endTime = LocalDateTime.of(2025,1,1,15,0);

        when(mockRoom.isAvailable(startTime, endTime)).thenReturn(true);
        boolean result = bookingSystem.bookRoom("room1", startTime, endTime);
        assertTrue(result);

        verify(mockRoom).addBooking(any(Booking.class));
        verify(roomRepository).save(mockRoom);
        verify(notificationService).sendBookingConfirmation(any(Booking.class));
    }

    @Test
    void bookRoomFailureTest() throws NotificationException{
        LocalDateTime startTime = LocalDateTime.of(2025,1,1,14,0);
        LocalDateTime endTime = LocalDateTime.of(2025,1,1,15,0);
        when(mockRoom.isAvailable(startTime, endTime)).thenReturn(false);
        boolean result = bookingSystem.bookRoom("room1", startTime, endTime);
        assertFalse(result);
        verify(mockRoom, never()).addBooking(any(Booking.class));
        verify(roomRepository, never()).save(mockRoom);
    }

    @Test
    void bookingInThePastWillThrowExceptionTest() throws NotificationException{
        LocalDateTime startTime = LocalDateTime.of(1970,1,1,14,0);
        LocalDateTime endTime = LocalDateTime.of(1970,1,1,15,0);
        assertThatThrownBy(() -> bookingSystem.bookRoom("room1", startTime, endTime))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("Kan inte boka tid i dåtid");
        // assertThrows(NotificationException.class, () -> bookingSystem.bookRoom("room1", startTime, endTime));
    }

    @Test
    void bookRoomWithNullParaThrowExceptionTest(){
        assertThatThrownBy(() -> bookingSystem.bookRoom(null, null, null))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("Bokning kräver giltiga start- och sluttider samt rum-id");
    }

    @Test
    void cancelBookingSuccessfullyTest() throws NotificationException{
        Booking booking = mock(Booking.class);
        when(booking.getId()).thenReturn("booking1");
        when(booking.getStartTime()).thenReturn(mockTime);
        when(mockRoom.hasBooking("booking1")).thenReturn(true);
        when(mockRoom.getBooking("booking1")).thenReturn(booking);
        when(roomRepository.findAll()).thenReturn(List.of(mockRoom));
        boolean result = bookingSystem.cancelBooking("booking1");
        assertTrue(result);
        verify(mockRoom).removeBooking("booking1");
        verify(roomRepository).save(mockRoom);
        verify(notificationService).sendCancellationConfirmation(booking);
    }

    @Test
    void cancelBookingFailureWhenNonExistentBookingTest() throws NotificationException{
        when(roomRepository.findAll()).thenReturn(List.of(mockRoom));
        when(mockRoom.hasBooking("booking1")).thenReturn(false);

        boolean result = bookingSystem.cancelBooking("booking1");
        assertFalse(result);
        // verify(mockRoom).removeBooking("booking1");
        verify(mockRoom,never()).removeBooking(anyString());
        verify(roomRepository, never()).save(mockRoom);
    }

    @Test
    void notCancelStartedBookingTest() throws NotificationException{
        Booking booking = mock(Booking.class);
        when(booking.getId()).thenReturn("booking1");
        when(booking.getStartTime()).thenReturn(mockTime.plusDays(1));
        when(mockRoom.hasBooking("booking1")).thenReturn(true);
        when(mockRoom.getBooking("booking1")).thenReturn(booking);
        when(roomRepository.findAll()).thenReturn(List.of(mockRoom));
        boolean result = bookingSystem.cancelBooking("booking1");
        assertTrue(result);
        verify(mockRoom).removeBooking("booking1");
        verify(roomRepository).save(mockRoom);
        verify(notificationService).sendCancellationConfirmation(booking);
    }

    @Test
    void NotCancellingNonExistentBookingTest() throws NotificationException{
        when(roomRepository.findAll()).thenReturn(List.of(mockRoom));
        when(mockRoom.hasBooking("booking1")).thenReturn(false);

        boolean result = bookingSystem.cancelBooking("booking1");
        assertFalse(result);
        verify(mockRoom, never()).removeBooking(anyString());
        verify(roomRepository, never()).save(mockRoom);
    }

    @Test
    void cancelBookingWithNullIdThrowsExceptionTest(){
        assertThatThrownBy(()-> bookingSystem.cancelBooking(null))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("Boknings-id kan inte vara null");
    }

    @Test //kolla titeln
    void cannotCancelStartedBookingTest(){
        Booking booking = mock(Booking.class);
        when(booking.getId()).thenReturn("booking1");
        when(booking.getStartTime()).thenReturn(mockTime.minusHours(1));
        when(mockRoom.hasBooking("booking1")).thenReturn(true);
        when(mockRoom.getBooking("booking1")).thenReturn(booking);
        when(roomRepository.findAll()).thenReturn(List.of(mockRoom));
        assertThatThrownBy(() -> bookingSystem.cancelBooking("booking1"))
                .isInstanceOf(IllegalStateException.class)
                .hasMessageContaining("Kan inte avboka påbörjad eller avslutad bokning");
    }

    @Test
    void bookRoomWithEndTimeBeforeStartTimeThrowsExceptTest() {
        LocalDateTime startTime = mockTime.plusHours(2);
        LocalDateTime endTime = mockTime.plusHours(1);

        assertThatThrownBy(() -> bookingSystem.bookRoom("room1", startTime, endTime))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("Sluttid måste vara efter starttid");
    }

    @ParameterizedTest
    @CsvSource({
            "2025-05-01T10:00,2025-05-01T12:00",
            "2025-06-15T14:00,2025-06-15T16:00"
    })
    void findAvailableRoomsTest(String start, String end){
        LocalDateTime startTime = LocalDateTime.parse(start);
        LocalDateTime endTime = LocalDateTime.parse(end);
        when(roomRepository.findAll()).thenReturn(List.of(mockRoom));
        when(mockRoom.isAvailable(startTime, endTime)).thenReturn(true);
        List<Room> availableRooms = bookingSystem.getAvailableRooms(startTime, endTime);
        assertThat(availableRooms).containsExactly(mockRoom);
    }

    @Test
    void throwExceptionWhenFindingRoomsWithInvalidTimeTest(){
        LocalDateTime startTime = LocalDateTime.of(2025,1,2,12,0);
        LocalDateTime endTime = LocalDateTime.of(2024,1,1,15,0);

        assertThatThrownBy(() -> bookingSystem.getAvailableRooms(startTime, endTime))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("Sluttid måste vara efter starttid");
    }


    @Test
    void getAvailableRoomsTest() {
        Room room1 = mock(Room.class);
        Room room2 = mock(Room.class);
        LocalDateTime startTime = LocalDateTime.of(2025,1,2,14,0);
        LocalDateTime endTime = LocalDateTime.of(2025,1,2,15,0);
        when(roomRepository.findAll()).thenReturn(List.of(room1, room2));
        when(room1.isAvailable(startTime, endTime)).thenReturn(true);
        when(room2.isAvailable(startTime, endTime)).thenReturn(false);

        List<Room> availableRooms = bookingSystem.getAvailableRooms(startTime, endTime);

        assertEquals(1, availableRooms.size());
        assertTrue(availableRooms.contains(room1));
        assertFalse(availableRooms.contains(room2));
    }

    @Test
    void getAllAvailableRoomsTest(){
        Room room1 = mock(Room.class);
        Room room2 = mock(Room.class);
        LocalDateTime startTime = LocalDateTime.of(2025,1,2,14,0);
        LocalDateTime endTime = LocalDateTime.of(2025,1,2,15,0);

        when(roomRepository.findAll()).thenReturn(List.of(room1,room2));
        when(room1.isAvailable(startTime,endTime)).thenReturn(true);
        when(room2.isAvailable(startTime,endTime)).thenReturn(true);

        List<Room> availableRooms = bookingSystem.getAvailableRooms(startTime, endTime);

        assertEquals(2,availableRooms.size());
        assertTrue(availableRooms.contains(room1));
        assertTrue(availableRooms.contains(room2));
    }

    @Test
    void getAvailableRoomsWithNullTimeThrowsExceptionTest(){
        assertThatThrownBy(()-> bookingSystem.getAvailableRooms(null,null))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("Måste ange både start- och sluttid");
    }

    @Test
    void bookRoomHandlesNotificationFailuresTest() throws NotificationException{
        LocalDateTime startTime = mockTime.plusHours(2);
        LocalDateTime endTime = mockTime.plusHours(3);

        when(mockRoom.isAvailable(startTime,endTime)).thenReturn(true);
        doThrow(new NotificationException("Notification failed"))
                .when(notificationService)
                .sendBookingConfirmation(any(Booking.class));

        boolean result = bookingSystem.bookRoom("room1", startTime, endTime);
        assertTrue(result);
        verify(mockRoom).addBooking(any(Booking.class));
        verify(roomRepository).save(mockRoom);
    }

    @Test
    void cancelBookingHandlesNotifFailureTest () throws NotificationException {
        Booking booking = mock(Booking.class);
        when(booking.getId()).thenReturn("booking1");
        when(booking.getStartTime()).thenReturn(mockTime.plusDays(1));
        when(mockRoom.hasBooking("booking1")).thenReturn(true);
        when(mockRoom.getBooking("booking1")).thenReturn(booking);
        when(roomRepository.findAll()).thenReturn(List.of(mockRoom));
        //skapar en misslyckad notifikation
        doThrow(new NotificationException("Notification failed"))
                .when(notificationService).sendCancellationConfirmation(booking);

        boolean result = bookingSystem.cancelBooking("booking1");

        assertTrue(result); //tar bort bokningen
        verify(mockRoom).removeBooking("booking1");
        verify(roomRepository).save(mockRoom);
    }

    @Test
    void bookNonExistentRoomThrowsExceptionTest(){
        LocalDateTime startTime = mockTime.plusHours(1);
        LocalDateTime endTime = mockTime.plusHours(2);

        when(roomRepository.findById("invalidRoom")).thenReturn(Optional.empty());
        assertThatThrownBy(()-> bookingSystem.bookRoom("invalidRoom", startTime, endTime))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("Rummet existerar inte");
    }

    @Test
    void bookRoomWithNullStartTimeThrowsExceptionTest() {
        LocalDateTime endTime = LocalDateTime.of(2025,1,1,15,0);

        assertThatThrownBy(()-> bookingSystem.bookRoom("room1", null, endTime))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("Bokning kräver giltiga start- och sluttider samt rum-id");
    }

    @Test
    void bookRoomWithNullStartAndEndTimeThrowsExcTest(){
        assertThatThrownBy(()->bookingSystem.bookRoom("room1",null,null))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("Bokning kräver giltiga start- och sluttider samt rum-id");
    }
    @Test
    void bookRoomWithNullStartAndEndTimeThrowsExcTest2(){
        LocalDateTime startTime = LocalDateTime.of(2025,1,1,15,0);
        assertThatThrownBy(()->bookingSystem.bookRoom("room1",startTime,null))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("Bokning kräver giltiga start- och sluttider samt rum-id");
    }
    @Test
    void bookRoomWithNullStartAndEndTimeThrowsExcTest3(){
        LocalDateTime startTime = LocalDateTime.of(2025,1,1,14,0);
        LocalDateTime endTime = LocalDateTime.of(2025,1,1,15,0);
        assertThatThrownBy(()->bookingSystem.bookRoom(null,startTime,endTime))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("Bokning kräver giltiga start- och sluttider samt rum-id");
    }

    @Test
    void getAvailableRoomsWithNullStartTimeThrowsExceptionTest(){
        LocalDateTime endTime = LocalDateTime.of(2025,1,1,15,0);

        assertThatThrownBy(()->bookingSystem.getAvailableRooms(null,endTime))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("Måste ange både start- och sluttid");
    }
}