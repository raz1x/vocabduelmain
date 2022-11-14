package de.htwberlin.game.export;

import de.htwberlin.vocab.export.Category;

public class Round {
    /**
     * Foreign key of the game.
     */
    private Game game;

    /**
     * The round number of the game.
     */
    private int round;

    /**
     * Foreign Key of the used category.
     */
    private Category category;

    /**
     * The Constructor for Round.
     * @param game The game object.
     * @param round The round number of the game.
     * @param category Foreign Key of the used category.
     */
    public Round(Game game, int round, Category category) {
        this.game = game;
        this.round = round;
        this.category = category;
    }

    public Game getGame() {
        return game;
    }

    public void setGame(Game game) {
        this.game = game;
    }

    public int getRound() {
        return round;
    }

    public void setRound(int round) {
        this.round = round;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }
}
