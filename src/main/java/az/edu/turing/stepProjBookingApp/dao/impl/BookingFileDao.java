package az.edu.turing.stepProjBookingApp.dao.impl;

import az.edu.turing.stepProjBookingApp.dao.BookingDao;
import az.edu.turing.stepProjBookingApp.model.dto.BookingDto;
import az.edu.turing.stepProjBookingApp.model.entity.BookingEntity;
import az.edu.turing.stepProjBookingApp.model.entity.FlightEntity;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.stream.Stream;

public class BookingFileDao extends BookingDao {
    private static final String RESOURCE_PATH = "C:\\Users\\ROMedia\\IdeaProjects\\bahruz-eldar-heybat-step-project\\src\\main\\java\\az\\edu\\turing\\stepProjBookingApp\\resource\\";
    private static final String BOOKING_FILE_PATH = RESOURCE_PATH.concat("bookings.json");
    private static final File file = new File(BOOKING_FILE_PATH);
    private final ObjectMapper objectMapper;

    public BookingFileDao(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    @Override
    public boolean save(List<BookingEntity> bookings) {
        try {
            FileWriter fw = new FileWriter(file);
            BufferedWriter bw = new BufferedWriter(fw);
            bw.write(objectMapper.writeValueAsString(bookings));
            bw.close();
            return true;
        } catch (IOException e) {
            System.err.println("Error while adding new flight: " + e.getMessage());
        }
        return false;
    }

    @Override
    public Optional<List<BookingEntity>> getAllBy(Predicate<BookingEntity> predicate) {
        try {
            byte[] jsonData = Files.readAllBytes(Paths.get(BOOKING_FILE_PATH));
            List<BookingEntity> allBookings = objectMapper.readValue(jsonData, new TypeReference<List<BookingEntity>>() {
            });
            List<BookingEntity> filteredBookings = allBookings.stream().filter(predicate).toList();
            return Optional.of(filteredBookings);
        } catch (IOException e) {
            System.out.println("Error while loading flights from file: " + e.getMessage());
        }
        return Optional.empty();
    }

    @Override
    public Optional<BookingEntity> getOneBy(Predicate<BookingEntity> predicate) {
        try {
            byte[] jsonData = Files.readAllBytes(Paths.get(BOOKING_FILE_PATH));
            BookingEntity[] bookings = objectMapper.readValue(jsonData, BookingEntity[].class);

            Optional<BookingEntity> oneFlight = Arrays.stream(bookings).filter(predicate).findFirst();
            return oneFlight;
        } catch (IOException e) {
            System.out.println("Error while reading flights from file: " + e.getMessage());
        }
        return Optional.empty();
    }

    @Override
    public Optional<List<BookingEntity>> getAll() {
        try {
            FileReader fr = new FileReader(file);
            BufferedReader x = new BufferedReader(fr);
            String jsonData = x.readLine();
            if (jsonData != null && !jsonData.isBlank()) {
                BookingEntity[] bookings = objectMapper.readValue(jsonData, BookingEntity[].class);
                x.close();
                return Optional.of(Arrays.asList(bookings));
            }
            x.close();
        } catch (IOException e) {
            System.out.println("Error while reading flights from file: " + e.getMessage());
        }
        return Optional.empty();
    }
}
