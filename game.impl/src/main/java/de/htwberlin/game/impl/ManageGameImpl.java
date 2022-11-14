package de.htwberlin.game.impl;

import de.htwberlin.game.export.*;
import de.htwberlin.vocab.export.*;

import java.util.ArrayList;
import java.util.List;

public class ManageGameImpl implements ManageGame {
    public AccessVocab accessVocabImpl;
    @Override
    public Game createGame(int user1Id, int user2Id) throws UserDoesNotExistException {
        try {
            // get user data from DB
            // dummy implementation
            // create round and questions for the game.
            return new Game(user1Id, user2Id);
        } catch (Exception e) {
            throw new UserDoesNotExistException("User does not exist.");
        }
    }

    @Override
    public Game continueGame(int gameId) throws GameDoesNotExistException {
        try {
            // get game data from DB
            // dummy implementation
            return new Game(0, 0);
        } catch (Exception e) {
            throw new GameDoesNotExistException("Game does not exist.");
        }
    }

    @Override
    public void endGame(int gameId) {
        // end game in DB
        // dummy implementation
    }

    @Override
    public Round createRound(int gameId, int round, int categoryId) throws GameDoesNotExistException {
        try {
            // get game data from DB
            // dummy implementation
            List <GameQuestion> gameQuestions = generateQuestions(categoryId, gameId);
            for (GameQuestion gameQuestion : gameQuestions) {
                List <GameAnswer> gameAnswers = generateAnswers(gameQuestion.getGameQuestionId());
                // save in DB
            }
            //get Game from gameId
            Game game = new Game(0, 0);
            // get Category from categoryId
            Category category = new Category("dummy");
            return new Round(game, round, category);
        } catch (Exception e) {
            throw new GameDoesNotExistException("Game");
        }
    }

    @Override
    public RoundResult createRoundResult(int chosenAnswerId, int userId) throws UserDoesNotExistException {
        try {
            // get user data from DB
            // dummy implementation
            // data from DB
            Game game = new Game(0,0);
            Category category = new Category("dummy");
            Round round = new Round(game, 0, category);
            VocabList vocabList = new VocabList(1, category, "dummy", "dummy", "dummy");
            Vocab vocab = new Vocab(vocabList, "dummy");
            Translation translation = new Translation(vocab, "dummy");
            GameQuestion gameQuestion = new GameQuestion(game, round, vocab, 1);
            GameAnswer chosenAnswer = new GameAnswer(gameQuestion, translation);
            return new RoundResult(chosenAnswer, user);
        } catch (Exception e) {
            throw new UserDoesNotExistException("User does not exist.");
        }
    }

    @Override
    public List<GameQuestion> generateQuestions(int categoryId, int gameId, int round) throws CategoryNotFoundException, VocabListNotFoundException, VocabNotFoundException {
        List<GameQuestion> gameQuestions = new ArrayList<>();
        // VocabList vocabList = accessVocabImpl.getRandomVocabListFromCategory(categoryId);
        for (int i = 0; i < 3; i++) {
            // Vocab question = accessVocabImpl.getRandomVocabFromVocabList(vocabList.getVocabListId());
            // Translation trueAnswer = accessVocabImpl.getTranslationFromVocabId(question.getVocabId());
            Game game = new Game(0, 0);
            Category category = new Category("dummy");
            Round round = new Round(game, 0, category);

            gameQuestions.add(new GameQuestion(game, round, 0, 0));
        }
        return gameQuestions;
    }

    @Override
    public List<GameAnswer> generateAnswers(int questionId) throws VocabNotFoundException {
        List<GameAnswer> gameAnswers = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            // implement: get question object from DB
            //List<Translation> possibleTranslations = accessVocabImpl.getPossibleTranslationsFromVocabId(1, 3);
            gameAnswers.add(new GameAnswer(questionId, 0));
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
