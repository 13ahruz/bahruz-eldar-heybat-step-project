package az.edu.turing.stepProjBookingApp.dao;

import az.edu.turing.stepProjBookingApp.model.entity.FlightEntity;

import java.util.Collection;
import java.util.Optional;
import java.util.function.Predicate;

public interface DAO<T> {
    boolean reservFlight(T t);
    Collection <T> getAllFlights();
    Optional <T> getFlightBy (Predicate <T> predicate);
    Collection <T> getFlightsBy (Predicate <T> predicate);
    boolean cancelFlightBy (Predicate <T> predicate);


}
