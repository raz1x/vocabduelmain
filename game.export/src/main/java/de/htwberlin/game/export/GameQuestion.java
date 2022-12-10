package de.htwberlin.game.export;

import de.htwberlin.vocab.export.Translation;
import de.htwberlin.vocab.export.Vocab;
import jakarta.persistence.*;

@Entity
@Table(name = "GameQuestion")
public class GameQuestion {
    /**
     * ID of the question.
     */
    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "gameQuestionId")
    private int gameQuestionId;

    /**
     * Foreign key of the game.
     */
    @ManyToOne
    @JoinColumn(name = "gameId")
    private Game game;

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
    @ManyToOne(targetEntity = de.htwberlin.vocab.export.Translation.class)
    @JoinColumn(name = "trueAnswerId")
    private Translation trueAnswer;

    /**
     * Index of the game question in the round.
     */
    @Column(name = "questionNumber")
    private int questionNumber;

    /**
     * Default constructor for GameQuestion.
     */
    public GameQuestion() {

    }
    /**
     * Constructor for GameQuestion.
     * @param game The Game object.
     * @param round The Round object.
     * @param vocab The Vocab object.
     * @param trueAnswer The foreign key of the true answer.
     * @param questionNumber The index of the question in the round.
     */
    public GameQuestion(Game game, Round round, Vocab vocab, Translation trueAnswer, int questionNumber) {
        this.gameQuestionId = 0;
        this.game = game;
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

    public Game getGame() {
        return game;
    }

    public void setGame(Game game) {
        this.game = game;
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
}
