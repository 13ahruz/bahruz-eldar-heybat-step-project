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
                int seat = resultSet.getInt("seats");
                LocalDateTime dateAndTime = resultSet.getTimestamp("date_and_time").toLocalDateTime();
                String location = resultSet.getString("location");
                String destination = resultSet.getString("destination");
                flightEntities.add(new FlightEntity(seat, dateAndTime, location, destination));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return flightEntities;
    }

    @Override
    public Optional<FlightEntity> getOneBy(Predicate<FlightEntity> predicate) {
        String getOneBySql = "SELECT FROM flights WHERE id = ?";
        try (Connection conn = getConnection()) {
            PreparedStatement statement = conn.prepareStatement(getOneBySql);
            ResultSet resultSet = statement.executeQuery();
            return Optional.of(new FlightEntity(
                    resultSet.getInt("seats"),
                    resultSet.getTimestamp("date_and_time").toLocalDateTime(),
                    resultSet.getString("location"),
                    resultSet.getString("destination")));
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }

    @Override
    public List<FlightEntity> getAllBy(Predicate<FlightEntity> predicate) {
        List<FlightEntity> flightEntities = new ArrayList<>();
        String getAllBySql = "SELECT id, firstname, lastname, amount, flightId FROM flights";
        try (Connection conn = getConnection()) {
            PreparedStatement statement = conn.prepareStatement(getAllBySql);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                FlightEntity flightEntity = new FlightEntity(
                        resultSet.getLong("id"),
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
    public Connection getConnection() {
        return JdbcConnect.super.getConnection();
    }
}