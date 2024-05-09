package az.edu.turing.stepProjBookingApp.dao.impl;

import az.edu.turing.stepProjBookingApp.dao.FlightDao;
import az.edu.turing.stepProjBookingApp.model.entity.FlightEntity;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;

public class FlightFileDao extends FlightDao {
    private static final String RESOURCE_PATH = "C:\\Users\\ROMedia\\IdeaProjects\\bahruz-eldar-heybat-step-project\\src\\main\\java\\az\\edu\\turing\\stepProjBookingApp\\resource\\";
    private static final String FLIGHT_FILE_PATH = RESOURCE_PATH.concat("flights.json");
    private static final File file = new File(FLIGHT_FILE_PATH);
    private final ObjectMapper objectMapper;

    public FlightFileDao(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    @Override
    public boolean save(List<FlightEntity> flights) {
        try {
            FileWriter fw = new FileWriter(file);
            BufferedWriter bw = new BufferedWriter(fw);
            bw.write(objectMapper.writeValueAsString(flights));
            bw.close();
            return true;
        } catch (IOException e) {
            System.err.println("Error while adding new flight: " + e.getMessage());
        }
        return false;
    }

    @Override
    public List<FlightEntity> getAllBy(Predicate<FlightEntity> predicate) {
        try {
            byte[] jsonData = Files.readAllBytes(Paths.get(FLIGHT_FILE_PATH));
            List<FlightEntity> allFlights = objectMapper.readValue(jsonData, new TypeReference<List<FlightEntity>>() {
            });
            return allFlights.stream().filter(predicate).toList();
        } catch (IOException e) {
            System.out.println("Error while loading flights from file: " + e.getMessage());
        }
        return null;
    }

    @Override
    public Optional<FlightEntity> getOneBy(Predicate<FlightEntity> predicate) {
        try {
            byte[] jsonData = Files.readAllBytes(Paths.get(FLIGHT_FILE_PATH));
            FlightEntity[] flights = objectMapper.readValue(jsonData, FlightEntity[].class);

            Optional<FlightEntity> oneFlight = Arrays.stream(flights).filter(predicate).findFirst();
            return oneFlight;
        } catch (IOException e) {
            System.out.println("Error while reading flights from file: " + e.getMessage());
        }
        return Optional.empty();
    }

    @Override
    public List<FlightEntity> getAll() {
        try {
            FileReader fr = new FileReader(file);
            BufferedReader x = new BufferedReader(fr);
            String jsonData = x.readLine();
            if (jsonData != null && !jsonData.isBlank()) {
                FlightEntity[] flights = objectMapper.readValue(jsonData, FlightEntity[].class);
                x.close();
                var tempList = Arrays.asList(flights);
                return new ArrayList<>(tempList);
            }
            x.close();
        } catch (IOException e) {
            System.out.println("Error while reading flights from file: " + e.getMessage());
        }
        return new ArrayList<>();
    }
}
