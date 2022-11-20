package de.htwberlin.game.export;

import de.htwberlin.userManager.export.User;
import jakarta.persistence.*;

@Entity
@Table(name = "RoundResult")
public class RoundResult {
    /**
     * The ID of the round result.
     */
    @Id @GeneratedValue
    @Column(name = "roundResultId")
    private int roundResultId;
    /**
     * Foreign key of the chosen answer.
     */
    @ManyToOne
    @JoinColumn(name = "chosenAnswerId")
    private GameAnswer chosenAnswer;

    /**
     * Foreign key of the user.
     */
    @ManyToOne
    @JoinColumn(name = "userId")
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

    public int getRoundResultId() {
        return roundResultId;
    }

    public void setRoundResultId(int roundResultId) {
        this.roundResultId = roundResultId;
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
