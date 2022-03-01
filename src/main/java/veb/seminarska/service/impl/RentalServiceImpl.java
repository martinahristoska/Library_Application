package veb.seminarska.service.impl;

import org.springframework.stereotype.Service;
import veb.seminarska.model.Book;
import veb.seminarska.model.Rentals;
import veb.seminarska.model.User;
import veb.seminarska.model.enumerations.RentalStatus;
import veb.seminarska.model.exceptions.BookAlreadyRented;
import veb.seminarska.model.exceptions.BookNotFoundException;
import veb.seminarska.model.exceptions.RentalNotFoundException;
import veb.seminarska.model.exceptions.UserNotFoundException;
import veb.seminarska.repository.jpa.RentalRepository;
import veb.seminarska.repository.jpa.UserRepository;
import veb.seminarska.service.BookService;
import veb.seminarska.service.RentalService;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class RentalServiceImpl implements RentalService {

    private final RentalRepository rentalRepository;
    private final UserRepository userRepository;
    private final BookService bookService;

    public RentalServiceImpl(RentalRepository rentalRepository, UserRepository userRepository, BookService bookService) {
        this.rentalRepository = rentalRepository;
        this.userRepository = userRepository;
        this.bookService = bookService;
    }

    @Override
    public List<Book> listAllBooksInMyRentals(Long cartId) {
        if (!this.rentalRepository.findById(cartId).isPresent())
            throw new RentalNotFoundException(cartId);
        return this.rentalRepository.findById(cartId).get().getBooks();
    }

    @Override
    public Rentals findByUser_Name(String username) {
        return this.rentalRepository.findByUser_Name(username);
    }

    @Override
    public Rentals getActiveRentals(String username) {
        User user = this.userRepository.getUserByUsername(username);
                //.orElseThrow(() -> new UserNotFoundException());

        return this.rentalRepository
                .findByUserAndStatus(user, RentalStatus.CREATED)
                .orElseGet(() -> {
                    Rentals booksToRead = new Rentals(user);
                    return this.rentalRepository.save(booksToRead);
                });
    }

    @Override
    public Rentals addBookToRentals(String username, Long bookId) {
        Rentals booksToRead = this.getActiveRentals(username);
        Book book = this.bookService.findById(bookId);
                //.orElseThrow(() -> new BookNotFoundException(bookId));
        if (booksToRead.getBooks()
                .stream().filter(i -> i.getId().equals(bookId))
                .collect(Collectors.toList()).size() > 0)
            throw new BookAlreadyRented(bookId, username);
        booksToRead.getBooks().add(book);
        return this.rentalRepository.save(booksToRead);
    }
}