package de.htwberlin.manageGame.export;

import de.htwberlin.manageVocab.export.Translation;
import jakarta.persistence.*;

@Entity
@Table(name = "GameAnswer")
public class GameAnswer {
    /**
     * The ID of the answer.
     */
    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "gameAnswerId")
    private int gameAnswerId;

    /**
     * Foreign key of the question.
     */
    @ManyToOne
    @JoinColumn(name = "gameQuestionId")
    private GameQuestion gameQuestion;

    /**
     * Foreign key of the translation.
     */
    @ManyToOne
    @JoinColumn(name = "translationId")
    private Translation translation;

    @Version
    @Column(name = "version")
    private int version;

    /**
     * Default constructor for GameAnswer.
     */
    public GameAnswer() {

    }
    /**
     * Constructor for GameAnswer.
     * @param gameQuestion The GameQuestion object.
     * @param translation The Translation object.
     */
    public GameAnswer(GameQuestion gameQuestion, Translation translation) {
        this.gameAnswerId = 0;
        this.gameQuestion = gameQuestion;
        this.translation = translation;
    }

    public int getGameAnswerId() {
        return gameAnswerId;
    }

    public void setGameAnswerId(int gameAnswerId) {
        this.gameAnswerId = gameAnswerId;
    }

    public GameQuestion getGameQuestion() {
        return gameQuestion;
    }

    public void setGameQuestion(GameQuestion gameQuestion) {
        this.gameQuestion = gameQuestion;
    }

    public Translation getTranslation() {
        return translation;
    }

    public void setTranslation(Translation translation) {
        this.translation = translation;
    }
}
