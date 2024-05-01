package az.edu.turing.stepProjBookingApp.controller;

import az.edu.turing.stepProjBookingApp.model.dto.FlightDto;
import az.edu.turing.stepProjBookingApp.model.entity.FlightEntity;
import az.edu.turing.stepProjBookingApp.service.FlightService;

import java.util.List;
import java.util.Optional;

public class FlightController {

    public FlightService flightService;

    public List<FlightEntity> onlineBoard() {
        return flightService.getAllFlights();
    }

    public boolean reservation(FlightDto flightDto) {
        return flightService.reservation(flightDto);
    }

    public boolean cancelReservation(long id) {
        return flightService.cancelReservation(id);
    }

    public Optional<FlightEntity> getFlightById (long id){
        return flightService.getFlightById(id);
    }
}
