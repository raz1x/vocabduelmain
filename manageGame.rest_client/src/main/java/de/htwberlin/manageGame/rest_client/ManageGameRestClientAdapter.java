package de.htwberlin.manageGame.rest_client;

import de.htwberlin.manageGame.export.*;
import de.htwberlin.manageVocab.export.CategoryNotFoundException;
import de.htwberlin.manageVocab.export.TranslationNotFoundException;
import de.htwberlin.manageVocab.export.VocabListNotFoundException;
import de.htwberlin.manageVocab.export.VocabNotFoundException;
import de.htwberlin.userManager.export.UserNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
public class ManageGameRestClientAdapter implements ManageGameRest {

    final String URI = "http://localhost:8080/manageGame";

    @Autowired
    RestTemplate restTemplate;

    @Override
    public Game createGame(int user1Id, int user2Id) throws UserDoesNotExistException, UserNotFoundException {
        return restTemplate.getForObject(URI + "/createGame/" + user1Id + "/" + user2Id, Game.class);
    }

    @Override
    public Game getGame(int gameId) throws GameDoesNotExistException {
        return restTemplate.getForObject(URI + "/getGame/" + gameId, Game.class);
    }

    @Override
    public Game updateGame(Game game) throws GameDoesNotExistException {
        return null;
    }

    @Override
    public void endGame(int gameId) throws GameDoesNotExistException {

    }

    @Override
    public List<Game> getAllOngoingGamesForUser(int userId) throws GameDoesNotExistException, UserDoesNotExistException, UserNotFoundException {
        ResponseEntity<List<Game>> response = restTemplate.exchange(URI + "/getAllOngoingGamesForUser/" + userId,
                HttpMethod.GET, null, new ParameterizedTypeReference<List<Game>>() {
                });
        List<Game> games = response.getBody();
        for (Game game : games) {
            System.out.println(game.getGameId());
        }
        return games;
    }

    @Override
    public Round createRound(int gameId, int round, int categoryId) throws GameDoesNotExistException, CategoryNotFoundException, VocabNotFoundException, GameQuestionDoesNotExistException, VocabListNotFoundException, TranslationNotFoundException {
        return null;
    }

    @Override
    public Round updateRound(Round round) throws RoundDoesNotExistException {
        return null;
    }

    @Override
    public void endRound(int roundId) throws RoundDoesNotExistException {

    }

    @Override
    public List<GameQuestion> generateQuestions(int categoryId, int gameId, Round round) throws CategoryNotFoundException, VocabListNotFoundException, VocabNotFoundException, TranslationNotFoundException, GameDoesNotExistException {
        return null;
    }

    @Override
    public List<GameAnswer> generateAnswers(int gameQuestionId) throws GameQuestionDoesNotExistException, VocabNotFoundException, TranslationNotFoundException {
        return null;
    }

    @Override
    public List<GameQuestion> getGameQuestionsForRound(int gameId, int roundId) throws GameDoesNotExistException, RoundDoesNotExistException, GameQuestionDoesNotExistException {
        return null;
    }

    @Override
    public List<GameAnswer> getGameAnswersForGameQuestion(int gameQuestionId) throws GameAnswerDoesNotExistException {
        return null;
    }

    @Override
    public void lockInAnswer(int gameAnswerId, int userId, boolean isCorrect) throws GameAnswerDoesNotExistException, UserNotFoundException {

    }

    @Override
    public int getScoreForUser(int userId, int gameId) throws GameDoesNotExistException, UserDoesNotExistException, RoundResultDoesNotExistException, UserNotFoundException {
        return 0;
    }

    @Override
    public int getLatestRoundForGame(int gameId) throws GameDoesNotExistException {
        return 0;
    }

    @Override
    public Round getOngoingRoundForGame(int gameId) throws RoundDoesNotExistException {
        return null;
    }
}
