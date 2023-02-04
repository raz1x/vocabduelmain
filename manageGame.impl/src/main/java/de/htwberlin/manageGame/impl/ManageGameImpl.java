package de.htwberlin.manageGame.impl;

import de.htwberlin.manageGame.export.*;
import de.htwberlin.manageVocab.export.*;
import de.htwberlin.userManager.export.ManageUser;
import de.htwberlin.userManager.export.User;

import de.htwberlin.userManager.export.UserDAOPersistenceException;
import de.htwberlin.userManager.export.UserNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service("manageGameImpl")
@Transactional(value = "transactionManager", propagation = Propagation.REQUIRES_NEW, rollbackFor = Throwable.class)
public class ManageGameImpl implements ManageGame {

    @Autowired
    private GameDAO gameDAO;

    @Autowired
    private ManageVocab manageVocab;

    @Autowired
    private ManageUser manageUser;

    @Override
    public Game createGame(int user1Id, int user2Id) throws UserNotFoundException {
        try {
            User user1 = manageUser.getById(user1Id);
            User user2 = manageUser.getById(user2Id);

            Game game = new Game(user1.getUserId(), user2.getUserId());
            gameDAO.saveGame(game);
            return game;
        } catch (GameDAOPersistenceException  e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    @Override
    public Game getGame(int gameId) throws GameDoesNotExistException {
        Game game = gameDAO.getGame(gameId);
        if (game == null) {
            throw new GameDoesNotExistException("Game with id " + gameId + " does not exist");
        }
        return game;
    }

    @Override
    public Game updateGame(Game game) throws GameDoesNotExistException {
        Game updatedGame = null;
        try {
            updatedGame = gameDAO.updateGame(game);
        } catch (GameDAOPersistenceException e) {
            throw new RuntimeException(e.getMessage());
        }
        if (updatedGame == null) {
            throw new GameDoesNotExistException("Game does not exist");
        }
        return updatedGame;
    }

    @Override
    public void endGame(int gameId) throws GameDoesNotExistException{
        try {
            Game game = gameDAO.getGame(gameId);
            game.setIsOngoing(false);
            gameDAO.updateGame(game);
        } catch (GameDAOPersistenceException e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    @Override
    public List<Game> getAllOngoingGamesForUser(int userId) throws GameDoesNotExistException, UserNotFoundException {
        User user = manageUser.getById(userId);
        List<Game> games = gameDAO.getOngoingGamesForUser(user);
        if (games.isEmpty()) {
            throw new GameDoesNotExistException("No ongoing games for user " + userId + " found.");
        }
        return games;
     }

    @Override
    public Round createRound(int gameId, int roundNumber, int categoryId) throws GameDoesNotExistException, CategoryNotFoundException, VocabNotFoundException, GameQuestionDoesNotExistException, VocabListNotFoundException, TranslationNotFoundException {
        Game game;
        Category category;
        game = gameDAO.getGame(gameId);
        category = manageVocab.getCategory(categoryId);
        try {
            Round round = gameDAO.saveRound(new Round(game, roundNumber, category));
            List<GameQuestion> gameQuestions = generateQuestions(category.getCategoryId(), game.getGameId(), round);
            for (GameQuestion gameQuestion : gameQuestions) {
                generateAnswers(gameQuestion.getGameQuestionId());
            }
            return round;
        } catch (GameDAOPersistenceException e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    @Override
    public Round updateRound(Round round) {
        try {
            gameDAO.updateRound(round);
            return round;
        } catch (GameDAOPersistenceException e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    @Override
    public void endRound(int roundId) throws RoundDoesNotExistException {
        try {
            Round round = gameDAO.getRoundById(roundId);
            round.setOnGoing(false);
            gameDAO.updateRound(round);
        } catch (GameDAOPersistenceException e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    @Override
    public List<GameQuestion> generateQuestions(int categoryId, int gameId, Round round) throws CategoryNotFoundException, VocabListNotFoundException, VocabNotFoundException, TranslationNotFoundException, GameDoesNotExistException {
        List<GameQuestion> gameQuestions = new ArrayList<>();
        VocabList vocabList;
        vocabList = manageVocab.getRandomVocabListFromCategory(categoryId);
        for (int i = 0; i < 3; i++) {
            Vocab question = manageVocab.getRandomVocabFromVocabList(vocabList.getVocabListId());
            Translation trueAnswer = manageVocab.getTranslationFromVocabId(question.getVocabId());
            Game game = gameDAO.getGame(gameId);
            Category category = manageVocab.getCategory(categoryId);
            GameQuestion gameQuestion = new GameQuestion(round, question, trueAnswer, i);
            try {
                gameDAO.saveGameQuestion(gameQuestion);
            } catch (GameDAOPersistenceException e) {
                throw new RuntimeException(e.getMessage());
            }
            //save the true answer
            GameAnswer gameAnswer = new GameAnswer(gameQuestion, trueAnswer);
            try {
                gameDAO.saveGameAnswer(gameAnswer);
            } catch (GameDAOPersistenceException e) {
                throw new RuntimeException(e.getMessage());
            }
            gameQuestions.add(gameQuestion);
        }
        return gameQuestions;
    }

    @Override
    public List<GameAnswer> generateAnswers(int gameQuestionId) throws VocabNotFoundException, GameQuestionDoesNotExistException, TranslationNotFoundException {
        GameQuestion gameQuestion = gameDAO.getGameQuestion(gameQuestionId);
        List<GameAnswer> gameAnswers = new ArrayList<>();
        List<Translation> possibleTranslations = null;
        possibleTranslations = manageVocab.getPossibleTranslationsFromVocabId(gameQuestion.getVocab().getVocabId(), 3);
        for (int i = 0; i < 3; i++) {
            GameAnswer gameAnswer = new GameAnswer(gameQuestion, possibleTranslations.get(i));
            try {
                gameDAO.saveGameAnswer(gameAnswer);
            } catch (GameDAOPersistenceException e) {
                throw new RuntimeException(e.getMessage());
            }
            gameAnswers.add(gameAnswer);
        }
        return gameAnswers;
    }

    @Override
    public List<GameQuestion> getGameQuestionsForRound(int gameId, int roundNumber) throws RoundDoesNotExistException, GameDoesNotExistException, GameQuestionDoesNotExistException {
        return gameDAO.getGameQuestionsForRound(gameId, roundNumber);
    }

    @Override
    public List<GameAnswer> getGameAnswersForGameQuestion(int gameQuestionId) throws GameAnswerDoesNotExistException {
        List <GameAnswer> gameAnswers = gameDAO.getGameAnswersForGameQuestion(gameQuestionId);
        Collections.shuffle(gameAnswers);
        return gameAnswers;
    }

    @Override
    public void lockInAnswer(int gameAnswerId, int userId, boolean isCorrect) throws GameAnswerDoesNotExistException, UserNotFoundException {
        try {
            GameAnswer gameAnswer = gameDAO.getGameAnswer(gameAnswerId);
            User user = manageUser.getById(userId);
            RoundResult roundResult = new RoundResult(gameAnswer, user, isCorrect);
            gameDAO.saveRoundResult(roundResult);
        } catch (GameDAOPersistenceException e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    @Override
    public int getScoreForUser(int userId, int gameId) throws GameDoesNotExistException, RoundResultDoesNotExistException, UserNotFoundException {
        Game game = gameDAO.getGame(gameId);
        User user = manageUser.getById(userId);
        List<RoundResult> correctRoundResults = gameDAO.getCorrectRoundResults(game, user);
        return correctRoundResults.size();
    }

    @Override
    public int getLatestRoundForGame(int gameId) throws GameDoesNotExistException {
        Game game = gameDAO.getGame(gameId);
        return gameDAO.getNumberOfRounds(game.getGameId());
    }

    @Override
    public Round getOngoingRoundForGame(int gameId) throws RoundDoesNotExistException{
        return gameDAO.getOngoingRound(gameId);
    }
}
