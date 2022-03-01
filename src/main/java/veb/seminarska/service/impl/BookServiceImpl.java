package veb.seminarska.service.impl;

import org.springframework.stereotype.Service;
import veb.seminarska.model.Book;
import veb.seminarska.model.Category;
import veb.seminarska.model.Publisher;
import veb.seminarska.model.Rating;
import veb.seminarska.model.exceptions.BookNotFoundException;
import veb.seminarska.model.exceptions.CategoryNotFoundException;
import veb.seminarska.model.exceptions.PublisherNotFoundException;
import veb.seminarska.repository.jpa.BookRepository;
import veb.seminarska.repository.jpa.CategoryRepository;
import veb.seminarska.repository.jpa.PublisherRepository;
import veb.seminarska.repository.jpa.RatingRepository;
import veb.seminarska.service.BookService;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
public class BookServiceImpl implements BookService {

    private final BookRepository bookRepository;
    private final PublisherRepository publisherRepository;
    private final CategoryRepository categoryRepository;
    private final RatingRepository ratingRepository;

    public BookServiceImpl(BookRepository bookRepository, PublisherRepository publisherRepository, CategoryRepository categoryRepository, RatingRepository ratingRepository) {
        this.bookRepository = bookRepository;
        this.publisherRepository = publisherRepository;
        this.categoryRepository = categoryRepository;
        this.ratingRepository = ratingRepository;
    }

    @Override
    public List<Book> findAll() {
        return this.bookRepository.findAll();
    }

    @Override
    public Book findById(Long id) {
        return this.bookRepository.findById(id).orElseThrow(BookNotFoundException::new);
    }

    @Override
    public Optional<Book> findByTitle(String title) {
        return this.bookRepository.findByTitle(title);
    }

    @Override
    @Transactional
    public Optional<Book> save(String title, String authorName, Long categoryId, Long publisherId, String cover) {
        Category category = this.categoryRepository.findById(categoryId)
                .orElseThrow(() -> new CategoryNotFoundException(categoryId));
        Publisher publisher = this.publisherRepository.findById(publisherId)
                .orElseThrow(() -> new PublisherNotFoundException(publisherId));

        this.bookRepository.deleteByTitle(title);
        return Optional.of(this.bookRepository.save(new Book(title, authorName, category, publisher, cover)));
    }

    @Override
    public void deleteById(Long id) {
        this.bookRepository.deleteById(id);
    }

    @Override
    public Book update(Long id, String title, String authorName, Long categoryId, Long publisherId, String cover) {
        Book book = findById(id);
        book.setTitle(title);
        book.setAuthorName(authorName);
        Category category = this.categoryRepository.findById(categoryId)
                .orElseThrow(() -> new CategoryNotFoundException(categoryId));
        Publisher publisher = this.publisherRepository.findById(publisherId)
                .orElseThrow(() -> new PublisherNotFoundException(publisherId));
        book.setCategory(category);
        book.setPublisher(publisher);
        book.setCover(cover);
        return bookRepository.save(book);
    }

    @Override
    public List<Book> listBooksByTitle(String title) {
        String nameLike = "%" + title + "%";
        if (title!=null)
        {
            return bookRepository.findAllByTitleLike(nameLike);
        }
        else{
            return bookRepository.findAll();
        }
    }

    @Override
    public Book addRating(Long ratingID, Long bookID) {
        Book book = this.bookRepository.findById(bookID).get();
        Rating rating = this.ratingRepository.findById(ratingID).get();
        if(book.getRatings().contains(rating)) return book;
        book.getRatings().add(rating);
        return this.bookRepository.save(book);
    }
}
