package az.edu.turing.stepProjBookingApp.model.dto;

import java.time.LocalDateTime;
import java.util.Objects;

public class FlightDto {
    private LocalDateTime dateAndTime;
    private String location;
    private String destination;
    private int seats;
    private long flightId;

    public FlightDto() {
    }

    public FlightDto(LocalDateTime dateAndTime, String location, String destination, int seats) {
        this.dateAndTime = dateAndTime;
        this.location = location;
        this.destination = destination;
        this.seats = seats;
    }

    public FlightDto(LocalDateTime dateAndTime, String location, String destination, int seats, long flightId) {
        this.dateAndTime = dateAndTime;
        this.location = location;
        this.destination = destination;
        this.seats = seats;
        this.flightId = flightId;
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

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public long getFlightId() {
        return flightId;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        FlightDto flightDto = (FlightDto) o;
        return seats == flightDto.seats && flightId == flightDto.flightId && Objects.equals(dateAndTime, flightDto.dateAndTime) && Objects.equals(destination, flightDto.destination);
    }

    @Override
    public int hashCode() {
        return Objects.hash(dateAndTime, destination, seats, flightId);
    }

    @Override
    public String toString() {
        return String.format("Flight ID: %d * Fly date: %s * Destination: '%s' * Seats: %d",
                flightId, dateAndTime, destination, seats);
    }
}
