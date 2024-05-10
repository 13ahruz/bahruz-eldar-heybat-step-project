package az.edu.turing.stepProjBookingApp;

import az.edu.turing.stepProjBookingApp.controller.BookingController;
import az.edu.turing.stepProjBookingApp.controller.FlightController;
import az.edu.turing.stepProjBookingApp.dao.BookingDao;
import az.edu.turing.stepProjBookingApp.dao.FlightDao;
import az.edu.turing.stepProjBookingApp.dao.impl.BookingFileDao;
import az.edu.turing.stepProjBookingApp.dao.impl.FlightFileDao;
import az.edu.turing.stepProjBookingApp.exception.NoEnoughSeatsException;
import az.edu.turing.stepProjBookingApp.exception.NoSuchReservationException;
import az.edu.turing.stepProjBookingApp.model.dto.FlightDto;
import az.edu.turing.stepProjBookingApp.model.entity.FlightEntity;
import az.edu.turing.stepProjBookingApp.service.BookingService;
import az.edu.turing.stepProjBookingApp.service.FlightService;
import az.edu.turing.stepProjBookingApp.service.impl.BookingServiceImpl;
import az.edu.turing.stepProjBookingApp.service.impl.FlightServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Scanner;

public class BookingManagementApp {
    private static final Scanner scanner = new Scanner(System.in);
    private static final BookingDao bookingDao = new BookingFileDao(new ObjectMapper().registerModule(new JavaTimeModule()));
    private static final FlightDao flightDao = new FlightFileDao(new ObjectMapper().registerModule(new JavaTimeModule()));
    private static final FlightService flightService = new FlightServiceImpl(flightDao);
    private static final FlightController flightController = new FlightController(flightService);
    private static final BookingService bookingService = new BookingServiceImpl(bookingDao, flightDao);
    private static final BookingController bookingController = new BookingController(bookingService);
    public static void main(String[] args) {
        FlightDto flight1 = new FlightDto(
                LocalDateTime.of(2024, 5, 1, 10, 30), "Kiev", "Baku", 15);
        FlightDto flight2 = new FlightDto(
                LocalDateTime.of(2024, 5, 2, 11, 30), "Kiev", "Salyan", 13);
        FlightDto flight3 = new FlightDto(
                LocalDateTime.of(2024, 5, 3, 12, 30), "London", "Bilasuvar republic", 2);
        flightController.createFlight(flight1);
        flightController.createFlight(flight2);
        flightController.createFlight(flight3);
        while (true){
            displayMenu();
            int choice = readIntChoice();
            String firstName;
            String secondName;
            switch (choice) {
                case 1:
                    System.out.println("Enter your location: ");
                    String location = readStringChoice();
                    List<FlightDto> filteredFlights = flightController.getFlightsByLocation(location);
                    if (!filteredFlights.isEmpty()){
                        filteredFlights.forEach(flightDto -> System.out.println("Flight id: " +
                                flightDto.getFlightId() + " || Destination: " +
                                flightDto.getDestination() + " || Fly time: " +
                                flightDto.getDateAndTime()));
                    }
                    else {
                        System.out.println("No flights from your destination!");
                    }
                    break;
                case 2:
                    System.out.println("Enter flight id: ");
                    int tempChoice = readIntChoice();
                    System.out.println(flightController.getFlightById(tempChoice).get().toString());
                    break;
                case 3:
                    try {
                        System.out.println("Enter your firstname: ");
                        firstName = readStringChoice();
                        System.out.println("Enter your second name: ");
                        secondName = readStringChoice();
                        System.out.println("Enter flight id: ");
                        int flightIdForBooking = readIntChoice();
                        System.out.println("Enter amount a seats for booking: ");
                        int amount = readIntChoice();
                        bookingController.bookAReservation(firstName, secondName, flightIdForBooking, amount);
                            System.out.println("Booking successful");
                    }
                    catch (NoEnoughSeatsException e){
                        System.out.println(e.getMessage());
                    }
                    break;
                case 4:
                    System.out.println("Enter your first name: ");
                    firstName = readStringChoice();
                    System.out.println("Enter your second name: ");
                    secondName = readStringChoice();
                    System.out.println("Enter flight ID for cancellation: ");
                    int flightIdForCancellation = readIntChoice();
                    try {
                        bookingController.cancelAReservation(firstName, secondName, flightIdForCancellation);
                        System.out.println("Cancellation successful");
                    }catch (NoSuchReservationException e){
                        System.out.println(e.getMessage());
                    }
                    break;
                case 5:
                    System.out.println("Enter your firstname: ");
                    firstName = readStringChoice();
                    System.out.println("Enter your second name: ");
                    secondName = readStringChoice();
                    try{
                        bookingController.getMyReservations(firstName, secondName);
                    }
                    catch (NoSuchReservationException e){
                        System.out.println(e.getMessage());
                    }
                    break;
                case 6:
                    System.out.println("Exiting the program ...");
                    return;
                default:
                    System.out.println("Invalid choice. Please choose again!");
            }
        }
    }



    private static void displayMenu() {
        System.out.println("Menu:");
        System.out.println("1. Online-board.");
        System.out.println("2. Show the flight info.");
        System.out.println("3. Search and book a flight.");
        System.out.println("4. Cancel the booking.");
        System.out.println("5. My flights.");
        System.out.println("6. Exit.");
    }
    private static int readIntChoice() {
        return scanner.nextInt();
    }

    private static String readStringChoice (){
        return scanner.next();
    }
}
