package veb.seminarska.model.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.PRECONDITION_FAILED)
public class BookAlreadyRented extends RuntimeException {

    public BookAlreadyRented(Long id, String username) {
        super(String.format("The book with id: %d is already rented for user with email: %s", id, username));
    }
}
