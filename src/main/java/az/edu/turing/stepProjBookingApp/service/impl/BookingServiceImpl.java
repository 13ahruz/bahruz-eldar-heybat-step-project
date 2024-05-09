package az.edu.turing.stepProjBookingApp.service.impl;

import az.edu.turing.stepProjBookingApp.dao.BookingDao;
import az.edu.turing.stepProjBookingApp.model.entity.BookingEntity;
import az.edu.turing.stepProjBookingApp.service.BookingService;

import java.util.List;
import java.util.function.Predicate;

public class BookingServiceImpl implements BookingService {
    private BookingDao bookingDao;

    public BookingServiceImpl(BookingDao bookingDao) {
        this.bookingDao = bookingDao;
    }

    @Override
    public boolean bookAReservation(String firstName, String secondName, long flightId) {
        List<BookingEntity> list = bookingDao.getAll();
        BookingEntity bookingEntity = new BookingEntity(firstName, secondName, flightId);
        list.add(bookingEntity);
        return bookingDao.save(list);
    }

    @Override
    public boolean cancelAReservation(long id) {
        List<BookingEntity> allReservation = bookingDao.getAll();
        Predicate<BookingEntity> removingPredicate = bookingEntity -> bookingEntity.getFlightId() == id;
        allReservation.remove(removingPredicate);
        return bookingDao.save(allReservation);
    }

    @Override
    public List<BookingEntity> getMyReservations(String firstName, String secondName) {
        List<BookingEntity> allReservation = bookingDao.getAll();
        return allReservation.stream().filter(bookingEntity -> bookingEntity.getFirstName().equals(firstName) && bookingEntity.getSecondName().equals(secondName)).toList();
    }
}