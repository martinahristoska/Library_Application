package veb.seminarska.repository.jpa;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import veb.seminarska.model.Book;
import veb.seminarska.model.Rentals;
import veb.seminarska.model.User;
import veb.seminarska.model.enumerations.RentalStatus;

import java.util.Optional;

@Repository
public interface RentalRepository extends JpaRepository<Rentals, Long> {
    Optional<Rentals> findByUser(Rentals booksToRead);

    Rentals findByUser_Name(String username);

    Optional<Rentals> findByUserAndStatus(User user, RentalStatus status);

    @Override
    <S extends Rentals> S save(S s);

    Rentals save(Book book);
}
