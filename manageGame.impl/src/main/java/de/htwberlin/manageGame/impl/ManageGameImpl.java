package de.htwberlin.manageGame.impl;

import de.htwberlin.manageGame.export.*;
import de.htwberlin.manageVocab.export.*;
import de.htwberlin.userManager.export.ManageUser;
import de.htwberlin.userManager.export.User;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
@Transactional(value = "transactionManager", propagation = Propagation.REQUIRES_NEW, rollbackFor = Throwable.class)
public class ManageGameImpl implements ManageGame {

    @Autowired
    private GameDAO gameDAO;

    @Autowired
    private ManageVocab manageVocab;

    @Autowired
    private ManageUser manageUser;

    @Override
    public Game createGame(int user1Id, int user2Id) throws UserDoesNotExistException {
        try {
            User user1 = manageUser.getById(user1Id);
            User user2 = manageUser.getById(user2Id);

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
            return game;
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
            user = manageUser.getById(userId);
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
    public Round createRound(int gameId, int roundNumber, int categoryId) throws GameDoesNotExistException, CategoryNotFoundException, GameDAOPersistenceException, VocabNotFoundException, GameQuestionDoesNotExistException, VocabListNotFoundException {
        Game game;
        Category category;
        try {
            game = gameDAO.getGame(gameId);
            category = manageVocab.getCategory(categoryId);
        } catch (GameDoesNotExistException e) {
            throw new GameDoesNotExistException("Game " + gameId + " does not exist.");
        } catch (CategoryNotFoundException e) {
            throw new CategoryNotFoundException("Category " + categoryId + " does not exist.");
        }
        try {
            Round round = gameDAO.saveRound(new Round(game, roundNumber, category));
            List<GameQuestion> gameQuestions = generateQuestions(category.getCategoryId(), game.getGameId(), round);
            for (GameQuestion gameQuestion : gameQuestions) {
                generateAnswers(gameQuestion.getGameQuestionId());
            }
            return round;
        } catch (GameDAOPersistenceException e) {
            throw new RuntimeException("Error while persisting round.");
        } catch (VocabNotFoundException e) {
            throw new VocabNotFoundException("Could not find vocab for category " + categoryId);
        } catch (GameQuestionDoesNotExistException e) {
            throw new GameQuestionDoesNotExistException("Could not find game question for round " + roundNumber + " in game " + gameId);
        } catch (VocabListNotFoundException e) {
            throw new VocabListNotFoundException("Could not find vocab list for category " + categoryId);
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
    public List<GameQuestion> generateQuestions(int categoryId, int gameId, Round round) throws GameDAOPersistenceException, CategoryNotFoundException, VocabListNotFoundException {
        List<GameQuestion> gameQuestions = new ArrayList<>();
        VocabList vocabList;
        try {
             vocabList = manageVocab.getRandomVocabListFromCategory(categoryId);
        } catch (CategoryNotFoundException e) {
            throw new CategoryNotFoundException("Category " + categoryId + " does not exist.");
        } catch (VocabListNotFoundException e) {
            throw new VocabListNotFoundException("Vocab list for category " + categoryId + " does not exist.");
        }
        for (int i = 0; i < 3; i++) {
            try {
                Vocab question = manageVocab.getRandomVocabFromVocabList(vocabList.getVocabListId());
                Translation trueAnswer = manageVocab.getTranslationFromVocabId(question.getVocabId());
                Game game = gameDAO.getGame(gameId);
                Category category = manageVocab.getCategory(categoryId);
                GameQuestion gameQuestion = new GameQuestion(round, question, trueAnswer, i);
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
        List<Translation> possibleTranslations = manageVocab.getPossibleTranslationsFromVocabId(gameQuestion.getVocab().getVocabId(), 3);
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
    public void lockInAnswer(int gameAnswerId, int userId, boolean isCorrect) throws GameAnswerDoesNotExistException, UserDoesNotExistException {
        try {
            GameAnswer gameAnswer = gameDAO.getGameAnswer(gameAnswerId);
            User user = manageUser.getById(userId);
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
            User user = manageUser.getById(userId);
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
