package de.htwberlin.manageGame.export;

public class RoundDoesNotExistException extends Exception {
    public RoundDoesNotExistException(String message) {
        super(message);
    }
}
