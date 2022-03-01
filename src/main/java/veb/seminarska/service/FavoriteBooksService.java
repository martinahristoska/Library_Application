package veb.seminarska.service;

import org.springframework.stereotype.Service;
import veb.seminarska.model.Book;
import veb.seminarska.model.FavoriteBooks;

import java.util.List;

@Service
public interface FavoriteBooksService {
    List<Book> listAllBooksInFavoriteBooks(Long favoriteBooksId);

    FavoriteBooks findByUser_Name(String username);

    FavoriteBooks getActiveMyFavoriteBooks(String username);

    FavoriteBooks addBookToMyFavoriteBooks(String username, Long bookId);
}
