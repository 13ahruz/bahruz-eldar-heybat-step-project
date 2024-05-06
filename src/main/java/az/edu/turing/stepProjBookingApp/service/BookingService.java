package az.edu.turing.stepProjBookingApp.service;

import az.edu.turing.stepProjBookingApp.model.entity.BookingEntity;

import java.util.List;
import java.util.Optional;

public interface BookingService {
    boolean bookAReservation(String firstName, String secondName, long flightId);

    boolean cancelAReservation(long id);

    Optional<List<BookingEntity>> getMyReservations(String firstName, String secondName);
}
