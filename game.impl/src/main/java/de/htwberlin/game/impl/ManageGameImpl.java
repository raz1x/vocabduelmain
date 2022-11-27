package de.htwberlin.game.impl;

import de.htwberlin.game.export.*;
import de.htwberlin.userManager.export.User;
import de.htwberlin.vocab.export.*;
import jakarta.persistence.*;
import jakarta.transaction.Transactional;
import org.hibernate.HibernateError;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class ManageGameImpl implements ManageGame {

    @Autowired
    EntityManager entityManager;

    @Autowired
    public AccessVocab accessVocab;

    @Override
    public Game createGame(int user1Id, int user2Id) throws UserDoesNotExistException {
        try {
            User user1 = entityManager.find(User.class, user1Id);
            User user2 = entityManager.find(User.class, user2Id);

            Game game = new Game(user1.getUserId(), user2.getUserId());
            entityManager.persist(game);
            return game;
        } catch (Exception e) {
            throw new UserDoesNotExistException(e.getMessage());
        }
    }

    @Override
    public Game continueGame(int gameId) throws GameDoesNotExistException {
        try {
            return entityManager.find(Game.class, gameId);
        } catch (Exception e) {
            throw new GameDoesNotExistException("Game " + gameId + " does not exist.");
        }
    }

    @Override
    public void endGame(int gameId) throws GameDoesNotExistException{
        try {
            Game game = entityManager.find(Game.class, gameId);
            game.setIsOngoing(false);
            entityManager.persist(game);
        } catch (Exception e) {
            throw new GameDoesNotExistException("Game " + gameId + "does not exist.");
        }
    }

    @Override
    public Round createRound(int gameId, int roundNumber, int categoryId) throws GameDoesNotExistException, CategoryNotFoundException {
        Game game;
        Category category;
        try {
            game = entityManager.find(Game.class, gameId);
        } catch (Exception e) {
            throw new GameDoesNotExistException("Game " + gameId + " does not exist.");
        }
        try {
            category = entityManager.find(Category.class, categoryId);
        } catch (Exception e) {
            throw new CategoryNotFoundException("Category " + categoryId + " does not exist.");
        }
        try {
            Round round = new Round(game, roundNumber, category);
            entityManager.persist(round);
            return round;
        } catch (HibernateError e) {
            throw new HibernateError("Could not create round with following Ids: .");
        }
    }

    @Override
    public RoundResult createRoundResult(int chosenAnswerId, int userId) throws UserDoesNotExistException {
        try {
            GameAnswer gameAnswer = entityManager.find(GameAnswer.class, chosenAnswerId);
            User user = entityManager.find(User.class, userId);
            RoundResult roundResult = new RoundResult(gameAnswer, user);
            entityManager.persist(roundResult);
            return roundResult;
        } catch (Exception e) {
            throw new UserDoesNotExistException("User does not exist.");
        }
    }

    @Override
    public List<GameQuestion> generateQuestions(int categoryId, int gameId, int roundNumber) throws CategoryNotFoundException, VocabListNotFoundException, VocabNotFoundException {
        List<GameQuestion> gameQuestions = new ArrayList<>();
        VocabList vocabList = accessVocab.getRandomVocabListFromCategory(categoryId);
        for (int i = 0; i < 3; i++) {
            Vocab question = accessVocab.getRandomVocabFromVocabList(vocabList.getVocabListId());
            Translation trueAnswer = accessVocab.getTranslationFromVocabId(question.getVocabId());
            Game game = entityManager.find(Game.class, gameId);
            Category category = entityManager.find(Category.class, categoryId);
            Round round = entityManager.createQuery("SELECT r FROM Round r WHERE r.gameId = :game AND r.roundNumber = :roundNumber", Round.class)
                    .setParameter("gameId", game.getGameId())
                    .setParameter("roundNumber", roundNumber)
                    .getSingleResult();
            gameQuestions.add(new GameQuestion(game, round, question, trueAnswer));
        }
        return gameQuestions;
    }

    @Override
    public List<GameAnswer> generateAnswers(int questionId) throws VocabNotFoundException {
        List<GameAnswer> gameAnswers = new ArrayList<>();
        List<Translation> possibleTranslations = accessVocab.getPossibleTranslationsFromVocabId(1, 3);
        GameQuestion gameQuestion = entityManager.find(GameQuestion.class, questionId);
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
