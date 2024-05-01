package az.edu.turing.stepProjBookingApp.service.impl;

import az.edu.turing.stepProjBookingApp.dao.BookingDao;
import az.edu.turing.stepProjBookingApp.model.dto.BookingDto;
import az.edu.turing.stepProjBookingApp.model.entity.BookingEntity;
import az.edu.turing.stepProjBookingApp.service.BookingService;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Collection;

public class BookingServiceImpl implements BookingService {
    private BookingDao bookingDao;
    public BookingServiceImpl(BookingDao bookingDao) {
        this.bookingDao = bookingDao;
    }
    @Override
    public boolean bookAReservation(String firstName, String secondName) {
        Collection<BookingDto> newBookingList = bookingDao.getAll();
        newBookingList.add(new BookingDto(firstName, secondName));
        return bookingDao.save(newBookingList.stream().toList());
        //todo  update  `
    }

    @Override
    public boolean cancelAReservation(long id) {
        Collection<BookingDto> newBookingList = bookingDao.getAll();
        newBookingList.removeIf(bookingDto -> bookingDto.getFlightId() == id);
        return bookingDao.save(newBookingList.stream().toList());
    }

    @Override
    public Collection<BookingDto> showMyReservations(String firstName, String secondName) {
        return bookingDao.getAll();
    }
}
