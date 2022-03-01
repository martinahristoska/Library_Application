package veb.seminarska.model.exceptions;

public class IllegalArgumentsException extends RuntimeException {
    public IllegalArgumentsException() {
        super("Please provide the necessary arguments");
    }
}
