package az.edu.turing.stepProjBookingApp.dao.impl;

import az.edu.turing.stepProjBookingApp.dao.FlightDao;
import az.edu.turing.stepProjBookingApp.model.dto.FlightDto;
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

public class FlightFileDao extends FlightDao {
    private static final String RESOURCE_PATH = "az/edu/turing/stepProjBookingApp/resource/";
    private static final String FLIGHT_FILE_PATH = RESOURCE_PATH.concat("flights.json");
    private final ObjectMapper objectMapper;

    public FlightFileDao(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    @Override
    public boolean save(List<FlightDto> flights) {
        try {
            final Path path = Paths.get(FLIGHT_FILE_PATH);
            Files.write(path, objectMapper.writeValueAsBytes(flights));
            return true;
        } catch (IOException e) {
            System.err.println("Error while adding new flight: " + e.getMessage());
        }
        return false;
    }

    @Override
    public Collection<FlightDto> getAllBy(Predicate predicate) {
        try {
            byte[] jsonData = Files.readAllBytes(Paths.get(FLIGHT_FILE_PATH));
            return objectMapper.readValue(jsonData, new TypeReference<Collection<FlightDto>>() {
            });
        } catch (Exception e) {
            System.out.println("Error while loading flights from file: " + e.getMessage());
        }
        return null;
    }

    @Override
    public Optional<FlightDto> getOneBy(Predicate predicate) {
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
    public Collection<FlightDto> getAll() {
        try {
            byte[] jsonData = Files.readAllBytes(Paths.get(FLIGHT_FILE_PATH));
            FlightDto[] flights = objectMapper.readValue(jsonData, FlightDto[].class);
            return Arrays.stream(flights).toList();
        } catch (IOException e) {
            System.out.println("Error while reading flights from file: " + e.getMessage());
        }
        return null;
    }
}
