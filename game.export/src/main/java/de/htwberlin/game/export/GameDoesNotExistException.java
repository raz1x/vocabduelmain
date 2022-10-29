package de.htwberlin.game.export;

public class GameDoesNotExistException extends Exception {
    public GameDoesNotExistException(String message) {
        super(message);
    }
}
