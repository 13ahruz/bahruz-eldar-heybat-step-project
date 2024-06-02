package az.edu.turing.stepProjBookingApp.controller;

import az.edu.turing.stepProjBookingApp.model.entity.BookingEntity;
import az.edu.turing.stepProjBookingApp.service.BookingService;

import java.util.List;

public class BookingController {
    private final BookingService bookingService;

    public BookingController(BookingService bookingService) {
        this.bookingService = bookingService;
    }

    public boolean bookAReservation(String [] passangers, long flightId) {
        return bookingService.bookAReservation(passangers, flightId);
    }

    public void cancelAReservation(long bookingId) {
        bookingService.cancelAReservation(bookingId);
    }

    public List<BookingEntity> getMyReservations(String passengerName) {
        return bookingService.getMyReservations(passengerName);
    }
}
