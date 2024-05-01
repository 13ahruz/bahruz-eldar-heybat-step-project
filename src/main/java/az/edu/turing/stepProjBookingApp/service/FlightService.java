package az.edu.turing.stepProjBookingApp.service;

import az.edu.turing.stepProjBookingApp.model.dto.FlightDto;
import az.edu.turing.stepProjBookingApp.model.entity.FlightEntity;

import java.util.List;
import java.util.Optional;

public interface FlightService {
    List<FlightDto> getAllFlights();

    List<FlightDto> getAllByDest(String destination);

    Optional<FlightDto> getFlightById(long id);
}
