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
        List <FlightEntity> FlightsById = flightDao.getAll().stream().toList();
        List <FlightDto> FlightsByIdDto = null;
        for (FlightEntity flight : FlightsById) {
            FlightsByIdDto.add(new FlightDto(flight.getDateAndTime(), flight.getDestination(), flight.getSeats(), flight.getFlightId()));
        }
        return FlightsByIdDto;
    }

    @Override
    public List<FlightDto> getAllByDest(String destination) {
        Predicate<FlightEntity> destPredicate = flight -> flight.getDestination().equals(destination);
        List <FlightEntity> FlightsById = flightDao.getAllBy(destPredicate);
        List <FlightDto> FlightsByIdDto = null;
        for (FlightEntity flight : FlightsById) {
            FlightsByIdDto.add(new FlightDto(flight.getDateAndTime(), flight.getDestination(), flight.getSeats(), flight.getFlightId()));
        }
        return FlightsByIdDto;
    }

    @Override
    public FlightDto getFlightById(long id) {
        Predicate<FlightEntity> predicateByName = flight -> flight.getFlightId() == id;
        Optional <FlightEntity> FlightById = flightDao.getOneBy(predicateByName);
        FlightDto FlightByIdDto = new FlightDto(FlightById.get().getDateAndTime(), FlightById.get().getDestination(), FlightById.get().getSeats(), FlightById.get().getFlightId());
        return FlightByIdDto;
    }

    @Override
    public boolean createFlight (FlightDto flightDto){
        FlightEntity flightEntity = new FlightEntity(
                flightDto.getDateAndTime(),
                flightDto.getDestination(),
                flightDto.getSeats()
        );
        if (flightDao.getAll() != null){
            List<FlightEntity> flights = new ArrayList<>(flightDao.getAll().stream().toList());
            flights.add(flightEntity);
            return flightDao.save(flights);
        }
        else {
            List<FlightEntity> flights = new ArrayList<>();
            flights.add(flightEntity);
            return flightDao.save(flights);
        }
    }
}
