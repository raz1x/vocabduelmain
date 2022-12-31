package de.htwberlin.manageGame.export;

import de.htwberlin.manageVocab.export.*;

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
     * Gets an existing game from the database.
     * @param gameId The ID of the game.
     * @return The Game object.
     */
    public Game getGame(int gameId) throws GameDoesNotExistException;

    /**
     * Updates a game.
     * @param game The Game object.
     * @return The updated Game object.
     */
    public Game updateGame(Game game) throws GameDoesNotExistException;

    /**
     * Finishes a game.
     * @param gameId The ID of the game.
     */
    public void endGame(int gameId) throws GameDoesNotExistException;

    /**
     * Gets all ongoing games for a user.
     * @param userId The ID of the user.
     */
    public List<Game> getAllOngoingGamesForUser(int userId) throws GameDoesNotExistException, UserDoesNotExistException;

    /**
     * Creates a new round.
     * @param gameId Foreign key of the game.
     * @param round The round number of the game.
     * @param categoryId Foreign Key of the used category.
     * @return The Round object.
     */
    public Round createRound(int gameId, int round, int categoryId) throws GameDoesNotExistException, CategoryNotFoundException, GameDAOPersistenceException;

    /**
     * Gets a round from the database.
     * @param roundId Id of the round.
     * @return The round object.
     */
    public Round getRound(int roundId) throws RoundDoesNotExistException;

    /**
     * Updates a round.
     * @param round The round object.
     * @return The updated round object.
     */
    public Round updateRound(Round round) throws RoundDoesNotExistException;

    /**
     * Ends a round.
     * @param roundId Id of the round.
     */
    public void endRound(int roundId) throws RoundDoesNotExistException;

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
    public List<GameAnswer> generateAnswers(int gameQuestionId) throws GameQuestionDoesNotExistException, VocabNotFoundException, GameDAOPersistenceException;

    /**
     * Returns a list of all game questions for a round of a game.
     * @param gameId Foreign key of the game.
     * @param roundNumber The round number of the game.
     * @return A list of GameQuestion objects.
     */
    public List<GameQuestion> getGameQuestionsForRound(int gameId, int roundNumber) throws RoundDoesNotExistException;

    /**
     * Returns a list of all game answers for a game question.
     * @param gameQuestionId Foreign key of the game question.
     * @return A list of GameAnswer objects.
     */
    public List<GameAnswer> getGameAnswersForGameQuestion(int gameQuestionId) throws GameAnswerDoesNotExistException;

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
     * @param isCorrect If the answer is correct.
     * @throws GameAnswerDoesNotExistException If the game answer does not exist.
     * @throws UserDoesNotExistException If the user does not exist.
     */
    public void lockInAnswer(int gameAnswerId, int userId, boolean isCorrect) throws GameAnswerDoesNotExistException, UserDoesNotExistException;

    /**
     * Returns the score of a user.
     * @param userId Foreign key of the user.
     * @param gameId Foreign key of the game.
     * @return The score of the user.
     */
    public int getScoreForUser(int userId, int gameId) throws GameDoesNotExistException, UserDoesNotExistException;

    /**
     * Returns the latest round number of the game.
     * @param gameId Foreign key of the game.
     * @return The round number.
     */
    public int getLatestRoundForGame(int gameId) throws GameDoesNotExistException;

    /**
     * Returns the latest round of the game.
     * @param gameId Foreign key of the game.
     * @return The round object.
     */
    public Round getOngoingRoundForGame(int gameId) throws RoundDoesNotExistException;
}