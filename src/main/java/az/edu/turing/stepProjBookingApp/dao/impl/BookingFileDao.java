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
    public boolean save(List<BookingEntity> reservations) {
        try {
            FileWriter fw = new FileWriter(file);
            BufferedWriter bw = new BufferedWriter(fw);
            bw.write(objectMapper.writeValueAsString(reservations));
            bw.close();
            return true;
        } catch (IOException e) {
            System.err.println("Error while booking: " + e.getMessage());
        }
        return false;
    }

    @Override
    public List<BookingEntity> getAllBy(Predicate predicate) {
        try {
            byte[] jsonData = Files.readAllBytes(Paths.get(BOOKING_FILE_PATH));
            return objectMapper.readValue(jsonData, new TypeReference<List<BookingEntity>>() {
            });
        } catch (Exception e) {
            System.out.println("Error while loading bookings from file: " + e.getMessage());
        }
        return null;
    }

    @Override
    public Optional<BookingDto> getOneBy(Predicate predicate) {
            Collection<BookingEntity> bookings = getAll();
            return Stream.of(bookings)
                    .filter(predicate)
                    .findFirst();
    }

    @Override
    public Collection<BookingEntity> getAll() {
        try {
            FileReader fr = new FileReader(file);
            BufferedReader br = new BufferedReader(fr);
            String jsonData = br.readLine();
            if (jsonData != null && !jsonData.isBlank()) {
                BookingEntity[] flights = objectMapper.readValue(jsonData, BookingEntity[].class);
                br.close();
                return Arrays.stream(flights).toList();
            }
            br.close();
        } catch (IOException e) {
            System.out.println("Error while reading reservations from file: " + e.getMessage());
        }
        return null;
    }
}
