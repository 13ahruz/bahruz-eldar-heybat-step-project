package az.edu.turing.stepProjBookingApp.dao;

import az.edu.turing.stepProjBookingApp.model.entity.FlightEntity;

import java.util.Collection;
import java.util.Optional;
import java.util.function.Predicate;

public interface DAO<T> {
    T save(T t);
    Collection <T> getAllFlights();
    Optional <T> getFlightBy (Predicate <T> predicate);
    Collection <T> getFlightsBy (Predicate <T> predicate);
    void update (FlightEntity flightEntity);


}
