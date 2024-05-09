package az.edu.turing.stepProjBookingApp;

import az.edu.turing.stepProjBookingApp.controller.BookingController;
import az.edu.turing.stepProjBookingApp.controller.FlightController;
import az.edu.turing.stepProjBookingApp.dao.BookingDao;
import az.edu.turing.stepProjBookingApp.dao.FlightDao;
import az.edu.turing.stepProjBookingApp.dao.impl.BookingFileDao;
import az.edu.turing.stepProjBookingApp.dao.impl.FlightFileDao;
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
    private static final BookingService bookingService = new BookingServiceImpl(bookingDao);
    private static final BookingController bookingController = new BookingController(bookingService);
    private static final FlightDao flightDao = new FlightFileDao(new ObjectMapper().registerModule(new JavaTimeModule()));
    private static final FlightService flightService = new FlightServiceImpl(flightDao);
    private static final FlightController flightController = new FlightController(flightService);
    public static void main(String[] args) {
        FlightDto flight1 = new FlightDto(
                LocalDateTime.of(2024, 5, 1, 10, 30), "Kiev", 15);
        FlightDto flight2 = new FlightDto(
                LocalDateTime.of(2024, 5, 2, 11, 30), "Moscow", 13);
        FlightDto flight3 = new FlightDto(
                LocalDateTime.of(2024, 5, 3, 12, 30), "London", 2);
        flightController.createFlight(flight1);
        flightController.createFlight(flight2);
        flightController.createFlight(flight3);
        while (true){
            displayMenu();
            int choice = readChoice();
            String firstName;
            String secondName;
            switch (choice) {
                case 1:
                    System.out.println("Enter your location: ");
                    String location = scanner.nextLine();
                    List<FlightDto> filteredFlights = flightController.getFlightsByDest(location);
                    if (!filteredFlights.isEmpty()){
                        filteredFlights.forEach(flightDto -> System.out.println(flightDto.toString()));
                    }
                    else {
                        System.out.println("No flight from your destination!");
                    }

                    break;
                case 2:
                    System.out.println("Enter flight id: ");
                    int tempChoice = readChoice();
                    System.out.println(flightController.getFlightById(tempChoice).toString());
                    break;
                case 3:
                    System.out.println("Enter your firstname: ");
                    firstName = scanner.nextLine();
                    System.out.println("Enter your second name: ");
                    secondName = scanner.nextLine();
                    System.out.println("Enter flight id: ");
                    int flightIdForBooking = readChoice();
                    if (bookingController.bookAReservation(firstName, secondName, flightIdForBooking)) {
                        System.out.println("Booking successful");
                    } else {
                        System.out.println("Order unsuccessful");
                    }
                    break;
                case 4:
                    System.out.println("Enter flight ID for cancellation: ");
                    int flightIdForCancellation = readChoice();
                    if (bookingController.cancelAReservation(flightIdForCancellation)) {
                        System.out.println("Cancellation successful");
                    } else {
                        System.out.println("Cancellation unsuccessful");
                    }
                    break;
                case 5:
                    System.out.println("Enter your firstname: ");
                    firstName = scanner.nextLine();
                    System.out.println("Enter your second name: ");
                    secondName = scanner.nextLine();
                    bookingController.getMyReservations(firstName, secondName)
                            .forEach(flight -> System.out.println(flight.toString()));
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
    private static int readChoice() {
        return scanner.nextInt();




    }
}
