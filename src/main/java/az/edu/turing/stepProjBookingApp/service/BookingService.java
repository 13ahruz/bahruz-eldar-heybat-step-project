package az.edu.turing.stepProjBookingApp.service;

import az.edu.turing.stepProjBookingApp.model.entity.BookingEntity;

import java.util.List;

public interface BookingService {
    boolean bookAReservation(String [] passengers, long flightId);

    void cancelAReservation(long bookingId);

    List<BookingEntity> getMyReservations(String passengerName);
}
