package az.edu.turing.stepProjBookingApp.model.entity;

import java.time.LocalDateTime;
import java.util.Objects;

public class FlightEntity {
    private static long nextId = 1;
    private LocalDateTime dateAndTime;
    private String destination;
    private int seats;
    private long flightId;

    public FlightEntity(LocalDateTime dateAndTime, String destination, int seats) {
        this.dateAndTime = dateAndTime;
        this.destination = destination;
        this.seats = seats;
        this.flightId = generateUniqueId();
    }

    private synchronized long generateUniqueId() {
        return nextId++;
    }

    public void setDateAndTime(LocalDateTime dateAndTime) {
        this.dateAndTime = dateAndTime;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public void setSeats(int seats) {
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

    public long getFlightId() {
        return flightId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        FlightEntity that = (FlightEntity) o;
        return seats == that.seats && flightId == that.flightId && Objects.equals(dateAndTime, that.dateAndTime) && Objects.equals(destination, that.destination);
    }

    @Override
    public int hashCode() {
        return Objects.hash(dateAndTime, destination, seats, flightId);
    }

    @Override
    public String toString() {
        return "Fly Date: %s * Destination: '%s' * Available seats: %d * FlightId: %d}"
                .formatted(dateAndTime, destination, seats, flightId);
    }
}
