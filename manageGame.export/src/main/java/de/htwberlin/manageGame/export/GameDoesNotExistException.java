package de.htwberlin.manageGame.export;

public class GameDoesNotExistException extends Exception {
    public GameDoesNotExistException(String message) {
        super(message);
    }
}
