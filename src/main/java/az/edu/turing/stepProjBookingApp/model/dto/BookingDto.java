package az.edu.turing.stepProjBookingApp.model.dto;

import java.util.Objects;

public class BookingDto {
    //TODO generate unique ids
    private final String firstName;
    private final String secondName;
    private long flightId;

    public BookingDto(String firstName, String secondName, long flightId) {
        this.firstName = firstName;
        this.secondName = secondName;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getSecondName() {
        return secondName;
    }

    public long getFlightId() {
        return flightId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BookingDto that = (BookingDto) o;
        return flightId == that.flightId && Objects.equals(firstName, that.firstName) && Objects.equals(secondName, that.secondName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(firstName, secondName, flightId);
    }

    @Override
    public String toString() {
        return "firstName='%s', secondName='%s', flightId=%d}"
                .formatted(firstName, secondName, flightId);
    }
}
