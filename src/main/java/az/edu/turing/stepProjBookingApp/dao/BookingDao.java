package az.edu.turing.stepProjBookingApp.dao;

import az.edu.turing.stepProjBookingApp.model.dto.BookingDto;
import az.edu.turing.stepProjBookingApp.model.entity.BookingEntity;

import java.util.List;

public abstract class BookingDao implements DAO<BookingDto, BookingEntity, List<BookingDto>>{
}
