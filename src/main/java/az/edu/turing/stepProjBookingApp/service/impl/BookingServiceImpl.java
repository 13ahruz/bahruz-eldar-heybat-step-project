package az.edu.turing.stepProjBookingApp.service.impl;

import az.edu.turing.stepProjBookingApp.dao.BookingDao;
import az.edu.turing.stepProjBookingApp.dao.FlightDao;
import az.edu.turing.stepProjBookingApp.exception.NoEnoughSeatsException;
import az.edu.turing.stepProjBookingApp.exception.NoSuchReservationException;
import az.edu.turing.stepProjBookingApp.exception.NotAValidFlightException;
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
    public boolean bookAReservation(String firstName, String secondName, long flightId, int amount) throws NotAValidFlightException, NoEnoughSeatsException {
        List<BookingEntity> list = bookingDao.getAll();
        List<FlightEntity> flightsList = flightDao.getAll();
        if (flightsList.stream().noneMatch(flightEntity -> flightEntity.getFlightId() == flightId)) {
            throw new NotAValidFlightException("It is not a valid flight!");
        } else {
            int seats = flightsList.stream().filter(flightEntity -> flightEntity.getFlightId() == flightId).findFirst().get().getSeats();
            if (amount > seats) {
                throw new NoEnoughSeatsException("No enough available seats!");
            }
            BookingEntity bookingEntity = new BookingEntity(firstName, secondName, flightId, amount);
            list.add(bookingEntity);
            seats -= amount;
            flightsList.stream().filter(flightEntity -> flightEntity.getFlightId() == flightId).findFirst().get().setSeats(seats);
            flightDao.save(flightsList);
            return bookingDao.save(list);
        }
    }

    @Override
    public boolean cancelAReservation(String firstName, String secondName, long id) throws NoSuchReservationException {
        List<BookingEntity> allReservation = bookingDao.getAll();
        Predicate<BookingEntity> removingPredicate = bookingEntity ->
                bookingEntity.getFlightId() == id &&
                        bookingEntity.getFirstName().equalsIgnoreCase(firstName) &&
                        bookingEntity.getSecondName().equalsIgnoreCase(secondName);
        List<BookingEntity> removedList = allReservation.stream().filter(bookingEntity ->
                bookingEntity.getFlightId() != id ||
                        !bookingEntity.getFirstName().equalsIgnoreCase(firstName) ||
                        !bookingEntity.getSecondName().equalsIgnoreCase(secondName)).toList();

        if (removedList.size() != allReservation.size()) {
            return bookingDao.save(removedList);
        } else {
            throw new NoSuchReservationException("There is no reservation matches your input! ");
        }
    }

    @Override
    public List<BookingEntity> getMyReservations(String firstName, String secondName) throws NoSuchReservationException {
        List<BookingEntity> allReservation = bookingDao.getAll();
        List<BookingEntity> myReservations = allReservation.stream().filter(bookingEntity -> bookingEntity.getFirstName().equalsIgnoreCase(firstName) && bookingEntity.getSecondName().equalsIgnoreCase(secondName)).toList();
        if (!myReservations.isEmpty()) {
            return myReservations;
        } else {
            throw new NoSuchReservationException("You dont have any reservation!");
        }
    }
}