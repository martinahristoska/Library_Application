package veb.seminarska.model.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class FavoriteBooksNotFoundException extends RuntimeException {

    public FavoriteBooksNotFoundException(Long id) {
        super(String.format("Favorite Books section with id: %d was not found", id));
    }
}