package az.edu.turing.stepProjBookingApp.service.impl;

import az.edu.turing.stepProjBookingApp.dao.FlightDao;
import az.edu.turing.stepProjBookingApp.model.dto.FlightDto;
import az.edu.turing.stepProjBookingApp.model.entity.FlightEntity;
import az.edu.turing.stepProjBookingApp.service.FlightService;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;

public class FlightServiceImpl implements FlightService {
    private FlightDao flightDao;

    public FlightServiceImpl(FlightDao flightDao) {
        this.flightDao = flightDao;
    }

    @Override
    public Boolean reservation(FlightDto flightDto) {
        FlightEntity flightEntity = new FlightEntity(
                flightDto.getDate(),
                flightDto.getTime(),
                flightDto.getDestination(),
                flightDto.getSeats());
                flightDao.reservFlight(flightEntity);
        return true;//todo  update  `
    }

    @Override
    public boolean cancelReservation(long id) {
    Predicate<FlightEntity> flightEntityPredicate = flightEntity -> flightEntity.getFlightId()==id;
    flightDao.cancelFlightBy(flightEntityPredicate);
    return true;
    }

    @Override
    public List<FlightEntity> getAllFlights() {
        return flightDao.getAllFlights().stream().toList();
    }

    @Override
    public Optional<FlightEntity> getFlightById(long id) {
        Predicate<FlightEntity> predicateByName = flight -> flight.getFlightId() == id;
        return flightDao.getFlightBy(predicateByName);
    }

}
