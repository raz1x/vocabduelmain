package de.htwberlin.game.export;

import de.htwberlin.vocab.export.Translation;

public class GameAnswer {
    /**
     * The ID of the answer.
     */
    private int gameAnswerId;

    /**
     * Foreign key of the question.
     */
    private GameQuestion gameQuestion;

    /**
     * Foreign key of the translation.
     */
    private Translation translation;

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

    public int getGameAnswer() {
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
