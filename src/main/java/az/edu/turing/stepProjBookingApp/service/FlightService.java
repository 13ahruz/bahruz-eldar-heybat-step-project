package az.edu.turing.stepProjBookingApp.service;

import az.edu.turing.stepProjBookingApp.model.dto.FlightDto;
import az.edu.turing.stepProjBookingApp.model.entity.FlightEntity;

import java.util.Optional;

public interface FlightService {

    Boolean reservation(FlightDto flightDto);

    boolean cancelReservation(long id);

    void showAllFlightTime();

    Optional<FlightEntity> getFlightById(long id);
}
