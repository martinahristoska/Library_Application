package veb.seminarska.service;

import org.springframework.stereotype.Service;
import veb.seminarska.model.Book;
import veb.seminarska.model.Rentals;

import java.util.List;

@Service
public interface RentalService {
    List<Book> listAllBooksInMyRentals(Long favoriteBooksId);

    Rentals findByUser_Name(String username);

    Rentals getActiveRentals(String username);

    Rentals addBookToRentals(String username, Long bookId);
}
