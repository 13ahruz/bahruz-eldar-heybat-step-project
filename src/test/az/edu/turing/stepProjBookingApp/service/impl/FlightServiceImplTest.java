package az.edu.turing.stepProjBookingApp.service.impl;

import az.edu.turing.stepProjBookingApp.controller.BookingController;
import az.edu.turing.stepProjBookingApp.controller.FlightController;
import az.edu.turing.stepProjBookingApp.dao.BookingDao;
import az.edu.turing.stepProjBookingApp.dao.FlightDao;
import az.edu.turing.stepProjBookingApp.dao.impl.BookingFileDao;
import az.edu.turing.stepProjBookingApp.dao.impl.FlightFileDao;
import az.edu.turing.stepProjBookingApp.model.dto.FlightDto;
import az.edu.turing.stepProjBookingApp.model.entity.FlightEntity;
import az.edu.turing.stepProjBookingApp.service.BookingService;
import az.edu.turing.stepProjBookingApp.service.FlightService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import static org.mockito.Mockito.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;

import static org.junit.jupiter.api.Assertions.*;

class FlightServiceImplTest {

    public static final BookingDao bookingDao = mock(BookingDao.class);
    public static final BookingService bookingService = new BookingServiceImpl(bookingDao);
    public static final BookingController bookingController = new BookingController(bookingService);
    public static final FlightDao flightDao = mock(FlightDao.class);
    public static final FlightService flightService = new FlightServiceImpl(flightDao);
    public static final FlightController flightController = new FlightController(flightService);

    @Test
    void testGetAllFlights() {
        FlightDao flightDao = mock(FlightDao.class);

        LocalDateTime dateTime1 = LocalDateTime.of(2024, 5, 2, 10, 0);
        LocalDateTime dateTime2 = LocalDateTime.of(2024, 5, 3, 12, 0);
        FlightEntity flight1 = new FlightEntity(dateTime1, "New York", 200);
        FlightEntity flight2 = new FlightEntity(dateTime2, "Los Angeles", 150);

        when(flightDao.getAll()).thenReturn(Optional.of(Arrays.asList(flight1, flight2)));
        Optional<List<FlightDto>> result = flightService.getAllFlights();

        List<FlightDto> expectedFlights = Arrays.asList(
                new FlightDto(dateTime1, "New York", 200, flight1.getFlightId()),
                new FlightDto(dateTime2, "Los Angeles", 150, flight2.getFlightId())
        );
        assertEquals(Optional.of(expectedFlights), result);
    }


    @Test
    void testGetAllByDest() {
        FlightDao flightDao = mock(FlightDao.class);

        LocalDateTime dateTime1 = LocalDateTime.of(2024, 5, 2, 10, 0);
        LocalDateTime dateTime2 = LocalDateTime.of(2024, 5, 3, 12, 0);
        FlightEntity flight1 = new FlightEntity(dateTime1, "New York", 200);
        FlightEntity flight2 = new FlightEntity(dateTime2, "Los Angeles", 150);
        FlightEntity flight3 = new FlightEntity(dateTime1, "New York", 220);

        String destination = "New York";
        Predicate<FlightEntity> destPredicate = flight -> flight.getDestination().equals(destination);
        when(flightDao.getAllBy(destPredicate)).thenReturn(Optional.of(Arrays.asList(flight1, flight3)));

        Optional<List<FlightDto>> result = flightService.getAllByDest(destination);

        List<FlightDto> expectedFlights = Arrays.asList(
                new FlightDto(dateTime1, "New York", 200, flight1.getFlightId()),
                new FlightDto(dateTime1, "New York", 220, flight3.getFlightId())
        );
        assertEquals(Optional.of(expectedFlights), result);
    }

    @Test
    void testGetFlightById() {
        FlightDao flightDao = mock(FlightDao.class);

        long id = 1;
        LocalDateTime dateTime = LocalDateTime.of(2024, 5, 2, 10, 0);
        FlightEntity flightEntity = new FlightEntity(dateTime, "New York", 200);

        Predicate<FlightEntity> predicate = flight -> flight.getFlightId() == id;
        when(flightDao.getOneBy(predicate)).thenReturn(Optional.of(flightEntity));

        Optional<FlightDto> result = flightService.getFlightById(id);

        assertTrue(result.isPresent());
        assertEquals(new FlightDto(dateTime, "New York", 200, id), result.get());
    }

    @Test
    void testCreateFlight() {
        FlightDao flightDao = mock(FlightDao.class);

        LocalDateTime dateTime = LocalDateTime.of(2024, 5, 2, 10, 0);
        FlightDto flightDto = new FlightDto(dateTime, "New York", 200, 1); // Assuming flight ID is 1

        when(flightDao.getAll()).thenReturn(Optional.of(new ArrayList<>()));

        boolean result = flightService.createFlight(flightDto);

        assertTrue(result);
        verify(flightDao).getAll();
        verify(flightDao).save(List.of(new FlightEntity(dateTime, "New York", 200)));
    }
}