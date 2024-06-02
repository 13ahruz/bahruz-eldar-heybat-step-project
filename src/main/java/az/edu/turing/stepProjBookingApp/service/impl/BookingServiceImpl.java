package az.edu.turing.stepProjBookingApp.service.impl;

import az.edu.turing.stepProjBookingApp.dao.BookingDao;
import az.edu.turing.stepProjBookingApp.dao.FlightDao;
import az.edu.turing.stepProjBookingApp.dao.impl.FlightPostgresDao;
import az.edu.turing.stepProjBookingApp.exception.NoEnoughSeatsException;
import az.edu.turing.stepProjBookingApp.exception.NoSuchReservationException;
import az.edu.turing.stepProjBookingApp.exception.NotAValidFlightException;
import az.edu.turing.stepProjBookingApp.model.entity.BookingEntity;
import az.edu.turing.stepProjBookingApp.model.entity.FlightEntity;
import az.edu.turing.stepProjBookingApp.service.BookingService;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class BookingServiceImpl implements BookingService {
    private BookingDao bookingDao;
    private FlightDao flightDao;

    public BookingServiceImpl(BookingDao bookingDao, FlightDao flightDao) {
        this.bookingDao = bookingDao;
        this.flightDao = flightDao;
    }

    @Override
    public boolean bookAReservation(String[] passengers, long flightId) throws NotAValidFlightException, NoEnoughSeatsException {
        List<BookingEntity> list = new ArrayList<>();
        List<FlightEntity> flightsList = flightDao.getAll();
        int amount = passengers.length;
        Optional<FlightEntity> optionalFlight = flightsList.stream()
                .filter(flightEntity -> flightEntity.getFlightId() == flightId)
                .findFirst();
        if (optionalFlight.isEmpty()) {
            throw new NotAValidFlightException("It is not a valid flight!");
        }
        FlightEntity flight = optionalFlight.get();
        int seats = flight.getSeats();
        if (amount > seats) {
            throw new NoEnoughSeatsException("No enough available seats!");
        }

        seats -= amount;
        flight.setSeats(seats);
            long currentBookingId = -1;
            BookingEntity currentBooking = null;
        flightDao.update(flightId, -amount);

        BookingEntity bookingEntity = new BookingEntity(passengers, flightId, amount);
        list.add(bookingEntity);
        return bookingDao.save(list);
    }


    @Override
    public void cancelAReservation(long bookingId) throws NoSuchReservationException {
        try{
        BookingEntity reservationEntity = bookingDao.getOneBy(bookingEntity -> bookingEntity.getBookingId() == bookingId).get();
        int amount = reservationEntity.getPassengers().length;
        long flightId = reservationEntity.getFlightId();
        bookingDao.delete(bookingId);
        flightDao.update(flightId, amount);}
        catch (NoSuchReservationException e){
            e.printStackTrace();
        }
    }

    @Override
    public List<BookingEntity> getMyReservations(String passengerName) {
        List<BookingEntity> allReservations = bookingDao.getAll();
        List<BookingEntity> myReservations = allReservations.stream()
                .filter(booking -> Arrays.asList(booking.getPassengers()).contains(passengerName))
                .collect(Collectors.toList());

        return myReservations;
    }

}
