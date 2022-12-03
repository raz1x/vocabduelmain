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
    public Round createRound(int gameId, int round, int categoryId) throws GameDoesNotExistException, CategoryNotFoundException;

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
     * @param roundNumber The round number of the game.
     * @return A list of GameQuestion objects.
     */
    public List<GameQuestion> generateQuestions(int categoryId, int gameId, int roundNumber) throws GameDoesNotExistException, CategoryNotFoundException, VocabListNotFoundException, VocabNotFoundException, RoundDoesNotExistException;

    /**
     * Returns a list of generated answers.
     * @param gameQuestionId Foreign key of the GameQuestion.
     * @return A list of GameAnswer objects.
     */
    public List<GameAnswer> generateAnswers(int gameQuestionId) throws GameQuestionDoesNotExistException, VocabNotFoundException;

    /**
     * Returns the next Question.
     * @param gameId Foreign key of the game.
     * @param round The round number of the game.
     * @param previousGameQuestionId Foreign key of the previous GameQuestion.
     * @return The GameQuestion object.
     */
    public GameQuestion getNextQuestion(int gameId, int round, int previousGameQuestionId) throws GameDoesNotExistException, GameQuestionDoesNotExistException;

    /**
     * Locks in an answer.
     * @param gameId Foreign key of the game.
     * @param round The round number of the game.
     * @param gameQuestionId Foreign key of the GameQuestion.
     * @param gameAnswerId Foreign key of the GameAnswer.
     * @param userId Foreign key of the user.
     * @throws GameDoesNotExistException If the game does not exist.
     * @throws GameQuestionDoesNotExistException If the game question does not exist.
     * @throws GameAnswerDoesNotExistException If the game answer does not exist.
     */
    public void lockInAnswer(int gameId, int round, int gameQuestionId, int gameAnswerId, int userId) throws GameDoesNotExistException, GameQuestionDoesNotExistException, GameAnswerDoesNotExistException;

}
