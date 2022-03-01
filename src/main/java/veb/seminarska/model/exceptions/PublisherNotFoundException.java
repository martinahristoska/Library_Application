package veb.seminarska.model.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.NOT_FOUND)
public class PublisherNotFoundException extends RuntimeException {

    public PublisherNotFoundException(Long id) {
        super(String.format("The publisher with id: %d was not found", id));
    }
}
