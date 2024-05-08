//package az.edu.turing.stepProjBookingApp.service.impl;
//
//import az.edu.turing.stepProjBookingApp.dao.FlightDao;
//import az.edu.turing.stepProjBookingApp.model.dto.FlightDto;
//import az.edu.turing.stepProjBookingApp.model.entity.FlightEntity;
//import az.edu.turing.stepProjBookingApp.service.FlightService;
//
//import java.util.ArrayList;
//import java.util.List;
//import java.util.Optional;
//import java.util.function.Predicate;
//import java.util.stream.Collectors;
//
//public class FlightServiceImpl implements FlightService {
//    private final FlightDao flightDao;
//
//    public FlightServiceImpl(FlightDao flightDao) {
//        this.flightDao = flightDao;
//    }
//
//    @Override
//    public List<FlightDto> getAllFlights() {
//        List<FlightDto> flightsDto = flightDao.getAll().stream()
//                .map(flight -> new FlightDto(flight.getFirst().getDateAndTime(), flight.getFirst().getDestination(), flight.getFirst().getSeats(), flight.getFirst().getFlightId()))
//                .collect(Collectors.toList());
//        return flightsDto;
//    }
//
//    @Override
//    public Optional<List<FlightDto>> getAllByDest(String destination) {
//        Predicate<FlightEntity> destPredicate = flight -> flight.getDestination().equals(destination);
//        Optional<List<FlightEntity>> flightsById = flightDao.getAllBy(destPredicate);
//        return flightsById.map(flights ->
//                flights.stream()
//                        .map(flight -> new FlightDto(flight.getDateAndTime(), flight.getDestination(), flight.getSeats(), flight.getFlightId()))
//                        .collect(Collectors.toList())
//        );
//    }
//
//    @Override
//    public Optional<FlightDto> getFlightById(long id) {
//        Predicate<FlightEntity> predicateByName = flight -> flight.getFlightId() == id;
//        Optional<FlightEntity> flightById = flightDao.getOneBy(predicateByName);
//        if (flightById.isPresent()) {
//            FlightEntity flightEntity = flightById.get();
//            FlightDto flightDto = new FlightDto(flightEntity.getDateAndTime(), flightEntity.getDestination(), flightEntity.getSeats(), flightEntity.getFlightId());
//            return Optional.of(flightDto);
//        } else {
//            return Optional.empty();
//        }
//    }
//
//
//    @Override
//    public boolean createFlight(FlightDto flightDto) {
//        FlightEntity flightEntity = new FlightEntity(
//                flightDto.getDateAndTime(),
//                flightDto.getDestination(),
//                flightDto.getSeats()
//        );
//        List<FlightEntity> flights = flightDao.getAll().orElse(new ArrayList<>());
//        flights.add(flightEntity);
//        return flightDao.save(flights);
//    }
//}
