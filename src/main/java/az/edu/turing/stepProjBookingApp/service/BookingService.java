package az.edu.turing.stepProjBookingApp.service;

import az.edu.turing.stepProjBookingApp.model.entity.BookingEntity;

import java.util.List;

public interface BookingService {
    boolean bookAReservation(String firstName, String secondName, long flightId, int amount);

    boolean cancelAReservation(String firstName, String secondName, long id);

    List<BookingEntity> getMyReservations(String firstName, String secondName);
}
