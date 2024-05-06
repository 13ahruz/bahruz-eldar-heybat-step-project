package az.edu.turing.stepProjBookingApp.dao;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;

public interface DAO<T> {
  boolean save(List<T> t);
  Collection<T> getAll();
  Optional<T> getOneBy(Predicate<T> predicate);
  List<T> getAllBy(Predicate<T> predicate);
}
