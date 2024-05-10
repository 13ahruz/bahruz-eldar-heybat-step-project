package az.edu.turing.stepProjBookingApp.exception;

public class NoEnoughSeatsException extends RuntimeException {
    public NoEnoughSeatsException (String message){
        super(message);
    }
}
