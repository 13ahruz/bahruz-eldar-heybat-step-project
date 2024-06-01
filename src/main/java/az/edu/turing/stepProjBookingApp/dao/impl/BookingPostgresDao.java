package az.edu.turing.stepProjBookingApp.dao.impl;

import az.edu.turing.stepProjBookingApp.dao.BookingDao;
import az.edu.turing.stepProjBookingApp.model.entity.BookingEntity;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;

public class BookingPostgresDao extends BookingDao implements JdbcConnect{
    @Override
    public boolean save(List<BookingEntity> bookingEntities) {
        String saveInto = "INSERT INTO bookings (firstname, lastname, amount, flightId) VALUES (?, ?, ?, ?)";
        try (Connection conn = getConnection()) {
            PreparedStatement statement = conn.prepareStatement(saveInto);
            for (BookingEntity bookingEntity : bookingEntities) {
                statement.setString(1, bookingEntity.getFirstName());
                statement.setString(2, bookingEntity.getSecondName());
                statement.setInt(3, bookingEntity.getAmount());
                statement.setLong(4, bookingEntity.getFlightId());
                statement.addBatch();
            }
            statement.executeBatch();
            return true;
        }
        catch (SQLException e){
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public List<BookingEntity> getAll() {
        String getAll = "SELECT * FROM booking";
        List<BookingEntity> bookingEntities = new ArrayList<>();
        try (Connection conn = getConnection()){
            PreparedStatement query = conn.prepareStatement(getAll);
            ResultSet resultSet = query.executeQuery();
            while (resultSet.next()) {
                int amount = resultSet.getInt("amount");
                String firstname = "firstname";
                String secondname = "lastname";
                long flightId = resultSet.getLong("flight_id");
                bookingEntities.add(new BookingEntity(firstname, secondname, flightId, amount));
            }
        }
        catch (SQLException e){
            System.out.println(e.getMessage());
        }
        return bookingEntities;
    }

    @Override
    public Optional<BookingEntity> getOneBy(Predicate<BookingEntity> predicate) {
        String getOneBySql = "SELECT FROM booking WHERE id = ?";
        try (Connection conn = getConnection()){
            PreparedStatement statement = conn.prepareStatement(getOneBySql);
            ResultSet resultSet = statement.executeQuery();
            return Optional.of ( new BookingEntity(
                    resultSet.getString("firstname"),
                    resultSet.getString("lastname"),
                    resultSet.getInt("amount"),
                    resultSet.getLong("flight_id")));
        }
        catch (SQLException e){
            e.printStackTrace();
        }
         return Optional.empty();
    }


    @Override
    public List<BookingEntity> getAllBy(Predicate<BookingEntity> predicate) {
        List <BookingEntity> bookingEntities = new ArrayList<>();
        String getAllBySql = "SELECT id, firstname, lastname, amount, flightId FROM bookings";
        try (Connection conn = getConnection()){
            PreparedStatement statement = conn.prepareStatement(getAllBySql);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()){
                BookingEntity bookingEntity = new BookingEntity(
                resultSet.getLong("id"),
                        resultSet.getString("firstname"),
                        resultSet.getString("lastname"),
                        resultSet.getLong("flight_id"),
                        resultSet.getInt("amount"));
                if (predicate.test(bookingEntity)) {
                    bookingEntities.add(bookingEntity);
                }
            }
        }
        catch (SQLException e){
            e.printStackTrace();
        }
        return bookingEntities;
    }

    @Override
    public Connection getConnection (){
        return JdbcConnect.super.getConnection();
    }
}
