package az.edu.turing.stepProjBookingApp.model.dto;

import java.time.LocalDateTime;
import java.util.Objects;

public class FlightDto {
    private final LocalDateTime dateAndTime;
    private final String destination;
    private final int seats;

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
        return "dateAndTime=%s, destination='%s', seats=%d}"
                .formatted(dateAndTime, destination, seats);
    }
}
