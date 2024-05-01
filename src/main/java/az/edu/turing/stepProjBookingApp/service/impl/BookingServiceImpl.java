package az.edu.turing.stepProjBookingApp.service.impl;

import az.edu.turing.stepProjBookingApp.dao.BookingDao;
import az.edu.turing.stepProjBookingApp.model.dto.BookingDto;
import az.edu.turing.stepProjBookingApp.service.BookingService;

import java.util.Collection;

public class BookingServiceImpl implements BookingService {
    private BookingDao bookingDao;

    public BookingServiceImpl(BookingDao bookingDao) {
        this.bookingDao = bookingDao;
    }

    @Override
    public boolean bookAReservation(String firstName, String secondName, long flightId) {
        Collection<BookingDto> newBookingList = bookingDao.getAll();
        newBookingList.add(new BookingDto(firstName, secondName, flightId));
        return bookingDao.save(newBookingList.stream().toList());
        //todo  update
    }

    @Override
    public boolean cancelAReservation(long id) {
        Collection<BookingDto> allReservations = bookingDao.getAll();
        allReservations.removeIf(bookingDto -> bookingDto.getFlightId() == id);
        return bookingDao.save(allReservations.stream().toList());
    }

    @Override
    public Collection<BookingDto> getMyReservations(String firstName, String secondName) {
        Collection<BookingDto> allReservations = bookingDao.getAll();
        Collection<BookingDto> myReservations = allReservations.stream().filter(booking ->
                booking.getFirstName() == firstName && booking.getSecondName() == secondName).toList();
        return myReservations;
    }
}