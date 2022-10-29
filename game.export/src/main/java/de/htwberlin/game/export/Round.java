package de.htwberlin.game.export;

public class Round {
    /**
     * Foreign key of the game.
     */
    private int gameId;

    /**
     * The round number of the game.
     */
    private int round;

    /**
     * Foreign Key of the used category.
     */
    private int categoryId;

    /**
     * The Constructor for Round.
     * @param gameId Foreign key of the game.
     * @param round The round number of the game.
     * @param categoryId Foreign Key of the used category.
     */
    public Round(int gameId, int round, int categoryId) {
        this.gameId = gameId;
        this.round = round;
        this.categoryId = categoryId;
    }

    public int getGameId() {
        return gameId;
    }

    public void setGameId(int gameId) {
        this.gameId = gameId;
    }

    public int getRound() {
        return round;
    }

    public void setRound(int round) {
        this.round = round;
    }

    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }
}
