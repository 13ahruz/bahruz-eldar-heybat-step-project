package az.edu.turing.stepProjBookingApp.dao.impl;

import az.edu.turing.stepProjBookingApp.dao.FlightDao;
import az.edu.turing.stepProjBookingApp.model.entity.FlightEntity;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.stream.Stream;

public class FlightFileDao extends FlightDao {
    private static final String RESOURCE_PATH = "src/main/java/az/edu/turing/stepProjBookingApp/resource";
    private static final String FLIGHT_FILE_PATH = RESOURCE_PATH.concat("flights.json");
    private final ObjectMapper objectMapper;

    public FlightFileDao(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    @Override
    public boolean reservFlight(FlightEntity flightEntity) {
        try {
            byte[] jsonData = Files.readAllBytes(Paths.get(FLIGHT_FILE_PATH));
            List<FlightEntity> flights = objectMapper.readValue(jsonData, new TypeReference<List<FlightEntity>>() {
            });
            flights.add(flightEntity);
            Files.write(Paths.get(FLIGHT_FILE_PATH), objectMapper.writeValueAsBytes(flights));
            return true;
        } catch (IOException e) {
            System.err.println("Error while adding new flight: " + e.getMessage());
        }
        return false;
    }

    @Override
    public Collection<FlightEntity> getAllFlights() {
        try {
            byte[] jsonData = Files.readAllBytes(Paths.get(FLIGHT_FILE_PATH));
            return objectMapper.readValue(jsonData, new TypeReference<Collection<FlightEntity>>() {
            });
        } catch (Exception e) {
            System.out.println("Error while loading flights from file: " + e.getMessage());
        }
        return null;
    }

    @Override
    public Optional<FlightEntity> getFlightBy(Predicate<FlightEntity> predicate) {
        try {
            byte[] jsonData = Files.readAllBytes(Paths.get(FLIGHT_FILE_PATH));
            FlightEntity[] flights = objectMapper.readValue(jsonData, FlightEntity[].class);
            return Stream.of(flights)
                    .filter(predicate)
                    .findFirst();
        } catch (IOException e) {
            System.out.println("Error while reading flights from file: " + e.getMessage());
        }
        return Optional.empty();
    }

    @Override
    public Collection<FlightEntity> getFlightsBy(Predicate<FlightEntity> predicate) {
        try {
            byte[] jsonData = Files.readAllBytes(Paths.get(FLIGHT_FILE_PATH));
            FlightEntity[] flights = objectMapper.readValue(jsonData, FlightEntity[].class);
            return Stream.of(flights)
                    .filter(predicate)
                    .toList();
        } catch (IOException e) {
            System.out.println("Error while reading flights from file: " + e.getMessage());
        }
        return new ArrayList<>();
    }

    @Override
    public boolean cancelFlightBy(Predicate<FlightEntity> predicate) {
        try {
            byte[] jsonData = Files.readAllBytes(Paths.get(FLIGHT_FILE_PATH));
            List<FlightEntity> flights = objectMapper.readValue(jsonData, new TypeReference<List<FlightEntity>>() {
            });
            Optional<FlightEntity> flightForRemove = flights.stream().filter(predicate).findFirst();
            flights.remove(flightForRemove);
            Files.write(Paths.get(FLIGHT_FILE_PATH), objectMapper.writeValueAsBytes(flights));
            return true;
        } catch (Exception e) {
            System.out.println("Error occured while removing reservation: " + e.getMessage());
        }
        return false;
    }
}
