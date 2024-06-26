package az.edu.turing.stepProjBookingApp.dao.impl;

import az.edu.turing.stepProjBookingApp.dao.BookingDao;
import az.edu.turing.stepProjBookingApp.model.entity.BookingEntity;

import java.sql.*;
import java.util.*;
import java.util.function.Predicate;

public class BookingPostgresDao extends BookingDao implements JdbcConnect {

    @Override
    public boolean save(List<BookingEntity> bookingEntities) {
        String saveIntoBookings = "INSERT INTO bookings (flight_id) VALUES (?)";
        String saveIntoBookingPassenger = "INSERT INTO booking_passenger (booking_id, passenger_id) VALUES (?, ?)";
        String saveIntoPassengers = "INSERT INTO passengers (name) VALUES (?)";
        Connection conn = null;

        try {
            conn = getConnection();
            conn.setAutoCommit(false);

            try (PreparedStatement statementForBookings = conn.prepareStatement(saveIntoBookings, Statement.RETURN_GENERATED_KEYS);
                 PreparedStatement statementForBookingPassenger = conn.prepareStatement(saveIntoBookingPassenger);
                 PreparedStatement statementForPassengers = conn.prepareStatement(saveIntoPassengers, Statement.RETURN_GENERATED_KEYS)) {

                for (BookingEntity bookingEntity : bookingEntities) {
                    statementForBookings.setLong(1, bookingEntity.getFlightId());
                    statementForBookings.executeUpdate();

                    ResultSet bookingKeys = statementForBookings.getGeneratedKeys();
                    if (bookingKeys.next()) {
                        long bookingId = bookingKeys.getLong(1);

                        for (String passengerName : bookingEntity.getPassengers()) {
                            statementForPassengers.setString(1, passengerName);
                            statementForPassengers.executeUpdate();

                            ResultSet passengerKeys = statementForPassengers.getGeneratedKeys();
                            if (passengerKeys.next()) {
                                long passengerId = passengerKeys.getLong(1);

                                statementForBookingPassenger.setLong(1, bookingId);
                                statementForBookingPassenger.setLong(2, passengerId);
                                statementForBookingPassenger.addBatch();
                            } else {
                                throw new SQLException("Creating passenger failed, no ID obtained.");
                            }
                        }
                    } else {
                        throw new SQLException("Creating booking failed, no ID obtained.");
                    }
                }

                statementForBookingPassenger.executeBatch();
                conn.commit();
                return true;
            } catch (SQLException e) {
                conn.rollback();
                e.printStackTrace();
                return false;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        } finally {
            if (conn != null) {
                try {
                    conn.setAutoCommit(true);
                    conn.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }


    @Override
    public List<BookingEntity> getAll() {
        String getAll = "SELECT bookings.id, bookings.flight_id, passengers.name " +
                "FROM bookings " +
                "JOIN booking_passenger ON bookings.id = booking_passenger.booking_id " +
                "JOIN passengers ON booking_passenger.passenger_id = passengers.id";
        List<BookingEntity> bookingEntities = new ArrayList<>();
        try (Connection conn = getConnection()) {
            PreparedStatement query = conn.prepareStatement(getAll);
            ResultSet resultSet = query.executeQuery();

            Map<Long, BookingEntity> bookingMap = new HashMap<>();
            while (resultSet.next()) {
                long bookingId = resultSet.getLong("id");
                long flightId = resultSet.getLong("flight_id");
                String passengerName = resultSet.getString("name");

                BookingEntity bookingEntity = bookingMap.computeIfAbsent(bookingId, id -> new BookingEntity(id, new String[]{}, flightId));

                List<String> passengers = new ArrayList<>(Arrays.asList(bookingEntity.getPassengers()));
                passengers.add(passengerName);
                bookingEntity.setPassengers(passengers.toArray(new String[0]));

                bookingMap.put(bookingId, bookingEntity);
            }
            bookingEntities.addAll(bookingMap.values());
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return bookingEntities;
    }

    @Override
    public Optional<BookingEntity> getOneBy(Predicate<BookingEntity> predicate) {
        String getOneBySql = "SELECT bookings.id, bookings.flight_id, passengers.name " +
                "FROM bookings " +
                "JOIN booking_passenger ON bookings.id = booking_passenger.booking_id " +
                "JOIN passengers ON booking_passenger.passenger_id = passengers.id";
        try (Connection conn = getConnection()) {
            PreparedStatement statement = conn.prepareStatement(getOneBySql);
            ResultSet resultSet = statement.executeQuery();

            Map<Long, BookingEntity> bookingMap = new HashMap<>();
            long currentBookingId = -1;
            while (resultSet.next()) {
                long bookingId = resultSet.getLong("id");
                long flightId = resultSet.getLong("flight_id");
                String passengerName = resultSet.getString("name");

                BookingEntity currentBooking = bookingMap.computeIfAbsent(bookingId, id -> new BookingEntity(id, new String[]{}, flightId));

                List<String> passengers = new ArrayList<>(Arrays.asList(currentBooking.getPassengers()));
                passengers.add(passengerName);
                currentBooking.setPassengers(passengers.toArray(new String[0]));

                bookingMap.put(bookingId, currentBooking);

                if (bookingId != currentBookingId) {
                    if (currentBooking != null && predicate.test(currentBooking)) {
                        return Optional.of(currentBooking);
                    }
                    currentBookingId = bookingId;
                }
            }
            if (currentBookingId != -1) {
                BookingEntity currentBooking = bookingMap.get(currentBookingId);
                if (currentBooking != null && predicate.test(currentBooking)) {
                    return Optional.of(currentBooking);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }

    @Override
    public List<BookingEntity> getAllBy(Predicate<BookingEntity> predicate) {
        String getAllBySql = "SELECT bookings.id, bookings.flight_id, bookings.amount, passengers.name " +
                "FROM bookings " +
                "JOIN booking_passenger ON bookings.id = booking_passenger.booking_id " +
                "JOIN passengers ON booking_passenger.passenger_id = passengers.id";
        List<BookingEntity> bookingEntities = new ArrayList<>();
        try (Connection conn = getConnection()) {
            PreparedStatement statement = conn.prepareStatement(getAllBySql);
            ResultSet resultSet = statement.executeQuery();

            Map<Long, BookingEntity> bookingMap = new HashMap<>();
            long currentBookingId = -1;
            while (resultSet.next()) {
                long bookingId = resultSet.getLong("id");
                int amount = resultSet.getInt("amount");
                long flightId = resultSet.getLong("flight_id");
                String passengerName = resultSet.getString("name");

                BookingEntity currentBooking = bookingMap.computeIfAbsent(bookingId, id -> new BookingEntity(new String[]{}, flightId, amount));

                List<String> passengers = new ArrayList<>(Arrays.asList(currentBooking.getPassengers()));
                passengers.add(passengerName);
                currentBooking.setPassengers(passengers.toArray(new String[0]));

                bookingMap.put(bookingId, currentBooking);

                if (bookingId != currentBookingId) {
                    if (currentBooking != null && predicate.test(currentBooking)) {
                        bookingEntities.add(currentBooking);
                    }
                    currentBookingId = bookingId;
                }
            }
            if (currentBookingId != -1) {
                BookingEntity currentBooking = bookingMap.get(currentBookingId);
                if (currentBooking != null && predicate.test(currentBooking)) {
                    bookingEntities.add(currentBooking);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return bookingEntities;
    }

    @Override
    public void update(long id, int amount) {
    }

    @Override
    public void delete(long id) {
        try (Connection conn = getConnection()) {
            String deleteBookingPassengerSql = "DELETE FROM booking_passenger WHERE booking_id = ?";
            try (PreparedStatement statement = conn.prepareStatement(deleteBookingPassengerSql)) {
                statement.setLong(1, id);
                statement.executeUpdate();
            }

            String deleteBookingSql = "DELETE FROM bookings WHERE id = ?";
            try (PreparedStatement statement = conn.prepareStatement(deleteBookingSql)) {
                statement.setLong(1, id);
                statement.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    @Override
    public Connection getConnection() {
        return JdbcConnect.super.getConnection();
    }
}
