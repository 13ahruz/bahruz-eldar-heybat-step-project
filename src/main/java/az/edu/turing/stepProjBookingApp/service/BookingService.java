package az.edu.turing.stepProjBookingApp.service;

import az.edu.turing.stepProjBookingApp.model.dto.BookingDto;

import java.util.Collection;

public interface BookingService {
    boolean bookAReservation(String firstName, String secondName, long flightId);

    boolean cancelAReservation(long id);

    Collection<BookingDto> getMyReservations(String firstName, String secondName);
}
