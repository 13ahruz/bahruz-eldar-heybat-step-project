package az.edu.turing.stepProjBookingApp.service;

import az.edu.turing.stepProjBookingApp.model.dto.BookingDto;
import az.edu.turing.stepProjBookingApp.model.dto.FlightDto;

import java.util.Collection;

public interface BookingService {
    boolean bookAReservation (String firstName, String secondName);
    boolean cancelAReservation (long id);
    Collection <BookingDto> showMyReservations (String firstName, String secondName);
}
