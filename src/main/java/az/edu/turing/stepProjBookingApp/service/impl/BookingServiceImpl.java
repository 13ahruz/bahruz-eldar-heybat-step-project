//package az.edu.turing.stepProjBookingApp.service.impl;
//
//import az.edu.turing.stepProjBookingApp.dao.BookingDao;
//import az.edu.turing.stepProjBookingApp.model.entity.BookingEntity;
//import az.edu.turing.stepProjBookingApp.service.BookingService;
//
//import java.util.List;
//import java.util.Optional;
//import java.util.function.Predicate;
//
//public class BookingServiceImpl implements BookingService {
//    private BookingDao bookingDao;
//
//    public BookingServiceImpl(BookingDao bookingDao) {
//        this.bookingDao = bookingDao;
//    }
//
//    @Override
//    public boolean bookAReservation(String firstName, String secondName, long flightId) {
//        List<BookingEntity> list = bookingDao.getAll();
//        BookingEntity bookingEntity = new BookingEntity(firstName, secondName, flightId);
//        list.add(bookingEntity);
//        return bookingDao.save(list);
//    }
//
//    @Override
//    public boolean cancelAReservation(long id) {
//        List<BookingEntity> allReservation = bookingDao.getAll();
//        Predicate<BookingEntity> bookingEntityPredicate = bookingEntity -> bookingEntity.getFlightId() == id;
//        allReservation.remove(bookingEntityPredicate);
//        return bookingDao.save(allReservation);
//    }
//
////    @Override
////    public Optional<List<BookingEntity>> getMyReservations(String firstName, String secondName) {
////        List<BookingEntity> allReservation = bookingDao.getAll();
////        Predicate<List<BookingEntity>> bookingEntityPredicate = bookingEntity -> bookingEntity.getFirst().getFirstName() == firstName && bookingEntity.getFirst().getSecondName() == secondName;
////        allReservation.
////        Optional<List<BookingEntity>> myReservations = allReservation.filter(bookingEntityPredicate);
////        return myReservations;
////    }
//}