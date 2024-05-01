package az.edu.turing.stepProjBookingApp.service.impl;

import az.edu.turing.stepProjBookingApp.dao.FlightDao;
import az.edu.turing.stepProjBookingApp.model.dto.FlightDto;
import az.edu.turing.stepProjBookingApp.model.entity.FlightEntity;
import az.edu.turing.stepProjBookingApp.service.FlightService;

import java.util.ArrayList;
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
        Predicate<FlightEntity> destPredicate = flight -> flight.getDestination().equals(destination);
        return flightDao.getAllBy(destPredicate).stream().toList();
    }

    @Override
    public Optional<FlightDto> getFlightById(long id) {
        Predicate<FlightEntity> predicateByName = flight -> flight.getFlightId() == id;
        return flightDao.getOneBy(predicateByName);
    }

    @Override
    public boolean createFlight (FlightEntity flightEntity){
        FlightDto flightDto = new FlightDto(
                flightEntity.getDateAndTime(),
                flightEntity.getDestination(),
                flightEntity.getSeats()
        );
        if (flightDao.getAll() != null){
            List<FlightDto> flights = new java.util.ArrayList<>(flightDao.getAll().stream().toList());
            flights.add(flightDto);
            return flightDao.save(flights);
        }
        else {
            List<FlightDto> flights = new ArrayList<>();
            flights.add(flightDto);
            return flightDao.save(flights);
        }
    }
}
