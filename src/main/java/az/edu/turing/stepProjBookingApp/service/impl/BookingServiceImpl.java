package az.edu.turing.stepProjBookingApp.service.impl;

import az.edu.turing.stepProjBookingApp.dao.BookingDao;
import az.edu.turing.stepProjBookingApp.dao.FlightDao;
import az.edu.turing.stepProjBookingApp.model.entity.BookingEntity;
import az.edu.turing.stepProjBookingApp.model.entity.FlightEntity;
import az.edu.turing.stepProjBookingApp.service.BookingService;

import java.util.List;
import java.util.function.Predicate;

public class BookingServiceImpl implements BookingService {
    private BookingDao bookingDao;
    private FlightDao flightDao;

    public BookingServiceImpl(BookingDao bookingDao, FlightDao flightDao) {
        this.bookingDao = bookingDao;
        this.flightDao = flightDao;
    }

    @Override
    public boolean bookAReservation(String firstName, String secondName, long flightId, int amount) {
        List<BookingEntity> list = bookingDao.getAll();
        List<FlightEntity> flightsList = flightDao.getAll();
        int seats = flightsList.stream().filter(flightEntity -> flightEntity.getFlightId() == flightId).findFirst().get().getSeats();
        if (amount >= seats){
            BookingEntity bookingEntity = new BookingEntity(firstName, secondName, flightId, amount);
            list.add(bookingEntity);
            seats -= amount;
            flightsList.stream().filter(flightEntity -> flightEntity.getFlightId() == flightId).findFirst().get().setSeats(seats);
            flightDao.save(flightsList);
        }
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