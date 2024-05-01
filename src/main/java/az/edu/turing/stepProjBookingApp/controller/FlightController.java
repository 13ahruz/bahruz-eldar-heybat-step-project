package az.edu.turing.stepProjBookingApp.controller;

import az.edu.turing.stepProjBookingApp.model.dto.FlightDto;
import az.edu.turing.stepProjBookingApp.service.FlightService;

import java.util.List;
import java.util.Optional;

public class FlightController {
    private FlightService flightService;
    public List<FlightDto> onlineBoard() {
        return flightService.getAllFlights();
    }
    public Optional<FlightDto> getFlightById(long id) {
        return flightService.getFlightById(id);
    }
}
