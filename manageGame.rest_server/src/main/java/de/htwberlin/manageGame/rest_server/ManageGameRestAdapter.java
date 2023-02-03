package de.htwberlin.manageGame.rest_server;

import de.htwberlin.manageGame.export.*;
import de.htwberlin.manageVocab.export.CategoryNotFoundException;
import de.htwberlin.manageVocab.export.TranslationNotFoundException;
import de.htwberlin.manageVocab.export.VocabListNotFoundException;
import de.htwberlin.manageVocab.export.VocabNotFoundException;
import de.htwberlin.userManager.export.UserNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/manageGame")
public class ManageGameRestAdapter implements ManageGameRest {

    @Autowired
    private ManageGame manageGame;

    @Override
    public Game createGame(int user1Id, int user2Id) throws UserDoesNotExistException, UserNotFoundException {
        return manageGame.createGame(user1Id, user2Id);
    }

    @Override
    public Game getGame(int gameId) throws GameDoesNotExistException {
        return manageGame.getGame(gameId);
    }

    @Override
    public Game updateGame(Game game) throws GameDoesNotExistException {
        return manageGame.updateGame(game);
    }

    @Override
    public void endGame(int gameId) throws GameDoesNotExistException {
        manageGame.endGame(gameId);
    }

    @Override
    public List<Game> getAllOngoingGamesForUser(int userId) throws GameDoesNotExistException, UserDoesNotExistException, UserNotFoundException {
        return manageGame.getAllOngoingGamesForUser(userId);
    }

    @Override
    public Round createRound(int gameId, int round, int categoryId) throws GameDoesNotExistException, CategoryNotFoundException, VocabNotFoundException, GameQuestionDoesNotExistException, VocabListNotFoundException, TranslationNotFoundException {
        return manageGame.createRound(gameId, round, categoryId);
    }

    @Override
    public Round updateRound(Round round) throws RoundDoesNotExistException {
        return manageGame.updateRound(round);
    }

    @Override
    public void endRound(int roundId) throws RoundDoesNotExistException {
        manageGame.endRound(roundId);
    }

    @Override
    public List<GameQuestion> generateQuestions(int categoryId, int gameId, Round round) throws CategoryNotFoundException, VocabListNotFoundException, VocabNotFoundException, TranslationNotFoundException, GameDoesNotExistException {
        return manageGame.generateQuestions(categoryId, gameId, round);
    }

    @Override
    public List<GameAnswer> generateAnswers(int gameQuestionId) throws GameQuestionDoesNotExistException, VocabNotFoundException, TranslationNotFoundException {
        return manageGame.generateAnswers(gameQuestionId);
    }

    @Override
    public List<GameQuestion> getGameQuestionsForRound(int gameId, int roundId) throws GameDoesNotExistException, RoundDoesNotExistException, GameQuestionDoesNotExistException {
        return manageGame.getGameQuestionsForRound(gameId, roundId);
    }

    @Override
    public List<GameAnswer> getGameAnswersForGameQuestion(int gameQuestionId) throws GameAnswerDoesNotExistException {
        return manageGame.getGameAnswersForGameQuestion(gameQuestionId);
    }

    @Override
    public void lockInAnswer(int gameAnswerId, int userId, boolean isCorrect) throws GameAnswerDoesNotExistException, UserNotFoundException {
        manageGame.lockInAnswer(gameAnswerId, userId, isCorrect);
    }

    @Override
    public int getScoreForUser(int userId, int gameId) throws GameDoesNotExistException, UserDoesNotExistException, RoundResultDoesNotExistException, UserNotFoundException {
        return manageGame.getScoreForUser(userId, gameId);
    }

    @Override
    public int getLatestRoundForGame(int gameId) throws GameDoesNotExistException {
        return manageGame.getLatestRoundForGame(gameId);
    }

    @Override
    public Round getOngoingRoundForGame(int gameId) throws RoundDoesNotExistException {
        return manageGame.getOngoingRoundForGame(gameId);
    }
}
