package de.htwberlin.game.impl;

import de.htwberlin.game.export.*;
import de.htwberlin.userManager.export.User;
import de.htwberlin.userManager.export.UserDAO;
import de.htwberlin.vocab.export.*;
import jakarta.persistence.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Component
@Transactional(value = "transactionManager", propagation = Propagation.REQUIRES_NEW, rollbackFor = Throwable.class)
public class ManageGameImpl implements ManageGame {

    @Autowired
    private GameDAO gameDAO;

    @Autowired
    private VocabDAO vocabDAO;

    @Autowired
    private UserDAO userDAO;

    @Override
    public Game createGame(int user1Id, int user2Id) throws UserDoesNotExistException {
        try {
            User user1 = userDAO.getUser(user1Id);
            User user2 = userDAO.getUser(user2Id);

            Game game = new Game(user1.getUserId(), user2.getUserId());
            gameDAO.saveGame(game);
            return game;
        } catch (Exception e) {
            throw new UserDoesNotExistException(e.getMessage());
        }
    }

    @Override
    public Game continueGame(int gameId) throws GameDoesNotExistException {
        try {
            return gameDAO.getGame(gameId);
        } catch (Exception e) {
            throw new GameDoesNotExistException("Game " + gameId + " does not exist.");
        }
    }

    @Override
    public void endGame(int gameId) throws GameDoesNotExistException{
        try {
            Game game = gameDAO.getGame(gameId);
            game.setIsOngoing(false);
            gameDAO.updateGame(game);
        } catch (Exception e) {
            throw new GameDoesNotExistException("Game " + gameId + "does not exist.");
        }
    }

    @Override
    public Round createRound(int gameId, int roundNumber, int categoryId) throws GameDoesNotExistException, CategoryNotFoundException {
        Game game;
        Category category;
        try {
            game = gameDAO.getGame(gameId);
        } catch (Exception e) {
            throw new GameDoesNotExistException("Game " + gameId + " does not exist.");
        }
        try {
            category = vocabDAO.getCategory(categoryId);
        } catch (Exception e) {
            throw new CategoryNotFoundException("Category " + categoryId + " does not exist.");
        }
        try {
            Round round = new Round(game, roundNumber, category);
            gameDAO.saveRound(round);
            return round;
        } catch (EntityExistsException e) {
            throw new EntityExistsException("Could not create round with following Ids: .");
        }
    }

    @Override
    public RoundResult createRoundResult(int chosenAnswerId, int userId) throws UserDoesNotExistException {
        try {
            GameAnswer gameAnswer = gameDAO.getGameAnswer(chosenAnswerId);
            User user = userDAO.getUser(userId);
            RoundResult roundResult = new RoundResult(gameAnswer, user);
            gameDAO.saveRoundResult(roundResult);
            return roundResult;
        } catch (Exception e) {
            throw new UserDoesNotExistException("User does not exist.");
        }
    }

    @Override
    public List<GameQuestion> generateQuestions(int categoryId, int gameId, int roundNumber) throws CategoryNotFoundException, VocabListNotFoundException, VocabNotFoundException, GameDoesNotExistException, RoundDoesNotExistException {
        List<GameQuestion> gameQuestions = new ArrayList<>();
        VocabList vocabList = vocabDAO.getRandomVocabListFromCategory(categoryId);
        for (int i = 0; i < 3; i++) {
            Vocab question = vocabDAO.getRandomVocabFromVocabList(vocabList.getVocabListId());
            Translation trueAnswer = vocabDAO.getTranslationFromVocabId(question.getVocabId());
            Game game = gameDAO.getGame(gameId);
            Category category = vocabDAO.getCategory(categoryId);
            Round round = gameDAO.getRound(gameId, roundNumber);
            GameQuestion gameQuestion = new GameQuestion(game, round, question, trueAnswer);
            gameDAO.saveGameQuestion(gameQuestion);
            gameQuestions.add(gameQuestion);
        }
        return gameQuestions;
    }

    @Override
    public List<GameAnswer> generateAnswers(int gameQuestionId) throws VocabNotFoundException, GameQuestionDoesNotExistException {
        List<GameAnswer> gameAnswers = new ArrayList<>();
        List<Translation> possibleTranslations = vocabDAO.getPossibleTranslationsFromVocabId(1, 3);
        GameQuestion gameQuestion = gameDAO.getGameQuestion(gameQuestionId);
        for (int i = 0; i < 3; i++) {
            gameAnswers.add(new GameAnswer(gameQuestion, possibleTranslations.get(i)));
        }
        return gameAnswers;
    }

    @Override
    public GameQuestion getNextQuestion(int gameId, int round, int previousGameQuestionId) throws GameDoesNotExistException, GameQuestionDoesNotExistException {
        return null;
    }

    @Override
    public void lockInAnswer(int gameId, int round, int gameQuestionId, int gameAnswerId, int userId) throws GameDoesNotExistException, GameQuestionDoesNotExistException, GameAnswerDoesNotExistException {

    }
}
