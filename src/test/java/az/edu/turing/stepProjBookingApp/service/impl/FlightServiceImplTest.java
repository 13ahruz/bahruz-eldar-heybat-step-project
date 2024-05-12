package az.edu.turing.stepProjBookingApp.service.impl;

import az.edu.turing.stepProjBookingApp.dao.FlightDao;
import az.edu.turing.stepProjBookingApp.model.dto.FlightDto;
import az.edu.turing.stepProjBookingApp.model.entity.FlightEntity;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class FlightServiceImplTest {

    @Test
    void testGetAllFlights() {
        FlightDao flightDao = mock(FlightDao.class);
        FlightServiceImpl flightService = new FlightServiceImpl(flightDao);

        List<FlightEntity> flightEntities = new ArrayList<>();
        flightEntities.add(new FlightEntity(LocalDateTime.now(), "Kiev", "Baku", 15));
        flightEntities.add(new FlightEntity(LocalDateTime.now(), "Baku", "Istanbul", 20));

        when(flightDao.getAll()).thenReturn(flightEntities);

        List<FlightDto> flightDtos = flightService.getAllFlights();

        assertEquals(flightEntities.size(), flightDtos.size());
    }

    @Test
    void testGetAllByLocationIn24Hours() {
        FlightDao flightDao = mock(FlightDao.class);
        FlightServiceImpl flightService = new FlightServiceImpl(flightDao);

        LocalDateTime now = LocalDateTime.now();
        LocalDateTime twentyFourHoursLater = now.plusHours(24);

        String location = "Kiev";
        FlightEntity within24HoursFlight = new FlightEntity(now.plusHours(1), location, "Baku", 15);
        FlightEntity outside24HoursFlight = new FlightEntity(twentyFourHoursLater.plusHours(1), location, "Baku", 15);
        List<FlightEntity> allFlights = new ArrayList<>();
        allFlights.add(within24HoursFlight);
        allFlights.add(outside24HoursFlight);

        when(flightDao.getAll()).thenReturn(allFlights);

        List<FlightDto> flightsWithin24Hours = flightService.getAllByLocationIn24Hours(location);

        assertEquals(1, flightsWithin24Hours.size());
        assertEquals(within24HoursFlight.getLocation(), flightsWithin24Hours.get(0).getLocation());
    }

    @Test
    void testGetFlightById() {
        FlightDao flightDao = mock(FlightDao.class);
        FlightServiceImpl flightService = new FlightServiceImpl(flightDao);

        long flightId = 1;
        FlightEntity flightEntity = new FlightEntity(LocalDateTime.now(), "Kiev", "Baku", 15);

        when(flightDao.getOneBy(Mockito.any())).thenReturn(java.util.Optional.of(flightEntity));

        java.util.Optional<FlightDto> flightDtoOptional = flightService.getFlightById(flightId);

        assertEquals(flightEntity.getLocation(), flightDtoOptional.get().getLocation());
    }

    @Test
    void testCreateFlight() {
        FlightDao flightDao = mock(FlightDao.class);
        FlightServiceImpl flightService = new FlightServiceImpl(flightDao);

        FlightDto flightDto = new FlightDto(LocalDateTime.now(), "Kiev", "Baku", 15);
        FlightEntity flightEntity = new FlightEntity(flightDto.getDateAndTime(), flightDto.getLocation(), flightDto.getDestination(), flightDto.getSeats());

        when(flightDao.save(Mockito.anyList())).thenReturn(true);

        boolean created = flightService.createFlight(flightDto);

        assertEquals(true, created);
    }
}