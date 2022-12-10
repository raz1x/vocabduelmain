package de.htwberlin.game.export;

import de.htwberlin.userManager.export.User;

import java.util.List;

public interface GameDAO {
    /**
     * Saves a game in the database.
     * @param game The game to be saved.
     * @return The saved game.
     */
    public Game saveGame(Game game) throws GameDAOPersistenceException;

    /**
     * Updates a game from the database.
     * @param game The game to be updated.
     * @return The updated game.
     */
    public Game updateGame(Game game) throws GameDAOPersistenceException;

    /**
     * Deletes a game from the database.
     * @param game The game to be deleted.
     */
    public void deleteGame(Game game) throws GameDAOPersistenceException;

    /**
     * Returns a game from the database.
     * @param gameId The id of the game.
     * @return The game.
     */
    public Game getGame(int gameId) throws GameDoesNotExistException;

    /**
     * Returns all ongoing games for a user from the database.
     * @param user The user.
     * @return The list of games.
     */
    public List<Game> getOngoingGamesForUser(User user) throws GameDoesNotExistException;

    /**
     * Saves a game answer in the database.
     * @param gameAnswer The game answer to be saved.
     * @return The saved game answer.
     */
    public GameAnswer saveGameAnswer(GameAnswer gameAnswer) throws GameDAOPersistenceException;

    /**
     * Updates a game answer from the database.
     * @param gameAnswer The game answer to be updated.
     * @return The updated game answer.
     */
    public GameAnswer updateGameAnswer(GameAnswer gameAnswer) throws GameDAOPersistenceException;

    /**
     * Deletes a game answer from the database.
     * @param gameAnswer The game answer to be deleted.
     */
    public void deleteGameAnswer(GameAnswer gameAnswer) throws GameDAOPersistenceException;

    /**
     * Returns a game answer from the database.
     * @param gameAnswerId The id of the game answer.
     * @return The game answer.
     */
    public GameAnswer getGameAnswer(int gameAnswerId) throws GameAnswerDoesNotExistException;

    /**
     * Returns all game answers for a game question from the database.
     * @return The game answers.
     */
    public List<GameAnswer> getGameAnswersForGameQuestion(int gameQuestionId) throws GameAnswerDoesNotExistException;

    /**
     * Saves a game question in the database.
     * @param gameQuestion The game question to be saved.
     * @return The saved game question.
     */
    public GameQuestion saveGameQuestion(GameQuestion gameQuestion) throws GameDAOPersistenceException;

    /**
     * Updates a game question from the database.
     * @param gameQuestion The game question to be updated.
     * @return The updated game question.
     */
    public GameQuestion updateGameQuestion(GameQuestion gameQuestion) throws GameDAOPersistenceException;

    /**
     * Deletes a game question from the database.
     * @param gameQuestion The game question to be deleted.
     */
    public void deleteGameQuestion(GameQuestion gameQuestion) throws GameDAOPersistenceException;

    /**
     * Returns a game question from the database.
     * @param gameQuestionId The id of the game question.
     * @return The game question.
     */
    public GameQuestion getGameQuestion(int gameQuestionId) throws GameQuestionDoesNotExistException;

    /**
     * Returns all game questions for a given round from the database.
     * @param gameId The id of the game.
     * @param roundNumber The number of the round.
     * @return The game questions.
     */
    public List<GameQuestion> getGameQuestionsForRound(int gameId, int roundNumber) throws GameDoesNotExistException, RoundDoesNotExistException;

    /**
     * Saves a round in the database.
     * @param round The round to be saved.
     * @return The saved round.
     */
    public Round saveRound(Round round) throws GameDAOPersistenceException;

    /**
     * Updates a round from the database.
     * @param round The round to be updated.
     * @return The updated round.
     */
    public Round updateRound(Round round) throws GameDAOPersistenceException;

    /**
     * Deletes a round from the database.
     * @param round The round to be deleted.
     */
    public void deleteRound(Round round) throws GameDAOPersistenceException;

    /**
     * Returns a round from the database.
     * @param gameId The id of the game.
     * @param roundNumber The number of the round.
     * @return The round.
     */
    public Round getRound(int gameId, int roundNumber) throws RoundDoesNotExistException;

    /**
     * Returns a round from the database.
     * @param roundId The id of the round.
     * @return The round.
     */
    public Round getRoundById(int roundId) throws RoundDoesNotExistException;

    /**
     * Returns the number of rounds for a given game from the database.
     * @param gameId The id of the game.
     * @return The number of rounds.
     */
    public int getNumberOfRounds(int gameId) throws GameDoesNotExistException;

    /**
     * Returns the ongoing round for a game from the database.
     * @param gameId The id of the game.
     * @return The round.
     */
    public Round getOngoingRound(int gameId) throws RoundDoesNotExistException;

    /**
     * Saves a round result in the database.
     * @param roundResult The round result to be saved.
     * @return The saved round result.
     */
    public RoundResult saveRoundResult(RoundResult roundResult) throws GameDAOPersistenceException;

    /**
     * Updates a round result from the database.
     * @param roundResult The round result to be updated.
     * @return The updated round result.
     */
    public RoundResult updateRoundResult(RoundResult roundResult) throws GameDAOPersistenceException;

    /**
     * Deletes a round result from the database.
     * @param roundResult The round result to be deleted.
     */
    public void deleteRoundResult(RoundResult roundResult) throws GameDAOPersistenceException;

    /**
     * Returns a round result from the database.
     * @param roundResultId The id of the round result.
     * @return The round result.
     */
    public RoundResult getRoundResult(int roundResultId) throws RoundResultDoesNotExistException;

    /**
     * Returns all correct round results for a game from the database.
     * @param game The game.
     * @param user the user.
     * @return The round results.
     */
    public List<RoundResult> getCorrectRoundResults(Game game, User user) throws RoundResultDoesNotExistException;
}
