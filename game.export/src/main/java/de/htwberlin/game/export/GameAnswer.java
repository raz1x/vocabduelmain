package de.htwberlin.game.export;

public class GameAnswer {
    /**
     * The ID of the answer.
     */
    private int gameAnswerId;

    /**
     * Foreign key of the question.
     */
    private int gameQuestionId;

    /**
     * Foreign key of the translation.
     */
    private int translationId;

    /**
     * Constructor for GameAnswer.
     * @param gameQuestionId Foreign key of the question.
     * @param translationId Foreign key of the translation.
     */
    public GameAnswer(int gameQuestionId, int translationId) {
        this.gameQuestionId = gameQuestionId;
        this.translationId = translationId;
    }

    public int getGameAnswerId() {
        return gameAnswerId;
    }

    public void setGameAnswerId(int gameAnswerId) {
        this.gameAnswerId = gameAnswerId;
    }

    public int getGameQuestionId() {
        return gameQuestionId;
    }

    public void setGameQuestionId(int gameQuestionId) {
        this.gameQuestionId = gameQuestionId;
    }

    public int getTranslationId() {
        return translationId;
    }

    public void setTranslationId(int translationId) {
        this.translationId = translationId;
    }
}
