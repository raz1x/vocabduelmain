package de.htwberlin.manageGame.export;

import de.htwberlin.manageVocab.export.*;
import de.htwberlin.userManager.export.UserDAOPersistenceException;
import de.htwberlin.userManager.export.UserNotFoundException;

import java.util.List;

public interface ManageGame {
    /**
     * Creates a new game.
     * @param user1Id The ID of the first user.
     * @param user2Id The ID of the second user.
     * @throws UserNotFoundException If the user is not found.
     * @return The Game object.
     */
    Game createGame(int user1Id, int user2Id) throws UserNotFoundException;

    /**
     * Gets an existing game from the database.
     * @param gameId The ID of the game.
     * @throws GameDoesNotExistException If the game does not exist.
     * @return The Game object.
     */
    Game getGame(int gameId) throws GameDoesNotExistException;

    /**
     * Updates a game.
     * @param game The Game object.
     * @throws GameDoesNotExistException If the game does not exist.
     * @return The updated Game object.
     */
    Game updateGame(Game game) throws GameDoesNotExistException;

    /**
     * Finishes a game.
     * @param gameId The ID of the game.
     * @throws GameDoesNotExistException If the game does not exist.
     */
    void endGame(int gameId) throws GameDoesNotExistException;

    /**
     * Gets all ongoing games for a user.
     * @param userId The ID of the user.
     * @throws GameDoesNotExistException If the game does not exist.
     * @throws UserNotFoundException If the user is not found.
     */
    List<Game> getAllOngoingGamesForUser(int userId) throws GameDoesNotExistException, UserNotFoundException;

    /**
     * Creates a new round.
     * @param gameId Foreign key of the game.
     * @param round The round number of the game.
     * @param categoryId Foreign Key of the used category.
     * @throws GameDoesNotExistException If the game does not exist.
     * @throws CategoryNotFoundException If the category is not found.
     * @throws VocabNotFoundException If the vocab is not found.
     * @throws GameQuestionDoesNotExistException If the game question does not exist.
     * @throws VocabListNotFoundException If the vocab list is not found.
     * @throws TranslationNotFoundException If the translation is not found.
     * @return The Round object.
     */
    Round createRound(int gameId, int round, int categoryId) throws GameDoesNotExistException, CategoryNotFoundException, VocabNotFoundException, GameQuestionDoesNotExistException, VocabListNotFoundException, TranslationNotFoundException;

    /**
     * Updates a round.
     * @param round The round object.
     * @throws RoundDoesNotExistException If the round does not exist.
     * @return The updated round object.
     */
    Round updateRound(Round round) throws RoundDoesNotExistException;

    /**
     * Ends a round.
     * @param roundId Id of the round.
     * @throws RoundDoesNotExistException If the round does not exist.
     */
    void endRound(int roundId) throws RoundDoesNotExistException;

    /**
     * Returns a list of generated questions.
     * @param categoryId Foreign key of the category.
     * @param gameId Foreign key of the game.
     * @param round The round of the game.
     * @throws CategoryNotFoundException If the category is not found.
     * @throws VocabListNotFoundException If the vocab list is not found.
     * @throws VocabNotFoundException If the vocab is not found.
     * @throws TranslationNotFoundException If the translation is not found.
     * @throws GameDoesNotExistException If the game does not exist.
     * @return A list of GameQuestion objects.
     */
    List<GameQuestion> generateQuestions(int categoryId, int gameId, Round round) throws CategoryNotFoundException, VocabListNotFoundException, VocabNotFoundException, TranslationNotFoundException, GameDoesNotExistException;

    /**
     * Returns a list of generated answers.
     * @param gameQuestionId Foreign key of the GameQuestion.
     * @throws GameQuestionDoesNotExistException If the game question does not exist.
     * @throws VocabNotFoundException If the vocab is not found.
     * @throws TranslationNotFoundException If the translation is not found.
     * @return A list of GameAnswer objects.
     */
    List<GameAnswer> generateAnswers(int gameQuestionId) throws GameQuestionDoesNotExistException, VocabNotFoundException, TranslationNotFoundException;

    /**
     * Returns a list of all game questions for a round of a game.
     * @param gameId Foreign key of the game.
     * @param roundNumber The round number of the game.
     * @throws RoundDoesNotExistException If the round does not exist.
     * @throws GameDoesNotExistException If the game does not exist.
     * @throws GameQuestionDoesNotExistException If the game question does not exist.
     * @return A list of GameQuestion objects.
     */
    List<GameQuestion> getGameQuestionsForRound(int gameId, int roundNumber) throws RoundDoesNotExistException, GameDoesNotExistException, GameQuestionDoesNotExistException;

    /**
     * Returns a list of all game answers for a game question.
     * @param gameQuestionId Foreign key of the game question.
     * @throws GameAnswerDoesNotExistException If the game answer does not exist.
     * @return A list of GameAnswer objects.
     */
    List<GameAnswer> getGameAnswersForGameQuestion(int gameQuestionId) throws GameAnswerDoesNotExistException;


    /**
     * Locks in an answer.
     * @param gameAnswerId Foreign key of the GameAnswer.
     * @param userId Foreign key of the user.
     * @param isCorrect If the answer is correct.
     * @throws GameAnswerDoesNotExistException If the game answer does not exist.
     * @throws UserNotFoundException If the user does not exist.
     */
    void lockInAnswer(int gameAnswerId, int userId, boolean isCorrect) throws GameAnswerDoesNotExistException, UserNotFoundException;

    /**
     * Returns the score of a user.
     * @param userId Foreign key of the user.
     * @param gameId Foreign key of the game.
     * @throws GameDoesNotExistException If the game does not exist.
     * @throws RoundResultDoesNotExistException If the round result does not exist.
     * @throws UserNotFoundException If the user is not found.
     * @return The score of the user.
     */
    int getScoreForUser(int userId, int gameId) throws GameDoesNotExistException, RoundResultDoesNotExistException, UserNotFoundException;

    /**
     * Returns the latest round number of the game.
     * @param gameId Foreign key of the game.
     * @throws GameDoesNotExistException If the game does not exist.
     * @return The round number.
     */
    int getLatestRoundForGame(int gameId) throws GameDoesNotExistException;

    /**
     * Returns the latest round of the game.
     * @param gameId Foreign key of the game.
     * @throws RoundDoesNotExistException If no round exists.
     * @return The round object.
     */
    Round getOngoingRoundForGame(int gameId) throws RoundDoesNotExistException;
}
