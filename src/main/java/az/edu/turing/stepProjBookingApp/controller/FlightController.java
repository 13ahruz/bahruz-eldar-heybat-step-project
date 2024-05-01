package az.edu.turing.stepProjBookingApp.controller;

import az.edu.turing.stepProjBookingApp.model.dto.FlightDto;
import az.edu.turing.stepProjBookingApp.model.entity.FlightEntity;
import az.edu.turing.stepProjBookingApp.service.FlightService;

import java.util.List;
import java.util.Optional;

public class FlightController {
    private final FlightService flightService;
    public FlightController(FlightService flightService) {
        this.flightService = flightService;
    }
    public List<FlightDto> onlineBoard() {
        return flightService.getAllFlights();
    }
    public Optional<FlightDto> getFlightById(long id) {
        return flightService.getFlightById(id);
    }
    public boolean createFlight (FlightEntity flightEntity){
        return flightService.createFlight(flightEntity);
    }
}
