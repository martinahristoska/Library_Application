package veb.seminarska.model.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.PRECONDITION_FAILED)
public class BookAlreadyInFavoriteBooks extends RuntimeException {

    public BookAlreadyInFavoriteBooks(Long id, String username) {
        super(String.format("The book with id: %d already exists in favorite books section for user with email: %s", id, username));
    }
}
