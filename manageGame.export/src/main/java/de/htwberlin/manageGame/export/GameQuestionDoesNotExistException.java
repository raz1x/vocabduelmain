package de.htwberlin.manageGame.export;

public class GameQuestionDoesNotExistException extends Exception {
    public GameQuestionDoesNotExistException(String message) {
        super(message);
    }
}
