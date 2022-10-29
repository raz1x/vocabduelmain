package de.htwberlin.game.export;

public class RoundResult {
    /**
     * Foreign key of the chosen answer.
     */
    private int chosenAnswerId;

    /**
     * Foreign key of the user.
     */
    private int userId;

    /**
     * Constructor for RoundResult.
     * @param chosenAnswerId Foreign key of the chosen answer.
     * @param userId Foreign key of the user.
     */
    public RoundResult(int chosenAnswerId, int userId) {
        this.chosenAnswerId = chosenAnswerId;
        this.userId = userId;
    }

    public int getChosenAnswerId() {
        return chosenAnswerId;
    }

    public void setChosenAnswerId(int chosenAnswerId) {
        this.chosenAnswerId = chosenAnswerId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }
}
