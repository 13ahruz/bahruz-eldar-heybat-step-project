package az.edu.turing.stepProjBookingApp.service.impl;

import az.edu.turing.stepProjBookingApp.dao.FlightDao;
import az.edu.turing.stepProjBookingApp.model.dto.FlightDto;
import az.edu.turing.stepProjBookingApp.model.entity.FlightEntity;
import az.edu.turing.stepProjBookingApp.service.FlightService;

import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;

public class FlightServiceImpl implements FlightService {
    private FlightDao flightDao;

    public FlightServiceImpl(FlightDao flightDao) {
        this.flightDao = flightDao;
    }

    @Override
    public List<FlightDto> getAllFlights() {
        return flightDao.getAll().stream().toList();
    }

    @Override
    public List<FlightDto> getAllByDest(String destination) {
        Predicate<FlightEntity> flightEntityPredicate = flight -> flight.getDestination().equals(destination);
        return flightDao.getAllBy(flightEntityPredicate).stream().toList();
    }

    @Override
    public Optional<FlightDto> getFlightById(long id) {
        Predicate<FlightEntity> predicateByName = flight -> flight.getFlightId() == id;
        return flightDao.getOneBy(predicateByName);
    }

}
