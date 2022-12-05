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
    public Round createRound(int gameId, int roundNumber, int categoryId) throws Exception {
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
            List <GameQuestion> gameQuestions = generateQuestions(category.getCategoryId(), game.getGameId(), round);
            for (GameQuestion gameQuestion : gameQuestions) {
                generateAnswers(gameQuestion.getGameQuestionId());
            }
            return round;
        } catch (Exception e) {
            throw new Exception(e.getMessage());
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
    public List<GameQuestion> generateQuestions(int categoryId, int gameId, Round round) throws Exception {
        List<GameQuestion> gameQuestions = new ArrayList<>();
        VocabList vocabList = vocabDAO.getRandomVocabListFromCategory(categoryId);
        for (int i = 0; i < 3; i++) {
            try {
                Vocab question = vocabDAO.getRandomVocabFromVocabList(vocabList.getVocabListId());
                Translation trueAnswer = vocabDAO.getTranslationFromVocabId(question.getVocabId());
                Game game = gameDAO.getGame(gameId);
                Category category = vocabDAO.getCategory(categoryId);
                GameQuestion gameQuestion = new GameQuestion(game, round, question, trueAnswer, i);
                gameDAO.saveGameQuestion(gameQuestion);
                //save the true answer
                GameAnswer gameAnswer = new GameAnswer(gameQuestion, trueAnswer);
                gameDAO.saveGameAnswer(gameAnswer);
                gameQuestions.add(gameQuestion);
            } catch (Exception e) {
                throw new Exception(e.getMessage());
            }
        }
        return gameQuestions;
    }

    @Override
    public List<GameAnswer> generateAnswers(int gameQuestionId) throws VocabNotFoundException, GameQuestionDoesNotExistException {
        GameQuestion gameQuestion = gameDAO.getGameQuestion(gameQuestionId);
        List<GameAnswer> gameAnswers = new ArrayList<>();
        List<Translation> possibleTranslations = vocabDAO.getPossibleTranslationsFromVocabId(gameQuestion.getVocab().getVocabId(), 3);
        for (int i = 0; i < 3; i++) {
            GameAnswer gameAnswer = new GameAnswer(gameQuestion, possibleTranslations.get(i));
            gameDAO.saveGameAnswer(gameAnswer);
            gameAnswers.add(gameAnswer);
        }
        return gameAnswers;
    }

    @Override
    public GameQuestion getNextQuestion(int previousGameQuestionId) throws GameDoesNotExistException, GameQuestionDoesNotExistException, RoundDoesNotExistException {
        GameQuestion previousGameQuestion = gameDAO.getGameQuestion(previousGameQuestionId);
        Game game = gameDAO.getGame(previousGameQuestion.getGame().getGameId());
        Round round = gameDAO.getRound(game.getGameId(), previousGameQuestion.getRound().getRoundNumber());
        List<GameQuestion> gameQuestions = gameDAO.getGameQuestionsForRound(game.getGameId(), round.getRoundNumber());
        for (int i = 0; i < gameQuestions.size(); i++) {
            if (gameQuestions.get(i).getGameQuestionId() == previousGameQuestionId) {
                return gameQuestions.get(i + 1);
            }
        }
        return null;
    }

    @Override
    public void lockInAnswer(int gameAnswerId, int userId) throws GameAnswerDoesNotExistException, UserDoesNotExistException {
        try {
            GameAnswer gameAnswer = gameDAO.getGameAnswer(gameAnswerId);
            User user = userDAO.getUser(userId);
            RoundResult roundResult = new RoundResult(gameAnswer, user);
            gameDAO.saveRoundResult(roundResult);
        } catch (Exception e) {
            throw new GameAnswerDoesNotExistException("Game answer does not exist.");
        }
    }
}
