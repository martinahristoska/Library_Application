package veb.seminarska.service.impl;

import org.springframework.stereotype.Service;
import veb.seminarska.model.Book;
import veb.seminarska.model.FavoriteBooks;
import veb.seminarska.model.User;
import veb.seminarska.model.enumerations.FavoriteBooksStatus;
import veb.seminarska.model.exceptions.BookAlreadyInFavoriteBooks;
import veb.seminarska.model.exceptions.BookNotFoundException;
import veb.seminarska.model.exceptions.RentalNotFoundException;
import veb.seminarska.model.exceptions.UserNotFoundException;
import veb.seminarska.repository.jpa.FavoriteBooksRepository;
import veb.seminarska.repository.jpa.UserRepository;
import veb.seminarska.service.BookService;
import veb.seminarska.service.FavoriteBooksService;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class FavoriteBooksServiceImpl implements FavoriteBooksService {

    private final FavoriteBooksRepository favoriteBooksRepository;
    private final UserRepository userRepository;
    private final BookService bookService;

    public FavoriteBooksServiceImpl(FavoriteBooksRepository favoriteBooksRepository, UserRepository userRepository, BookService bookService) {
        this.favoriteBooksRepository = favoriteBooksRepository;
        this.userRepository = userRepository;
        this.bookService = bookService;
    }

    @Override
    public FavoriteBooks findByUser_Name(String username) {
        return this.favoriteBooksRepository.findByUser_Name(username);
    }

    @Override
    public List<Book> listAllBooksInFavoriteBooks(Long cartId) {
        if (!this.favoriteBooksRepository.findById(cartId).isPresent())
            throw new RentalNotFoundException(cartId);
        return this.favoriteBooksRepository.findById(cartId).get().getBooks();
    }

    @Override
    public FavoriteBooks getActiveMyFavoriteBooks(String username) {
        User user = this.userRepository.getUserByUsername(username);
                //.orElseThrow(() -> new UserNotFoundException());

        return this.favoriteBooksRepository
                .findByUserAndStatus(user, FavoriteBooksStatus.CREATED)
                .orElseGet(() -> {
                    FavoriteBooks booksToRead = new FavoriteBooks(user);
                    return this.favoriteBooksRepository.save(booksToRead);
                });
    }

    @Override
    public FavoriteBooks addBookToMyFavoriteBooks(String username, Long bookId) {
        FavoriteBooks booksToRead = this.getActiveMyFavoriteBooks(username);
        Book book = this.bookService.findById(bookId);
                //.orElseThrow(() -> new BookNotFoundException(bookId));
        if (booksToRead.getBooks()
                .stream().filter(i -> i.getId().equals(bookId))
                .collect(Collectors.toList()).size() > 0)
            throw new BookAlreadyInFavoriteBooks(bookId, username);
        booksToRead.getBooks().add(book);
        return this.favoriteBooksRepository.save(booksToRead);
    }
}
