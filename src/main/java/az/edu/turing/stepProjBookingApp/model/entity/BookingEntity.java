package az.edu.turing.stepProjBookingApp.model.entity;

import java.util.Objects;

public class BookingEntity {
    private String firstName;
    private String secondName;
    private long flightId;
    private int amount;
    private long bookingId;
    public static long MAX_BOOKING_ID = 1;

    public BookingEntity() {
    }

    public BookingEntity(String firstName, String secondName, long flightId, int amount) {
        this.firstName = firstName;
        this.secondName = secondName;
        this.flightId = flightId;
        this.amount = amount;
        this.bookingId = MAX_BOOKING_ID++;
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
        BookingEntity that = (BookingEntity) o;
        return flightId == that.flightId && Objects.equals(firstName, that.firstName) && Objects.equals(secondName, that.secondName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(firstName, secondName, flightId);
    }

    @Override
    public String toString() {
        return "First name: '%s' || Second name: '%s' || "
                .formatted(firstName, secondName);
    }
}
