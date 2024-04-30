package az.edu.turing.stepProjBookingApp.model.entity;

import java.util.Objects;

public class FlightEntity {
    private final int date;
    private final int time;
    private final String destination;
    private final int seats;
    private int flightId;

    public FlightEntity(int date, int time, String destination, int seats) {
        this.date = date;
        this.time = time;
        this.destination = destination;
        this.seats = seats;
    }

    public FlightEntity(int date, int time, String destination, int seats, int flightsId) {
        this.date = date;
        this.time = time;
        this.destination = destination;
        this.seats = seats;
        this.flightId = flightsId;
    }

    public int getDate() {
        return date;
    }

    public int getTime() {
        return time;
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
        return date == that.date && time == that.time && seats == that.seats && flightId == that.flightId && Objects.equals(destination, that.destination);
    }

    @Override
    public int hashCode() {
        return Objects.hash(flightId);
    }

    @Override
    public String toString() {
        return "date=%d, time=%d, destination='%s', seats=%d, flightId=%d}".formatted(date, time, destination, seats, flightId);
    }
}
