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
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class FlightServiceImplTest {

    public static final BookingDao bookingDao = mock(BookingDao.class);
    public static final BookingService bookingService = new BookingServiceImpl(bookingDao);
    public static final BookingController bookingController = new BookingController(bookingService);
    public static final FlightDao flightDao = mock(FlightDao.class);
    public static final FlightService flightService = new FlightServiceImpl(flightDao);
    public static final FlightController flightController = new FlightController(flightService);


    @Test
    void getAllFlights() {
        LocalDateTime dateTime1 = LocalDateTime.of(2024, 5, 2, 10, 0);
        LocalDateTime dateTime2 = LocalDateTime.of(2024, 5, 3, 12, 0);
        FlightDto flight1 = new FlightDto(dateTime1, "New York", 200);
        FlightDto flight2 = new FlightDto(dateTime2, "Los Angeles", 150);

        List<FlightDto> sampleFlights = Arrays.asList(flight1, flight2);
        when(flightDao.getAll()).thenReturn(sampleFlights);
        List<FlightDto> returnedFlights = flightService.getAllFlights();
        verify(flightDao, times(1)).getAll();
        assertEquals(sampleFlights, returnedFlights);
    }

    @Test
    void getAllByDest() {
        // Mock data
        String destination = "New York";
        LocalDateTime dateTime1 = LocalDateTime.of(2024, 5, 2, 10, 0);
        LocalDateTime dateTime2 = LocalDateTime.of(2024, 5, 3, 12, 0);
        FlightEntity flightEntity1 = new FlightEntity(dateTime1, "New York", 200);
        FlightEntity flightEntity2 = new FlightEntity(dateTime2, "Los Angeles", 150);
        FlightDto flightDto1 = new FlightDto(dateTime1, "New York", 200);
        FlightDto flightDto2 = new FlightDto(dateTime2, "Los Angeles", 150);

        // Mock behavior
        when(flightDao.getAllBy(any())).thenReturn(List.of(flightDto1, flightDto2));

        // Test
        List<FlightDto> flightsByDestination = flightService.getAllByDest(destination);

        // Verify
        assertNotNull(flightsByDestination);
        assertEquals(1, flightsByDestination.size());
        assertEquals(flightDto1, flightsByDestination.get(0));
    }

//    @Test
//    void getFlightById() {
//        // Sample data
//        long flightId = 123456;
//        LocalDateTime dateTime = LocalDateTime.of(2024, 5, 2, 10, 0);
//        FlightDto flightDto = new FlightDto(dateTime, "New York", 200);
//        flightDto.setFlightId(flightId);
//
//        when(flightDao.getOneBy(any())).thenReturn(Optional.of(flightDto));
//
//        Optional<FlightDto> returnedFlight = flightService.getFlightById(flightId);
//
//        verify(flightDao, times(1)).getOneBy(any());
//
//        assertTrue(returnedFlight.isPresent());
//        assertEquals(flightId, returnedFlight.get().getFlightId());
//    }

    @Test
    void createFlight() {
        LocalDateTime dateTime = LocalDateTime.of(2024, 5, 2, 10, 0);
        FlightEntity flightEntity = new FlightEntity(dateTime, "New York", 200);

        // Mock the behavior of flightDao.getAll() to return null initially
        when(flightDao.getAll()).thenReturn(null);

        // Call the method under test
        boolean result = flightService.createFlight(flightEntity);

        // Verify that flightDao.getAll() was called exactly once
        verify(flightDao, times(1)).getAll();

        // Assert that the result is true (indicating successful creation)
        assertTrue(result);
    }
}