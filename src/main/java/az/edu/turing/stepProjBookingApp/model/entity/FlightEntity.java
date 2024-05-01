package az.edu.turing.stepProjBookingApp.model.entity;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.Objects;

public class FlightEntity {
    private final LocalDateTime dateAndTime;
    private final String destination;
    private final int seats;
    private int flightId;

    public FlightEntity(LocalDateTime dateAndTime, String destination, int seats) {
        this.dateAndTime = dateAndTime;
        this.destination = destination;
        this.seats = seats;
    }

    public LocalDateTime getDateAndTime() {
        return dateAndTime;
    }

    public String getDestination() {
        return destination;
    }

    public int getSeats() {
        return seats;
    }

    public int getFlightId() {
        return flightId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        FlightEntity that = (FlightEntity) o;
        return seats == that.seats && flightId == that.flightId && Objects
                .equals(dateAndTime, that.dateAndTime) && Objects
                .equals(destination, that.destination);
    }

    @Override
    public int hashCode() {
        return Objects.hash(flightId);
    }

    @Override
    public String toString() {
        return "dateAndTime=%s, destination='%s', seats=%d, flightId=%d}"
                .formatted(dateAndTime, destination, seats, flightId);
    }
}
