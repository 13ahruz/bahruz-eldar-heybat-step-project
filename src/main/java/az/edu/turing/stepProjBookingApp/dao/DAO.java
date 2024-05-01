package az.edu.turing.stepProjBookingApp.dao;

import java.util.Collection;
import java.util.Optional;
import java.util.function.Predicate;

public interface DAO<T, E, Q> {
  boolean save(Q q);
  Collection<T> getAll();
  Optional<T> getOneBy(Predicate<E> predicate);
  Collection<T> getAllBy(Predicate<E> predicate);

}
