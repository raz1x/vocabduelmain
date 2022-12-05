package de.htwberlin.game.export;

import de.htwberlin.vocab.export.CategoryNotFoundException;
import de.htwberlin.vocab.export.VocabListNotFoundException;
import de.htwberlin.vocab.export.VocabNotFoundException;

import java.util.List;

public interface ManageGame {
    /**
     * Creates a new game.
     * @param user1Id The ID of the first user.
     * @param user2Id The ID of the second user.
     * @return The Game object.
     */
    public Game createGame(int user1Id, int user2Id) throws UserDoesNotExistException;

    /**
     * Continues an existing game.
     * @param gameId The ID of the game.
     * @return The Game object.
     */
    public Game continueGame(int gameId) throws GameDoesNotExistException;

    /**
     * Finishes a game.
     * @param gameId The ID of the game.
     */
    public void endGame(int gameId) throws GameDoesNotExistException;

    /**
     * Creates a new round.
     * @param gameId Foreign key of the game.
     * @param round The round number of the game.
     * @param categoryId Foreign Key of the used category.
     * @return The Round object.
     */
    public Round createRound(int gameId, int round, int categoryId) throws Exception;

    /**
     * Creates a new RoundResult.
     * @param chosenAnswerId Foreign key of the chosen answer.
     * @param userId Foreign key of the user.
     * @return The RoundResult object.
     */
    public RoundResult createRoundResult(int chosenAnswerId, int userId) throws UserDoesNotExistException;

    /**
     * Returns a list of generated questions.
     * @param categoryId Foreign key of the category.
     * @param gameId Foreign key of the game.
     * @param round The round of the game.
     * @return A list of GameQuestion objects.
     */
    public List<GameQuestion> generateQuestions(int categoryId, int gameId, Round round) throws Exception;

    /**
     * Returns a list of generated answers.
     * @param gameQuestionId Foreign key of the GameQuestion.
     * @return A list of GameAnswer objects.
     */
    public List<GameAnswer> generateAnswers(int gameQuestionId) throws GameQuestionDoesNotExistException, VocabNotFoundException;

    /**
     * Returns the next Question.
     * @param previousGameQuestionId Id of the previous GameQuestion.
     * @return The GameQuestion object.
     */
    public GameQuestion getNextQuestion(int previousGameQuestionId) throws GameDoesNotExistException, GameQuestionDoesNotExistException, RoundDoesNotExistException;

    /**
     * Locks in an answer.
     * @param gameAnswerId Foreign key of the GameAnswer.
     * @param userId Foreign key of the user.
     * @throws GameAnswerDoesNotExistException If the game answer does not exist.
     * @throws UserDoesNotExistException If the user does not exist.
     */
    public void lockInAnswer(int gameAnswerId, int userId) throws GameAnswerDoesNotExistException, UserDoesNotExistException;

}
