package az.edu.turing.stepProjBookingApp.dao.impl;

import az.edu.turing.stepProjBookingApp.dao.FlightDao;
import az.edu.turing.stepProjBookingApp.model.entity.FlightEntity;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;

public class FlightPostgresDao extends FlightDao implements JdbcConnect {
    @Override
    public boolean save(List<FlightEntity> flightEntities) {
        String saveInto = "INSERT INTO flights (seats, date_and_time, location, destination) VALUES (?, ?, ?, ?)";
        try (Connection conn = getConnection()) {
            PreparedStatement statement = conn.prepareStatement(saveInto);
            for (FlightEntity flightEntity : flightEntities) {
                statement.setInt(1, flightEntity.getSeats());
                statement.setTimestamp(2, Timestamp.valueOf(flightEntity.getDateAndTime()));
                statement.setString(3, flightEntity.getLocation());
                statement.setString(4, flightEntity.getDestination());
                statement.addBatch();
            }
            statement.executeBatch();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }


    @Override
    public List<FlightEntity> getAll() {
        String getAll = "SELECT * FROM flights";
        List<FlightEntity> flightEntities = new ArrayList<>();
        try (Connection conn = getConnection()) {
            PreparedStatement query = conn.prepareStatement(getAll);
            ResultSet resultSet = query.executeQuery();
            while (resultSet.next()) {
                FlightEntity flightEntity = new FlightEntity(
                        resultSet.getInt("id"),
                        resultSet.getInt("seats"),
                        resultSet.getTimestamp("date_and_time").toLocalDateTime(),
                        resultSet.getString("location"),
                        resultSet.getString("destination"));
                flightEntities.add(flightEntity);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return flightEntities;
    }


    @Override
    public Optional<FlightEntity> getOneBy(Predicate<FlightEntity> predicate) {
        String getOneBySql = "SELECT * FROM flights";
        try (Connection conn = getConnection()) {
            PreparedStatement statement = conn.prepareStatement(getOneBySql);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                FlightEntity flightEntity = new FlightEntity(
                        resultSet.getInt("id"),
                        resultSet.getInt("seats"),
                        resultSet.getTimestamp("date_and_time").toLocalDateTime(),
                        resultSet.getString("location"),
                        resultSet.getString("destination"));
                if (predicate.test(flightEntity)) {
                    return Optional.of(flightEntity);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }

    @Override
    public List<FlightEntity> getAllBy(Predicate<FlightEntity> predicate) {
        List<FlightEntity> flightEntities = new ArrayList<>();
        String getAllBySql = "SELECT * FROM flights";
        try (Connection conn = getConnection()) {
            PreparedStatement statement = conn.prepareStatement(getAllBySql);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                FlightEntity flightEntity = new FlightEntity(
                        resultSet.getInt("id"),
                        resultSet.getInt("seats"),
                        resultSet.getTimestamp("date_and_time").toLocalDateTime(),
                        resultSet.getString("location"),
                        resultSet.getString("destination"));
                if (predicate.test(flightEntity)) {
                    flightEntities.add(flightEntity);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return flightEntities;
    }

    @Override
    public void update(long flightId, int seats) {
        String getSeatsSql = "SELECT seats FROM flights WHERE id = ?";
        String updateSql = "UPDATE flights SET seats = ? WHERE id = ?";
        Connection conn = null;
        try {
            conn = getConnection();
            conn.setAutoCommit(false);

            int currentSeats;
            try (PreparedStatement getSeatsStatement = conn.prepareStatement(getSeatsSql)) {
                getSeatsStatement.setLong(1, flightId);
                ResultSet resultSet = getSeatsStatement.executeQuery();
                if (resultSet.next()) {
                    currentSeats = resultSet.getInt("seats");
                } else {
                    throw new SQLException("Flight with ID " + flightId + " not found.");
                }
            }

            int newSeats = currentSeats + seats;

            try (PreparedStatement updateStatement = conn.prepareStatement(updateSql)) {
                updateStatement.setInt(1, newSeats);
                updateStatement.setLong(2, flightId);
                updateStatement.executeUpdate();
            }
            conn.commit();
        } catch (SQLException e) {
            e.printStackTrace();
            if (conn != null) {
                try {
                    conn.rollback();
                } catch (SQLException rollbackException) {
                    rollbackException.printStackTrace();
                }
            }
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
    public void delete(long id) {
        String deleteSql = "DELETE FROM flights WHERE id = ?";
        try (Connection conn = getConnection();
             PreparedStatement statement = conn.prepareStatement(deleteSql)) {
            statement.setLong(1, id);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Connection getConnection() {
        return JdbcConnect.super.getConnection();
    }
}