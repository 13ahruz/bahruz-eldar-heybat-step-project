package az.edu.turing.stepProjBookingApp.dao.impl;

import az.edu.turing.stepProjBookingApp.dao.BookingDao;
import az.edu.turing.stepProjBookingApp.model.dto.BookingDto;
import az.edu.turing.stepProjBookingApp.model.entity.FlightEntity;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.stream.Stream;

public class BookingFileDao extends BookingDao {
    private static final String RESOURCE_PATH = "src/main/java/az/edu/turing/stepProjBookingApp/resource";
    private static final String BOOKING_FILE_PATH = RESOURCE_PATH.concat("bookings.json");
    private final ObjectMapper objectMapper;

    public BookingFileDao(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    @Override
    public boolean save(List<BookingDto> bookingDto) {
        try {
            final Path path = Paths.get(BOOKING_FILE_PATH);
            Files.write(path, objectMapper.writeValueAsBytes(bookingDto));
            return true;
        } catch (IOException e) {
            System.err.println("Error while adding new booking: " + e.getMessage());
        }
        return false;
    }

    @Override
    public Collection<BookingDto> getAllBy(Predicate predicate) {
        try {
            byte[] jsonData = Files.readAllBytes(Paths.get(BOOKING_FILE_PATH));
            return objectMapper.readValue(jsonData, new TypeReference<Collection<BookingDto>>() {
            });
        } catch (Exception e) {
            System.out.println("Error while loading bookings from file: " + e.getMessage());
        }
        return null;
    }

    @Override
    public Optional<BookingDto> getOneBy(Predicate predicate) {
        try {
            byte[] jsonData = Files.readAllBytes(Paths.get(BOOKING_FILE_PATH));
            FlightEntity[] flights = objectMapper.readValue(jsonData, FlightEntity[].class);
            return Stream.of(flights)
                    .filter(predicate)
                    .findFirst();
        } catch (IOException e) {
            System.out.println("Error while reading bookings from file: " + e.getMessage());
        }
        return Optional.empty();
    }

    @Override
    public Collection<BookingDto> getAll() {
        try {
            byte[] jsonData = Files.readAllBytes(Paths.get(BOOKING_FILE_PATH));
            BookingDto[] flights = objectMapper.readValue(jsonData, BookingDto[].class);
            return Arrays.stream(flights).toList();
        } catch (IOException e) {
            System.out.println("Error while reading bookings from file: " + e.getMessage());
        }
        return null;
    }

}
