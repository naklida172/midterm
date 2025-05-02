package kg.alatoo.midterm.exceptions;

public class InvalidCredentialsException extends RuntimeException {
    public InvalidCredentialsException() {
        super("Incorrect login or password");
    }
}
