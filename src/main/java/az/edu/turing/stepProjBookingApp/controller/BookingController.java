package az.edu.turing.stepProjBookingApp.controller;

import az.edu.turing.stepProjBookingApp.exception.NoEnoughSeatsException;
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

    public boolean bookAReservation(String firstName, String secondName, long flightId, int amount) {
        return bookingService.bookAReservation(firstName, secondName, flightId, amount);
    }

    public boolean cancelAReservation(String firstName, String secondName, long id) {
        return bookingService.cancelAReservation(firstName, secondName, id);
    }

    public List<BookingEntity> getMyReservations(String firstName, String secondName) {
        return bookingService.getMyReservations(firstName, secondName);
    }

}
