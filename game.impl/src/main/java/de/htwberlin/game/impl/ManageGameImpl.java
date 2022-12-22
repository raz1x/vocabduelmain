package de.htwberlin.game.impl;

import de.htwberlin.game.export.*;
import de.htwberlin.userManager.export.User;
import de.htwberlin.userManager.export.UserDAO;
import de.htwberlin.vocab.export.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collections;
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
            throw new UserDoesNotExistException("User does not exist");
        }
    }

    @Override
    public Game getGame(int gameId) throws GameDoesNotExistException {
        try {
            Game game = gameDAO.getGame(gameId);
            if (game == null) {
                throw new GameDoesNotExistException("Game does not exist");
            }
            return gameDAO.getGame(gameId);
        } catch (Exception e) {
            throw new GameDoesNotExistException("Game " + gameId + " does not exist.");
        }
    }

    @Override
    public Game updateGame(Game game) throws GameDoesNotExistException {
        try {
            Game updatedGame = gameDAO.updateGame(game);
            if (updatedGame == null) {
                throw new GameDoesNotExistException("Game does not exist");
            }
            return updatedGame;
        } catch (Exception e) {
            throw new GameDoesNotExistException("Game " + game.getGameId() + " does not exist.");
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
    public List<Game> getAllOngoingGamesForUser(int userId) throws GameDoesNotExistException, UserDoesNotExistException {
        User user;
        try {
            user = userDAO.getUser(userId);
        } catch (Exception e) {
            throw new UserDoesNotExistException("User " + userId + " does not exist.");
        }
        List<Game> games = gameDAO.getOngoingGamesForUser(user);
        if (games.isEmpty()) {
            throw new GameDoesNotExistException("No ongoing games for user " + userId + " found.");
        }
        return games;
     }

    @Override
    public Round createRound(int gameId, int roundNumber, int categoryId) throws GameDoesNotExistException, CategoryNotFoundException, GameDAOPersistenceException {
        Game game;
        Category category;
        try {
            game = gameDAO.getGame(gameId);
        } catch (GameDoesNotExistException e) {
            throw new GameDoesNotExistException("Game " + gameId + " does not exist.");
        }
        try {
            category = vocabDAO.getCategory(categoryId);
        } catch (CategoryNotFoundException e) {
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
        } catch (GameDAOPersistenceException | VocabNotFoundException | GameQuestionDoesNotExistException e) {
            throw new GameDAOPersistenceException("Could not create round while generating questions and answers.");
        }
    }

    @Override
    public Round getRound(int roundId) throws RoundDoesNotExistException {
        try {
            return gameDAO.getRoundById(roundId);
        } catch (Exception e) {
            throw new RoundDoesNotExistException("Round " + roundId + " does not exist.");
        }
    }

    @Override
    public Round updateRound(Round round) throws RoundDoesNotExistException {
        try {
            gameDAO.updateRound(round);
            return round;
        } catch (Exception e) {
            throw new RoundDoesNotExistException("Round " + round.getRoundId() + " does not exist.");
        }
    }

    @Override
    public void endRound(int roundId) throws RoundDoesNotExistException {
        try {
            Round round = gameDAO.getRoundById(roundId);
            round.setOnGoing(false);
            gameDAO.updateRound(round);
        } catch (Exception e) {
            throw new RoundDoesNotExistException("Round " + roundId + " does not exist.");
        }
    }

    @Override
    public List<GameQuestion> generateQuestions(int categoryId, int gameId, Round round) throws GameDAOPersistenceException, CategoryNotFoundException {
        List<GameQuestion> gameQuestions = new ArrayList<>();
        VocabList vocabList;
        try {
             vocabList = vocabDAO.getRandomVocabListFromCategory(categoryId);
        } catch (CategoryNotFoundException e) {
            throw new CategoryNotFoundException("Category " + categoryId + " does not exist.");
        }
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
            } catch (VocabListNotFoundException | VocabNotFoundException | TranslationNotFoundException | GameDoesNotExistException e) {
                throw new GameDAOPersistenceException(e.getMessage());
            }
        }
        return gameQuestions;
    }

    @Override
    public List<GameAnswer> generateAnswers(int gameQuestionId) throws VocabNotFoundException, GameQuestionDoesNotExistException, GameDAOPersistenceException {
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
    public List<GameQuestion> getGameQuestionsForRound(int gameId, int roundNumber) throws RoundDoesNotExistException {
        try {
            return gameDAO.getGameQuestionsForRound(gameId, roundNumber);
        } catch (Exception e) {
            throw new RoundDoesNotExistException("Round " + roundNumber + " does not exist.");
        }
    }

    @Override
    public List<GameAnswer> getGameAnswersForGameQuestion(int gameQuestionId) throws GameAnswerDoesNotExistException {
        try {
            List <GameAnswer> gameAnswers = gameDAO.getGameAnswersForGameQuestion(gameQuestionId);
            Collections.shuffle(gameAnswers);
            return gameAnswers;
        } catch (Exception e) {
            throw new GameAnswerDoesNotExistException("Game answers for game question " + gameQuestionId + " do not exist.");
        }
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
    public void lockInAnswer(int gameAnswerId, int userId, boolean isCorrect) throws GameAnswerDoesNotExistException, UserDoesNotExistException {
        try {
            GameAnswer gameAnswer = gameDAO.getGameAnswer(gameAnswerId);
            User user = userDAO.getUser(userId);
            RoundResult roundResult = new RoundResult(gameAnswer, user, isCorrect);
            gameDAO.saveRoundResult(roundResult);
        } catch (Exception e) {
            throw new GameAnswerDoesNotExistException("Game answer does not exist.");
        }
    }

    @Override
    public int getScoreForUser(int userId, int gameId) throws GameDoesNotExistException, UserDoesNotExistException {
        try {
            Game game = gameDAO.getGame(gameId);
            User user = userDAO.getUser(userId);
            List<RoundResult> correctRoundResults = gameDAO.getCorrectRoundResults(game, user);
            return correctRoundResults.size();
        } catch (Exception e) {
            System.out.println(e.getMessage());
            throw new GameDoesNotExistException("Game does not exist.");
        }
    }

    @Override
    public int getLatestRoundForGame(int gameId) throws GameDoesNotExistException {
        try {
            Game game = gameDAO.getGame(gameId);
            return gameDAO.getNumberOfRounds(gameId);
        } catch (Exception e) {
            throw new GameDoesNotExistException("Game does not exist.");
        }
    }

    @Override
    public Round getOngoingRoundForGame(int gameId) throws RoundDoesNotExistException{
        try {
            return gameDAO.getOngoingRound(gameId);
        } catch (Exception e) {
            throw new RoundDoesNotExistException("Game does not exist.");
        }
    }
}
