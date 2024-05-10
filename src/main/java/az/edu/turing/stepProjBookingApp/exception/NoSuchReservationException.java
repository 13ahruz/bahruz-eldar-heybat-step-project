package az.edu.turing.stepProjBookingApp.exception;

public class NoSuchReservationException extends RuntimeException{
    public NoSuchReservationException(String message){
        super(message);
    }
}
