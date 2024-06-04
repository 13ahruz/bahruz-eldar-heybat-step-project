package az.edu.turing.stepProjBookingApp.service.impl;

import az.edu.turing.stepProjBookingApp.dao.impl.BookingFileDao;
import az.edu.turing.stepProjBookingApp.dao.impl.FlightFileDao;
import az.edu.turing.stepProjBookingApp.exception.NoSuchReservationException;
import az.edu.turing.stepProjBookingApp.model.entity.BookingEntity;
import az.edu.turing.stepProjBookingApp.model.entity.FlightEntity;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

class BookingServiceImplTest {
    FlightFileDao flightDao = mock(FlightFileDao.class);
    BookingFileDao bookingDao = mock(BookingFileDao.class);

    @Test
    void bookAReservation() {
        LocalDateTime customDateTime = LocalDateTime.of(2024, 5, 2, 11, 30);
        FlightEntity flight = new FlightEntity(customDateTime, "Kiev", "Baku", 15);
        BookingEntity bookingEntity = new BookingEntity(new String[]{"Heybat Movlamov"}, flight.getFlightId(), 1);

        when(flightDao.getAll()).thenReturn(Collections.singletonList(flight));
        when(bookingDao.save(anyList())).thenReturn(true);

        BookingServiceImpl bookingService = new BookingServiceImpl(bookingDao, flightDao);
        boolean result = bookingService.bookAReservation(bookingEntity.getPassengers(), flight.getFlightId());

        assertTrue(result);
        verify(flightDao, times(1)).update(eq(flight.getFlightId()), anyInt());
        verify(bookingDao, times(1)).save(anyList());
    }

    @Test
    void testBookAReservation_SaveData() {
        LocalDateTime customDateTime = LocalDateTime.of(2024, 5, 2, 11, 30);
        FlightEntity flightEntity = new FlightEntity(customDateTime, "Kiev", "Baku", 30);

        when(flightDao.getAll()).thenReturn(Collections.singletonList(flightEntity));
        doNothing().when(flightDao).update(eq(flightEntity.getFlightId()), anyInt());
        when(bookingDao.save(anyList())).thenReturn(true);

        BookingServiceImpl bookingService = new BookingServiceImpl(bookingDao, flightDao);

        boolean result = bookingService.bookAReservation(new String[]{"John Doe"}, flightEntity.getFlightId());

        assertTrue(result);

        verify(flightDao, times(1)).update(eq(flightEntity.getFlightId()), anyInt());
        verify(bookingDao, times(1)).save(anyList());
    }


    @Test
    void testCancelAReservation() throws NoSuchReservationException {
        long bookingId = 1;

        when(bookingDao.getOneBy(any())).thenReturn(Optional.of(new BookingEntity(bookingId, new String[]{"Heybat Movlamov"}, 1)));
        doNothing().when(bookingDao).delete(anyLong());

        BookingServiceImpl bookingService = new BookingServiceImpl(bookingDao, flightDao);

        bookingService.cancelAReservation(bookingId);

        verify(bookingDao, times(1)).delete(eq(bookingId));
        verify(flightDao, times(1)).update(anyLong(), anyInt());
    }

    @Test
    void testGetMyReservations_ExistingReservations() {
        String passengerName = "Heybat Movlamov";
        BookingServiceImpl bookingService = new BookingServiceImpl(bookingDao, flightDao);

        BookingEntity bookingEntity1 = new BookingEntity(new String[]{passengerName}, 1L, 1);
        BookingEntity bookingEntity2 = new BookingEntity(new String[]{passengerName}, 2L, 1);

        when(bookingDao.getAll()).thenReturn(Arrays.asList(bookingEntity1, bookingEntity2));

        List<BookingEntity> result = bookingService.getMyReservations(passengerName);

        assertEquals(2, result.size());

        verify(bookingDao, times(1)).getAll();
    }
}
