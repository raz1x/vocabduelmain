package de.htwberlin.game.export;

public class UserDoesNotExistException extends Exception {
    public UserDoesNotExistException(String message) {
        super(message);
    }
}
