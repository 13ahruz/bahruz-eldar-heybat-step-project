package az.edu.turing.stepProjBookingApp.exception;

public class NotAValidFlightException extends RuntimeException{
    public NotAValidFlightException (String message){
        super(message);
    }
}
