package az.edu.turing.stepProjBookingApp.service.impl;

import az.edu.turing.stepProjBookingApp.dao.BookingDao;
import az.edu.turing.stepProjBookingApp.model.entity.BookingEntity;
import az.edu.turing.stepProjBookingApp.service.BookingService;

import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;

public class BookingServiceImpl implements BookingService {
    private BookingDao bookingDao;

    public BookingServiceImpl(BookingDao bookingDao) {
        this.bookingDao = bookingDao;
    }

    @Override
    public boolean bookAReservation(String firstName, String secondName, long flightId) {
        Optional<List<BookingEntity>> list = bookingDao.getAll();
        BookingEntity bookingEntity = new BookingEntity(firstName, secondName, flightId);
        list.get().add(bookingEntity);
        return bookingDao.save(list.get());
    }

    @Override
    public boolean cancelAReservation(long id) {
        Optional<List<BookingEntity>> allReservation = bookingDao.getAll();
        Predicate<BookingEntity> bookingEntityPredicate = bookingEntity -> bookingEntity.getFlightId() == id;
        allReservation.get().remove(bookingEntityPredicate);
        return bookingDao.save(allReservation.get());
    }

    @Override
    public Optional<List<BookingEntity>> getMyReservations(String firstName, String secondName) {
        Optional<List<BookingEntity>> allReservation = bookingDao.getAll();
        Predicate<List<BookingEntity>> bookingEntityPredicate = bookingEntity -> bookingEntity.getFirst().getFirstName() ==firstName && bookingEntity.getFirst().getSecondName() == secondName ;
        Optional<List<BookingEntity>> myReservations = allReservation.filter(bookingEntityPredicate);
        return myReservations;
    }
}