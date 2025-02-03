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
    private final RoomRepository roomRepository=mock(RoomRepository.class);//sim för åtkomst för databas för rum;
    private final TimeProvider timeProvider= mock(TimeProvider.class);//simulation för tid
    private final NotificationService notificationService= mock(NotificationService.class); //sim för notifikationer;
    private Room mockRoom;
    private final LocalDateTime mockTime = LocalDateTime.of(2025,1,1,12,0);

    @BeforeEach
    void setUp() {
        //sätter upp mock dependencies före varje test för att börja på en "clean slate".
        bookingSystem = new BookingSystem(timeProvider, roomRepository, notificationService); //lägger in mocks i bookingsystem
        //satte mock tiden till för att använda genom alla tester:
        when(timeProvider.getCurrentTime()).thenReturn(LocalDateTime.of(2025,1,1,12,0));
        //gör ett mock rum
        mockRoom = mock(Room.class);
        when(mockRoom.getId()).thenReturn("room1"); //fejk rum för repo
        when(roomRepository.findById("room1")).thenReturn(Optional.of(mockRoom));
    }

    @Test
    void bookRoomSuccessTest() throws NotificationException{ //test simulation för att boka ledigt rum
        LocalDateTime startTime = LocalDateTime.of(2025,1,1,14,0);
        LocalDateTime endTime = LocalDateTime.of(2025,1,1,15,0);

        //mockar tillgängligt rum
        when(mockRoom.isAvailable(startTime, endTime)).thenReturn(true);//mockar/fejkar ett svar från is available så att den returnerar true oavsett.
        //"bokar" rum
        boolean result = bookingSystem.bookRoom("room1", startTime, endTime);
        assertTrue(result);

        //verifying att bokningen är tillagd, verifierar att nedanstående har körts
        verify(mockRoom).addBooking(any(Booking.class));//kallat på addbooking på fejk rummet
        verify(roomRepository).save(mockRoom);//rummet sparats i repot
        verify(notificationService).sendBookingConfirmation(any(Booking.class));//skickas notifikation
    }

    @Test
    void bookRoomFailureTest(){//test för när rummet inte är tillgänglig, bokningen ska misslyckats
        LocalDateTime startTime = LocalDateTime.of(2025,1,1,14,0);
        LocalDateTime endTime = LocalDateTime.of(2025,1,1,15,0);
        when(mockRoom.isAvailable(startTime, endTime)).thenReturn(false);//säger att rummet inte är tillgänglig
        boolean result = bookingSystem.bookRoom("room1", startTime, endTime);
        assertFalse(result);//kollar resultatet av bokningen och fastställer att den misslyckats
        verify(mockRoom, never()).addBooking(any(Booking.class));//ser till att add booking inte ska bli kallad på, så att det inte blir tillagd eller sparade
        verify(roomRepository, never()).save(mockRoom);//ser till att save inte ska bli kallad på
    }

    @Test
    void bookingInThePastWillThrowExceptionTest(){ //förhindrar att man inte kan boka ett rum bakåt i tiden
        LocalDateTime startTime = LocalDateTime.of(1970,1,1,14,0);
        LocalDateTime endTime = LocalDateTime.of(1970,1,1,15,0);
        assertThatThrownBy(() -> bookingSystem.bookRoom("room1", startTime, endTime))//för bättre exception validation
                .isInstanceOf(IllegalArgumentException.class)//skickar meddelandet när det blir en dålig input :
                .hasMessageContaining("Kan inte boka tid i dåtid");
    }

    @Test
    void bookRoomWithNullParaThrowExceptionTest(){ //förhindrar när det är null i rumID och tid.
        assertThatThrownBy(() -> bookingSystem.bookRoom(null, null, null))//koden ska throw an exception när...
                .isInstanceOf(IllegalArgumentException.class)//ger meddelandet nedan vid felaktig inmatning
                .hasMessageContaining("Bokning kräver giltiga start- och sluttider samt rum-id");
    }

    @Test
    void cancelBookingSuccessfullyTest() throws NotificationException{//avbryter en bokning
        Booking booking = mock(Booking.class);
        when(booking.getId()).thenReturn("booking1");
        when(booking.getStartTime()).thenReturn(mockTime);
        when(mockRoom.hasBooking("booking1")).thenReturn(true);
        when(mockRoom.getBooking("booking1")).thenReturn(booking);
        when(roomRepository.findAll()).thenReturn(List.of(mockRoom));

        boolean result = bookingSystem.cancelBooking("booking1");
        assertTrue(result);//avbryter bokning
        //verifiering av borttagning av bokning
        verify(mockRoom).removeBooking("booking1");//tar bort bokningen
        verify(roomRepository).save(mockRoom);//sparar rummet i repo
        verify(notificationService).sendCancellationConfirmation(booking);//skickar notis för avbruten bokning
    }

    @Test
    void cancelBookingFailureWhenNonExistentBookingTest(){ //kan ej avboka en icke-existerande bokning(bookingId)
        when(roomRepository.findAll()).thenReturn(List.of(mockRoom));
        when(mockRoom.hasBooking("booking1")).thenReturn(false);

        boolean result = bookingSystem.cancelBooking("booking1");
        assertFalse(result);
        verify(mockRoom,never()).removeBooking(anyString());//ser till att removebooking inte blir kallad på
        verify(roomRepository, never()).save(mockRoom);//save() blir ej kallad på.
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
    void notCancellingNonExistentBookingTest(){
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
    void bookRoomWithEndTimeBeforeStartTimeThrowsExceptTest() { //ser till att bokning med en endtime som går före starttime inte ska gå igenom
        LocalDateTime startTime = mockTime.plusHours(2);
        LocalDateTime endTime = mockTime.plusHours(1);

        assertThatThrownBy(() -> bookingSystem.bookRoom("room1", startTime, endTime))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("Sluttid måste vara efter starttid");
    }

    @ParameterizedTest //kollar tillgängliga rum i dessa parametrar (tillfällen i tid och datum).
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
        assertThat(availableRooms).containsExactly(mockRoom); //verifierar att det bara är tillgängliga rum returned.
    }

    @Test
    void throwExceptionWhenFindingRoomsWithInvalidTimeTest(){//ser till att ogiltig tidsspann kastar en exception
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
    void getAvailableRoomsWithNullTimeThrowsExceptionTest(){ //kanttest för konstiga/ogiltiga input
        assertThatThrownBy(()-> bookingSystem.getAvailableRooms(null,null))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("Måste ange både start- och sluttid");
    }

    @Test
    void bookRoomHandlesNotificationFailuresTest() throws NotificationException{ //test för lyckad bokning fortfarande lyckats även om notifikation misslyckats
        LocalDateTime startTime = mockTime.plusHours(2);
        LocalDateTime endTime = mockTime.plusHours(3);

        when(mockRoom.isAvailable(startTime,endTime)).thenReturn(true);
        doThrow(new NotificationException("Notification failed"))//simulering av notifikation misslyckanden
                .when(notificationService)
                .sendBookingConfirmation(any(Booking.class));

        boolean result = bookingSystem.bookRoom("room1", startTime, endTime);
        assertTrue(result);
        verify(mockRoom).addBooking(any(Booking.class));//verifierar att bokningen fortfarande är sparad
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