package de.htwberlin.game.export;

import de.htwberlin.vocab.export.Category;
import javax.persistence.*;

@Entity
@Table(name = "Round")
public class Round {
    /**
     * ID of the round.
     */
    @Id @GeneratedValue
    @Column(name = "roundId")
    private int roundId;
    /**
     * Foreign key of the game.
     */
    @ManyToOne
    @JoinColumn(name = "gameId")
    private Game game;

    /**
     * The round number of the game.
     */
    @Column(name = "roundNumber")
    private int roundNumber;

    /**
     * Foreign Key of the used category.
     */
    @ManyToOne
    @JoinColumn(name = "categoryId")
    private Category category;

    /**
     * The Constructor for Round.
     * @param game The game object.
     * @param roundNumber The round number of the game.
     * @param category Foreign Key of the used category.
     */
    public Round(Game game, int roundNumber, Category category) {
        this.game = game;
        this.roundNumber = roundNumber;
        this.category = category;
    }

    public int getRoundId() {
        return roundId;
    }

    public void setRoundId(int roundId) {
        this.roundId = roundId;
    }

    public Game getGame() {
        return game;
    }

    public void setGame(Game game) {
        this.game = game;
    }

    public int getRoundNumber() {
        return roundNumber;
    }

    public void setRoundNumber(int roundNumber) {
        this.roundNumber = roundNumber;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }
}
