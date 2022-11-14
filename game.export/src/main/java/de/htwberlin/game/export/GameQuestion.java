package de.htwberlin.game.export;

import de.htwberlin.vocab.export.Vocab;

public class GameQuestion {
    /**
     * ID of the question.
     */
    private int gameQuestionId;

    /**
     * Foreign key of the game.
     */
    private Game game;

    /**
     * Number of the round.
     */
    private Round round;

    /**
     * Foreign key of the used vocab.
     */
    private Vocab vocab;

    /**
     * Foreign key of the true answer.
     */
    private int trueAnswerId;

    /**
     * Constructor for GameQuestion.
     * @param game The Game object.
     * @param round The Round object.
     * @param vocab The Vocab object.
     * @param trueAnswerId The foreign key of the true answer.
     */
    public GameQuestion(Game game, Round round, Vocab vocab, int trueAnswerId) {
        this.gameQuestionId = 0;
        this.game = game;
        this.round = round;
        this.vocab = vocab;
        this.trueAnswerId = trueAnswerId;
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

    public int getTrueAnswerId() {
        return trueAnswerId;
    }

    public void setTrueAnswerId(int trueAnswer) {
        this.trueAnswerId = trueAnswerId;
    }
}
