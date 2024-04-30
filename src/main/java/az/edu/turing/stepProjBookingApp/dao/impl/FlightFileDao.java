package az.edu.turing.stepProjBookingApp.dao.impl;

import az.edu.turing.stepProjBookingApp.dao.FlightDao;
import az.edu.turing.stepProjBookingApp.model.entity.FlightEntity;
import com.fasterxml.jackson.databind.ObjectMapper;


import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collection;
import java.util.Optional;
import java.util.function.Predicate;

public class FlightFileDao extends FlightDao {
    private static final String RESOURCE_PATH = "src/main/java/az/edu/turing/stepProjBookingApp/resource";
    private static final String FLIGHT_FILE_PATH = RESOURCE_PATH.concat("flights.txt");
    private final ObjectMapper objectMapper;

    public FlightFileDao(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    @Override
    public FlightEntity save (FlightEntity flightEntity){
        try {
            final Path path = Paths.get(FLIGHT_FILE_PATH);
            Files.write(path, objectMapper.writeValueAsBytes(flightEntity));
        } catch (IOException e){
            System.err.println("Error while saving flight to file ...");
        }
        return flightEntity;
    }

    @Override
    public Collection<FlightEntity> getAllFlights (){
        final Path path = Paths.get(FLIGHT_FILE_PATH);

        return null;
    }

    @Override
    public Optional<FlightEntity> getFlightBy(Predicate<FlightEntity> predicate) {
        return Optional.empty();
    }

    @Override
    public Collection<FlightEntity> getFlightsBy(Predicate<FlightEntity> predicate) {
        return null;
    }

    @Override
    public void update(FlightEntity flightEntity) {

    }
}
