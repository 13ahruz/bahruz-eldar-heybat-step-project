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
import java.util.*;
import java.util.function.Predicate;

import static org.junit.jupiter.api.Assertions.*;

class FlightServiceImplTest {

    public static final BookingDao bookingDao = mock(BookingDao.class);
    public static final FlightDao flightDao = mock(FlightDao.class);
    public static final FlightService flightService = new FlightServiceImpl(flightDao);
    public static final FlightController flightController = new FlightController(flightService);
    public static final BookingService bookingService = new BookingServiceImpl(bookingDao, flightDao);
    public static final BookingController bookingController = new BookingController(bookingService);

    @BeforeEach
    void setup() {
        LocalDateTime dateTime1 = LocalDateTime.of(2024, 5, 2, 10, 0);
        LocalDateTime dateTime2 = LocalDateTime.of(2024, 5, 3, 12, 0);
        FlightEntity flight1 = new FlightEntity(dateTime1, "New York", "Baku", 20);
        FlightEntity flight2 = new FlightEntity(dateTime2, "Los Angeles", "Dubai", 13);
        String location = "New York";
        Predicate<FlightEntity> idPredicate = flight -> flight.getFlightId() == flight1.getFlightId();
        Predicate<FlightEntity> locationPredicate = flight -> flight.getDestination().equals(location);
        when(flightDao.getAll()).thenReturn(Arrays.asList(flight1, flight2));
        when(flightDao.getAllBy(locationPredicate)).thenReturn(Arrays.asList(flight1, flight2));
        when(flightDao.getOneBy(idPredicate)).thenReturn(Optional.of(flight1));
        //when(flightDao.getAll()).thenReturn(List.of(flight1));
    }

    @Test
    void testGetAllFlights() {
        List<FlightDto> result = flightService.getAllFlights();
        assertEquals(2, result.size());
    }


    @Test
    void testGetAllByLocation() {
        List<FlightDto> result = flightService.getAllByLocationIn24Hours("New York");
        assertEquals(1, result.size());
    }

    @Test
    void testGetFlightById() {

    }

    @Test
    void testCreateFlight() {
        // Arrange
        LocalDateTime dateTime = LocalDateTime.of(2024, 5, 10, 8, 0);
        String location = "New York";
        String destination = "London";
        int seats = 100;
        FlightDto flightDto = new FlightDto(dateTime, location, destination, seats);

        FlightEntity expectedFlightEntity = new FlightEntity(dateTime, location, destination, seats);

        when(flightDao.save(anyList())).thenReturn(true);

        // Act
        boolean result = flightService.createFlight(flightDto);

        // Assert
        assertTrue(result);
        verify(flightDao, times(1)).save(Collections.singletonList(expectedFlightEntity));
    }
}