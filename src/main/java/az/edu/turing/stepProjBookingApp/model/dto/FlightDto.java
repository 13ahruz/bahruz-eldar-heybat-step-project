package az.edu.turing.stepProjBookingApp.model.dto;

import java.time.LocalDateTime;
import java.util.Objects;

public class FlightDto {
    private LocalDateTime dateAndTime;
    private String destination;
    private int seats;
    private long flightId;

    public FlightDto() {
    }

    public FlightDto(LocalDateTime dateAndTime, String destination, int seats) {
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        FlightDto flightDto = (FlightDto) o;
        return seats == flightDto.seats && Objects.equals(dateAndTime, flightDto.dateAndTime) && Objects.equals(destination, flightDto.destination);
    }

    @Override
    public int hashCode() {
        return Objects.hash(dateAndTime, destination, seats);
    }

    @Override
    public String toString() {
        return "Flight ID: %d * Fly date: %s * Destination: '%s' * Seats: %d}"
                .formatted(flightId, dateAndTime, destination, seats);
    }
}
