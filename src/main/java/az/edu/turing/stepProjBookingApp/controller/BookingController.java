package az.edu.turing.stepProjBookingApp.controller;

import az.edu.turing.stepProjBookingApp.model.dto.BookingDto;
import az.edu.turing.stepProjBookingApp.model.entity.BookingEntity;
import az.edu.turing.stepProjBookingApp.service.BookingService;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

public class BookingController {
    private final BookingService bookingService;

    public BookingController(BookingService bookingService) {
        this.bookingService = bookingService;
    }

    public boolean bookAReservation(String firstName, String secondName, long flightId) {
        return bookingService.bookAReservation(firstName, secondName, flightId);
    }

    public boolean cancelAReservation(long id) {
        return bookingService.cancelAReservation(id);
    }

    public List<BookingEntity> getMyReservations(String firstName, String secondName) {
        return bookingService.getMyReservations(firstName, secondName);
    }

}
