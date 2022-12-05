package de.htwberlin.game.export;

import java.util.List;

public interface GameDAO {
    /**
     * Saves a game in the database.
     * @param game The game to be saved.
     * @return The saved game.
     */
    public Game saveGame(Game game);

    /**
     * Updates a game from the database.
     * @param game The game to be updated.
     * @return The updated game.
     */
    public Game updateGame(Game game);

    /**
     * Deletes a game from the database.
     * @param game The game to be deleted.
     */
    public void deleteGame(Game game);

    /**
     * Returns a game from the database.
     * @param gameId The id of the game.
     * @return The game.
     */
    public Game getGame(int gameId) throws GameDoesNotExistException;

    /**
     * Saves a game answer in the database.
     * @param gameAnswer The game answer to be saved.
     * @return The saved game answer.
     */
    public GameAnswer saveGameAnswer(GameAnswer gameAnswer);

    /**
     * Updates a game answer from the database.
     * @param gameAnswer The game answer to be updated.
     * @return The updated game answer.
     */
    public GameAnswer updateGameAnswer(GameAnswer gameAnswer);

    /**
     * Deletes a game answer from the database.
     * @param gameAnswer The game answer to be deleted.
     */
    public void deleteGameAnswer(GameAnswer gameAnswer);

    /**
     * Returns a game answer from the database.
     * @param gameAnswerId The id of the game answer.
     * @return The game answer.
     */
    public GameAnswer getGameAnswer(int gameAnswerId) throws GameAnswerDoesNotExistException;

    /**
     * Saves a game question in the database.
     * @param gameQuestion The game question to be saved.
     * @return The saved game question.
     */
    public GameQuestion saveGameQuestion(GameQuestion gameQuestion);

    /**
     * Updates a game question from the database.
     * @param gameQuestion The game question to be updated.
     * @return The updated game question.
     */
    public GameQuestion updateGameQuestion(GameQuestion gameQuestion);

    /**
     * Deletes a game question from the database.
     * @param gameQuestion The game question to be deleted.
     */
    public void deleteGameQuestion(GameQuestion gameQuestion);

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
    public Round saveRound(Round round);

    /**
     * Updates a round from the database.
     * @param round The round to be updated.
     * @return The updated round.
     */
    public Round updateRound(Round round);

    /**
     * Deletes a round from the database.
     * @param round The round to be deleted.
     */
    public void deleteRound(Round round);

    /**
     * Returns a round from the database.
     * @param gameId The id of the game.
     * @param roundNumber The number of the round.
     * @return The round.
     */
    public Round getRound(int gameId, int roundNumber) throws RoundDoesNotExistException;

    /**
     * Saves a round result in the database.
     * @param roundResult The round result to be saved.
     * @return The saved round result.
     */
    public RoundResult saveRoundResult(RoundResult roundResult);

    /**
     * Updates a round result from the database.
     * @param roundResult The round result to be updated.
     * @return The updated round result.
     */
    public RoundResult updateRoundResult(RoundResult roundResult);

    /**
     * Deletes a round result from the database.
     * @param roundResult The round result to be deleted.
     */
    public void deleteRoundResult(RoundResult roundResult);

    /**
     * Returns a round result from the database.
     * @param roundResultId The id of the round result.
     * @return The round result.
     */
    public RoundResult getRoundResult(int roundResultId) throws RoundResultDoesNotExistException;
}
