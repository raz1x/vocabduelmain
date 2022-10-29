package de.htwberlin.game.export;

public class GameQuestion {
    /**
     * ID of the question.
     */
    private int gameQuestionId;

    /**
     * Foreign key of the game.
     */
    private int gameId;

    /**
     * Number of the round.
     */
    private int roundNumber;

    /**
     * Foreign key of the used vocab.
     */
    private int vocabId;

    /**
     * Foreign key of the true answer.
     */
    private int trueAnswerId;

    /**
     * Constructor for GameQuestion.
     * @param gameId Foreign key of the game.
     * @param roundNumber Number of the round.
     * @param vocabId Foreign key of the used vocab.
     * @param trueAnswerId Foreign key of the true answer.
     */
    public GameQuestion(int gameId, int roundNumber, int vocabId, int trueAnswerId) {
        this.gameQuestionId = 0;
        this.gameId = gameId;
        this.roundNumber = roundNumber;
        this.vocabId = vocabId;
        this.trueAnswerId = trueAnswerId;
    }

    public int getGameQuestionId() {
        return gameQuestionId;
    }

    public void setGameQuestionId(int gameQuestionId) {
        this.gameQuestionId = gameQuestionId;
    }

    public int getGameId() {
        return gameId;
    }

    public void setGameId(int gameId) {
        this.gameId = gameId;
    }

    public int getRoundNumber() {
        return roundNumber;
    }

    public void setRoundNumber(int roundNumber) {
        this.roundNumber = roundNumber;
    }

    public int getVocabId() {
        return vocabId;
    }

    public void setVocabId(int vocabId) {
        this.vocabId = vocabId;
    }

    public int getTrueAnswerId() {
        return trueAnswerId;
    }

    public void setTrueAnswerId(int trueAnswerId) {
        this.trueAnswerId = trueAnswerId;
    }
}
