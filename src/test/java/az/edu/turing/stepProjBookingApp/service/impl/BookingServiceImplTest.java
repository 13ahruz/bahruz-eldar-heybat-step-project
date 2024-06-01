//package az.edu.turing.stepProjBookingApp.service.impl;
//
//import az.edu.turing.stepProjBookingApp.dao.BookingDao;
//import az.edu.turing.stepProjBookingApp.dao.FlightDao;
//import az.edu.turing.stepProjBookingApp.dao.impl.BookingFileDao;
//import az.edu.turing.stepProjBookingApp.dao.impl.FlightFileDao;
//import az.edu.turing.stepProjBookingApp.exception.NoEnoughSeatsException;
//import az.edu.turing.stepProjBookingApp.exception.NoSuchReservationException;
//import az.edu.turing.stepProjBookingApp.model.entity.BookingEntity;
//import az.edu.turing.stepProjBookingApp.model.entity.FlightEntity;
//import org.junit.jupiter.api.Test;
//import org.mockito.Mockito;
//
//import java.time.LocalDateTime;
//import java.util.ArrayList;
//import java.util.Arrays;
//import java.util.List;
//
//import static org.junit.jupiter.api.Assertions.*;
//import static org.mockito.Mockito.*;
//
//class BookingServiceImplTest {
//    FlightFileDao flightDao = mock(FlightFileDao.class);
//    BookingFileDao bookingDao = mock(BookingFileDao.class);
//
//    @Test
//    void bookAReservation() {
//        LocalDateTime customDateTime = LocalDateTime.of(2024, 5, 2, 11, 30);
//        FlightEntity flight1 = new FlightEntity(customDateTime, "Kiev", "Baku", 15);
//        FlightEntity flight2 = new FlightEntity(customDateTime, "Kiev", "Baku", 15);
//        BookingEntity bookingEntity = new BookingEntity("Heybat", "Movlamov", 1, 13);
//        when(flightDao.getAll()).thenReturn(Arrays.asList(flight1, flight2));
//        when(bookingDao.getAll()).thenReturn(List.of(bookingEntity));
//        Mockito.doReturn(bookingEntity.getFirstName().equals("Heybat"));
//    }
//
//    @Test
//    void testBookAReservation_NotEnoughSeats() {
//        LocalDateTime customDateTime = LocalDateTime.of(2024, 5, 2, 11, 30);
//
//        BookingDao bookingDao = mock(BookingDao.class);
//        FlightDao flightDao = mock(FlightDao.class);
//        BookingServiceImpl bookingService = new BookingServiceImpl(bookingDao, flightDao);
//
//        String firstName = "Bahruz";
//        String secondName = "Shabili";
//        long flightId = 1;
//        int amount = 13;
//
//        List<FlightEntity> flightsList = new ArrayList<>();
//        FlightEntity flightEntity = new FlightEntity(customDateTime, "Kiev", "Baku", 1); // Assuming there are 1 seats available
//        flightsList.add(flightEntity);
//
//        when(flightDao.getAll()).thenReturn(flightsList);
//
//        assertThrows(NoEnoughSeatsException.class, () -> {
//            bookingService.bookAReservation(firstName, secondName, flightId, amount);
//        });
//    }
//
//    @Test
//    void testBookAReservation_SaveData() {
//        LocalDateTime customDateTime = LocalDateTime.of(2024, 5, 2, 11, 30);
//        BookingServiceImpl bookingService = new BookingServiceImpl(bookingDao, flightDao);
//
//        String firstName = "John";
//        String secondName = "Doe";
//        long flightId = 1;
//        int amount = 2;
//
//        List<FlightEntity> flightsList = new ArrayList<>();
//        FlightEntity flightEntity = new FlightEntity(customDateTime, "Kiev", "Baku", 30); // FlightEntity örneği
//        flightsList.add(flightEntity);
//
//        List<BookingEntity> bookingList = new ArrayList<>();
//        BookingEntity bookingEntity = new BookingEntity(firstName, secondName, flightId, amount); // BookingEntity örneği
//        bookingList.add(bookingEntity);
//
//        when(flightDao.getAll()).thenReturn(flightsList);
//        when(flightDao.save(flightsList)).thenReturn(true);
//
//        when(bookingDao.getAll()).thenReturn(bookingList);
//        when(bookingDao.save(bookingList)).thenReturn(true);
//
//        boolean result = bookingService.bookAReservation(firstName, secondName, flightId, amount);
//
//        assertTrue(result);
//
//        verify(flightDao, times(1)).save(flightsList);
//        verify(bookingDao, times(1)).save(bookingList);
//    }
//
//    @Test
//    void testCancelAReservation_NonExistingReservation() {
//        BookingServiceImpl bookingService = new BookingServiceImpl(bookingDao, flightDao);
//        String firstName = "Heybat";
//        String secondName = "MOvlamov";
//        long id = 1;
//
//        List<BookingEntity> bookingList = new ArrayList<>();
//
//        when(bookingDao.getAll()).thenReturn(bookingList);
//
//        assertThrows(NoSuchReservationException.class, () -> {
//            bookingService.cancelAReservation(firstName, secondName, id);
//        });
//
//        verify(bookingDao, never()).save(anyList());
//    }
//
//    @Test
//    void testCancelAReservation() throws NoSuchReservationException {
//        BookingDao bookingDao = mock(BookingDao.class);
//        FlightDao flightDao = mock(FlightDao.class);
//        BookingServiceImpl bookingService = new BookingServiceImpl(bookingDao, flightDao);
//
//        String firstName = "Heybat";
//        String secondName = "Movlamov";
//        long id = 1;
//
//        List<BookingEntity> bookingList = new ArrayList<>();
//        BookingEntity bookingEntity = new BookingEntity(firstName, secondName, id, 1);
//        bookingList.add(bookingEntity);
//
//        when(bookingDao.getAll()).thenReturn(bookingList);
//        when(bookingDao.save(anyList())).thenReturn(true);
//
//        boolean canceled = bookingService.cancelAReservation(firstName, secondName, id);
//
//        // Assertions
//        assertTrue(canceled);
//        verify(bookingDao, times(1)).getAll();
//        verify(bookingDao, times(1)).save(anyList());
//    }
//
//    @Test
//    void testGetMyReservations_ExistingReservations() throws NoSuchReservationException {
//        BookingServiceImpl bookingService = new BookingServiceImpl(bookingDao, flightDao);
//        String firstName = "Heybat";
//        String secondName = "Movlamov";
//
//        BookingEntity bookingEntity1 = new BookingEntity(firstName, secondName, 1, 1);
//        BookingEntity bookingEntity2 = new BookingEntity(firstName, secondName, 2, 1);
//
//        List<BookingEntity> bookingList = new ArrayList<>();
//        bookingList.add(bookingEntity1);
//        bookingList.add(bookingEntity2);
//
//        when(bookingDao.getAll()).thenReturn(bookingList);
//
//        List<BookingEntity> result = bookingService.getMyReservations(firstName, secondName);
//
//        assertEquals(2, result.size()); // İki rezervasyon olmalı
//
//        verify(bookingDao, times(1)).getAll();
//    }
//
//    @Test
//    void testGetMyReservations_NonExistingReservations() {
//        BookingServiceImpl bookingService = new BookingServiceImpl(bookingDao, flightDao);
//        String firstName = "Heybat";
//        String secondName = "Movlamov";
//
//        List<BookingEntity> bookingList = new ArrayList<>();
//
//        when(bookingDao.getAll()).thenReturn(bookingList);
//
//        assertThrows(NoSuchReservationException.class, () -> {
//            bookingService.getMyReservations(firstName, secondName);
//        });
//    }
//}