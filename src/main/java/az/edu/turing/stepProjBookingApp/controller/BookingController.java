package az.edu.turing.stepProjBookingApp.controller;

import az.edu.turing.stepProjBookingApp.model.dto.BookingDto;
import az.edu.turing.stepProjBookingApp.service.BookingService;

import java.util.Collection;

public class BookingController {
    private BookingService bookingService;

    public boolean bookAReservation(String firstName, String secondName) {
        return bookingService.bookAReservation(firstName, secondName);
    }

    public boolean cancelAReservation(long id) {
        return bookingService.cancelAReservation(id);
    }

    public Collection<BookingDto> getMyReservations(String firstName, String secondName) {
        return bookingService.getMyReservations(firstName, secondName);
    }

}
