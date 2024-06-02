package az.edu.turing.stepProjBookingApp.service.impl;

import az.edu.turing.stepProjBookingApp.dao.FlightDao;
import az.edu.turing.stepProjBookingApp.model.dto.FlightDto;
import az.edu.turing.stepProjBookingApp.model.entity.FlightEntity;
import az.edu.turing.stepProjBookingApp.service.FlightService;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class FlightServiceImpl implements FlightService {
    private final FlightDao flightDao;

    public FlightServiceImpl(FlightDao flightDao) {
        this.flightDao = flightDao;
    }

    @Override
    public List<FlightDto> getAllFlights() {
        List<FlightEntity> allFlights = flightDao.getAll();
        if (allFlights != null) {
            return allFlights.stream()
                    .map(flight -> new FlightDto(flight.getDateAndTime(), flight.getLocation(), flight.getDestination(), flight.getSeats(), flight.getFlightId()))
                    .collect(Collectors.toList());
        } else {
            return Collections.emptyList();
        }
    }

    @Override
    public List<FlightDto> getAllByLocationIn24Hours(String location) {
        Predicate<FlightEntity> locationAndDatePredicate = (flight ->
                flight.getLocation().equalsIgnoreCase(location) &&
                        flight.getDateAndTime().isAfter(LocalDateTime.now()) &&
                        flight.getDateAndTime().isBefore(LocalDateTime.now().plusHours(24))
        );

        List<FlightEntity> flights = flightDao.getAll();
        List<FlightEntity> filteredFlights = flights.stream()
                .filter(locationAndDatePredicate)
                .collect(Collectors.toList());

        return filteredFlights.stream()
                .map(flight -> new FlightDto(
                        flight.getDateAndTime(),
                        flight.getLocation(),
                        flight.getDestination(),
                        flight.getSeats(),
                        flight.getFlightId()))
                .collect(Collectors.toList());
    }


    @Override
    public Optional<FlightDto> getFlightById(long id) {
        try {
            Predicate<FlightEntity> predicateById = flight -> flight.getFlightId() == id;
            Optional<FlightEntity> flightById = flightDao.getOneBy(predicateById);
            return flightById.map(flightEntity ->
                    new FlightDto(flightEntity.getDateAndTime(), flightEntity.getLocation(),
                            flightEntity.getDestination(), flightEntity.getSeats(), flightEntity.getFlightId()));
        } catch (Exception e) {
            e.printStackTrace();
            return Optional.empty();
        }
    }

    @Override
    public boolean createFlight(FlightDto flightDto) {
        Predicate<FlightEntity> flightPredicate = flightEntity ->
                flightEntity.getDateAndTime().equals(flightDto.getDateAndTime()) &&
                        flightEntity.getLocation().equals(flightDto.getLocation()) &&
                        flightEntity.getDestination().equals(flightDto.getDestination());

        Optional<FlightEntity> existingFlight = flightDao.getOneBy(flightPredicate);
        if (existingFlight.isPresent()) {
            return false;
        }

        FlightEntity flightEntity = new FlightEntity(
                flightDto.getDateAndTime(),
                flightDto.getLocation(),
                flightDto.getDestination(),
                flightDto.getSeats()
        );
        return flightDao.save(List.of(flightEntity));
    }
}
