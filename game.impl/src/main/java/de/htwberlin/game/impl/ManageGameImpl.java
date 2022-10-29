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
            List <GameQuestion> gameQuestions = generateQuestions(categoryId, gameId, round);
            for (GameQuestion gameQuestion : gameQuestions) {
                List <GameAnswer> gameAnswers = generateAnswers(gameQuestion.getGameQuestionId());
                // save in DB
            }
            return new Round(gameId, round, categoryId);
        } catch (Exception e) {
            throw new GameDoesNotExistException("Game");
        }
    }

    @Override
    public RoundResult createRoundResult(int chosenAnswerId, int userId) throws UserDoesNotExistException {
        try {
            // get user data from DB
            // dummy implementation
            return new RoundResult(chosenAnswerId, userId);
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
            gameQuestions.add(new GameQuestion(gameId, round, 0, 0));
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
}
