package veb.seminarska.repository.jpa;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import veb.seminarska.model.Book;
import veb.seminarska.model.Category;
import veb.seminarska.model.Publisher;

import java.util.List;
import java.util.Optional;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {


    @Override
    List<Book> findAll();

    @Override
    void deleteById(Long aLong);

    void deleteByTitle(String title);

    Optional<Book> findByTitle(String title);

    List<Book> findAllByTitleLike(String name);

    List<Book> findByCategory(Category category);

    List<Book> findByPublisher(Publisher publisher);

}