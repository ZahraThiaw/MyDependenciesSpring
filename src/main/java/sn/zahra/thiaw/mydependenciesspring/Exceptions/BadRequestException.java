package sn.zahra.thiaw.mydependenciesspring.Exceptions;

public class BadRequestException extends RuntimeException {
    public BadRequestException(String message) {
        super(message);
    }
}