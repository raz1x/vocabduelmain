package de.htwberlin.game.export;

import de.htwberlin.vocab.export.Translation;
import de.htwberlin.vocab.export.Vocab;
import javax.persistence.*;

@Entity
@Table(name = "GameQuestion")
public class GameQuestion {
    /**
     * ID of the question.
     */
    @Id @GeneratedValue
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
     * Constructor for GameQuestion.
     * @param game The Game object.
     * @param round The Round object.
     * @param vocab The Vocab object.
     * @param trueAnswer The foreign key of the true answer.
     */
    public GameQuestion(Game game, Round round, Vocab vocab, Translation trueAnswer) {
        this.gameQuestionId = 0;
        this.game = game;
        this.round = round;
        this.vocab = vocab;
        this.trueAnswer = trueAnswer;
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

    public Translation getTrueAnswerId() {
        return trueAnswer;
    }

    public void setTrueAnswerId(Translation trueAnswer) {
        this.trueAnswer = trueAnswer;
    }
}
