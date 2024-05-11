package az.edu.turing.stepProjBookingApp.service.impl;

import az.edu.turing.stepProjBookingApp.dao.BookingDao;
import az.edu.turing.stepProjBookingApp.dao.FlightDao;
import az.edu.turing.stepProjBookingApp.dao.impl.BookingFileDao;
import az.edu.turing.stepProjBookingApp.dao.impl.FlightFileDao;
import az.edu.turing.stepProjBookingApp.exception.NoEnoughSeatsException;
import az.edu.turing.stepProjBookingApp.model.entity.BookingEntity;
import az.edu.turing.stepProjBookingApp.model.entity.FlightEntity;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

class BookingServiceImplTest {
    FlightFileDao flightDao = mock(FlightFileDao.class);
    BookingFileDao bookingDao = mock(BookingFileDao.class);

    @Test
    void bookAReservation() {
        LocalDateTime customDateTime = LocalDateTime.of(2024, 5, 2, 11, 30);
        FlightEntity flight1 = new FlightEntity(customDateTime, "Kiev", "Baku", 15);
        FlightEntity flight2 = new FlightEntity(customDateTime, "Kiev", "Baku", 15);
        BookingEntity bookingEntity = new BookingEntity("Heybat", "Movlamov", 1, 13);
        when(flightDao.getAll()).thenReturn(Arrays.asList(flight1, flight2));
        when(bookingDao.getAll()).thenReturn(List.of(bookingEntity));
        Mockito.doReturn(bookingEntity.getFirstName().equals("Heybat"));

    }

    @Test
    void testBookAReservation_NotEnoughSeats() {
        LocalDateTime customDateTime = LocalDateTime.of(2024, 5, 2, 11, 30);

        BookingDao bookingDao = mock(BookingDao.class);
        FlightDao flightDao = mock(FlightDao.class);
        BookingServiceImpl bookingService = new BookingServiceImpl(bookingDao, flightDao);

        String firstName = "Bahruz";
        String secondName = "Shabili";
        long flightId = 1;
        int amount = 13;

        List<FlightEntity> flightsList = new ArrayList<>();
        FlightEntity flightEntity = new FlightEntity(customDateTime, "Kiev", "Baku", 1); // Assuming there are 1 seats available
        flightsList.add(flightEntity);

        when(flightDao.getAll()).thenReturn(flightsList);

        assertThrows(NoEnoughSeatsException.class, () -> {
            bookingService.bookAReservation(firstName, secondName, flightId, amount);
        });
    }
    @Test
    void testBookAReservation_SaveData() {
        LocalDateTime customDateTime = LocalDateTime.of(2024, 5, 2, 11, 30);
        BookingServiceImpl bookingService = new BookingServiceImpl(bookingDao, flightDao);

        // Önceden belirlenmiş birkaç örnek veri
        String firstName = "John";
        String secondName = "Doe";
        long flightId = 1;
        int amount = 2; // Rezervasyon yapılacak koltuk sayısı

        // FlightEntity listesi oluşturulur ve örnek bir FlightEntity eklenir
        List<FlightEntity> flightsList = new ArrayList<>();
        FlightEntity flightEntity = new FlightEntity(customDateTime, "Kiev", "Baku", 30); // FlightEntity örneği
        flightsList.add(flightEntity);

        // BookingEntity listesi oluşturulur ve örnek bir BookingEntity eklenir
        List<BookingEntity> bookingList = new ArrayList<>();
        BookingEntity bookingEntity = new BookingEntity(firstName, secondName, flightId, amount); // BookingEntity örneği
        bookingList.add(bookingEntity);

        // FlightDao mock'lanır ve save metodu için beklenen davranış belirlenir
        when(flightDao.getAll()).thenReturn(flightsList);
        when(flightDao.save(flightsList)).thenReturn(true);

        // BookingDao mock'lanır ve save metodu için beklenen davranış belirlenir
        when(bookingDao.getAll()).thenReturn(bookingList);
        when(bookingDao.save(bookingList)).thenReturn(true);

        // Metodu çağırarak geri dönüş değerini alırız
        boolean result = bookingService.bookAReservation(firstName, secondName, flightId, amount);

        // Metodun beklenen davranışı gerçekleştirip gerçekleştirmediğini kontrol ederiz
        assertTrue(result); // Geri dönüş değeri true olmalı

        // FlightDao.save() ve BookingDao.save() metodlarının bir kez çağrıldığını kontrol ederiz
        verify(flightDao, times(1)).save(flightsList);
        verify(bookingDao, times(1)).save(bookingList);
    }

    @Test
    void cancelAReservation() {
    }

//    @Test
//    void getMyReservations() {
//        FlightFileDao daoFlight = Mockito.mock(FlightFileDao.class);
//        BookingFileDao daoBooking = Mockito.mock(BookingFileDao.class);
//        BookingServiceImpl service = new BookingServiceImpl(daoBooking, daoFlight);
//        service.getMyReservations("Heybat", "Movlamov");
////        Mockito.verify(daoBooking).getAll().
//    }
}