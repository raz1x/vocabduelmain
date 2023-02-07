package de.htwberlin.manageGame.export;

import de.htwberlin.userManager.export.User;
import jakarta.persistence.*;

@Entity
@Table(name = "RoundResult")
@NamedQuery(name = "RoundResult.selectCorrectRoundResults", query = "SELECT rr FROM RoundResult rr JOIN rr.chosenAnswer ga JOIN ga.gameQuestion gq JOIN gq.round r JOIN r.game g WHERE g.gameId = :gameId AND rr.user = :user AND rr.isCorrect = true")
public class RoundResult {
    /**
     * The ID of the round result.
     */
    @Id @GeneratedValue(strategy = GenerationType.AUTO)
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
     * If the answer is correct.
     */
    @Column(name = "isCorrect")
    private boolean isCorrect;

    @Version
    @Column(name = "version")
    private int version;

    /**
     * Default constructor for RoundResult.
     */
    public RoundResult() {

    }
    /**
     * Constructor for RoundResult.
     * @param chosenAnswer Foreign key of the chosen answer.
     * @param user Foreign key of the user.
     * @param isCorrect If the answer is correct.
     */
    public RoundResult(GameAnswer chosenAnswer, User user, boolean isCorrect) {
        this.chosenAnswer = chosenAnswer;
        this.user = user;
        this.isCorrect = isCorrect;
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

    public boolean isCorrect() {
        return isCorrect;
    }

    public void setCorrect(boolean isCorrect) {
        this.isCorrect = isCorrect;
    }

    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }
}
