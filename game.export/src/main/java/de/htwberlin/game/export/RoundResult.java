package de.htwberlin.game.export;

import de.htwberlin.userManager.export.User;

public class RoundResult {
    /**
     * Foreign key of the chosen answer.
     */
    private GameAnswer chosenAnswer;

    /**
     * Foreign key of the user.
     */
    private User user;

    /**
     * Constructor for RoundResult.
     * @param chosenAnswer Foreign key of the chosen answer.
     * @param user Foreign key of the user.
     */
    public RoundResult(GameAnswer chosenAnswer, User user) {
        this.chosenAnswer = chosenAnswer;
        this.user = user;
    }

    public GameAnswer getChosenAnswer() {
        return chosenAnswer;
    }

    public void setChosenAnswer(GameAnswer chosenAnswer) {
        this.chosenAnswer = chosenAnswer;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
