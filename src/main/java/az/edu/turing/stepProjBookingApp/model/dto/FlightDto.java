package az.edu.turing.stepProjBookingApp.model.dto;

import java.util.Objects;

public class FlightDto {
    private final int date;
    private final int time;
    private final String destination;
    private final int seats;

    public FlightDto(int date, int time, String destination, int seats) {
        this.date = date;
        this.time = time;
        this.destination = destination;
        this.seats = seats;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        FlightDto flightDto = (FlightDto) o;
        return date == flightDto.date && time == flightDto.time && seats == flightDto.seats && destination.equals(flightDto.destination);
    }

    @Override
    public int hashCode() {
        return Objects.hash(date, time, destination, seats);
    }

    @Override
    public String toString() {
        return "date=%d, time=%d, destination='%s', seats=%d}"
                .formatted(date, time, destination, seats);
    }
}
