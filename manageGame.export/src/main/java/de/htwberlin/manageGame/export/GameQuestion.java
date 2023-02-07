package de.htwberlin.manageGame.export;

import de.htwberlin.manageVocab.export.Translation;
import de.htwberlin.manageVocab.export.Vocab;
import jakarta.persistence.*;

@Entity
@Table(name = "GameQuestion")
@NamedQuery(name = "GameQuestion.getByRound", query = "SELECT gq FROM GameQuestion gq WHERE gq.round = :round")
public class GameQuestion {
    /**
     * ID of the question.
     */
    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "gameQuestionId")
    private int gameQuestionId;
    /**
     * Number of the round.
     */
    @ManyToOne
    @JoinColumn(name = "round")
    private Round round;

    /**
     * Foreign key of the used vocab.
     */
    @ManyToOne
    @JoinColumn(name = "vocabId")
    private Vocab vocab;

    /**
     * Foreign key of the true answer.
     */
    @ManyToOne(targetEntity = Translation.class)
    @JoinColumn(name = "trueAnswerId")
    private Translation trueAnswer;

    /**
     * Index of the game question in the round.
     */
    @Column(name = "questionNumber")
    private int questionNumber;

    @Version
    @Column(name = "version")
    private int version;

    /**
     * Default constructor for GameQuestion.
     */
    public GameQuestion() {

    }
    /**
     * Constructor for GameQuestion.
     * @param round The Round object.
     * @param vocab The Vocab object.
     * @param trueAnswer The foreign key of the true answer.
     * @param questionNumber The index of the question in the round.
     */
    public GameQuestion(Round round, Vocab vocab, Translation trueAnswer, int questionNumber) {
        this.gameQuestionId = 0;
        this.round = round;
        this.vocab = vocab;
        this.trueAnswer = trueAnswer;
        this.questionNumber = questionNumber;
    }

    public int getGameQuestionId() {
        return gameQuestionId;
    }

    public void setGameQuestionId(int gameQuestionId) {
        this.gameQuestionId = gameQuestionId;
    }

    public Round getRound() {
        return round;
    }

    public void setRound(Round round) {
        this.round = round;
    }

    public Vocab getVocab() {
        return vocab;
    }

    public void setVocab(Vocab vocab) {
        this.vocab = vocab;
    }

    public Translation getTrueAnswer() {
        return trueAnswer;
    }

    public void setTrueAnswer(Translation trueAnswer) {
        this.trueAnswer = trueAnswer;
    }

    public int getQuestionNumber() {
        return questionNumber;
    }

    public void setQuestionNumber(int index) {
        this.questionNumber = index;
    }

    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }
}
