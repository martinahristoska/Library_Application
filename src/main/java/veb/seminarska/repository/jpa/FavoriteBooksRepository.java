package veb.seminarska.repository.jpa;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import veb.seminarska.model.Book;
import veb.seminarska.model.FavoriteBooks;
import veb.seminarska.model.User;
import veb.seminarska.model.enumerations.FavoriteBooksStatus;

import java.util.Optional;

@Repository
public interface FavoriteBooksRepository extends JpaRepository<FavoriteBooks, Long> {
    Optional<FavoriteBooks> findByUserAndStatus(User user, FavoriteBooksStatus status);

    @Override
    <S extends FavoriteBooks> S save(S s);

    FavoriteBooks save(Book book);

    FavoriteBooks findByUser_Name(String username);
}
