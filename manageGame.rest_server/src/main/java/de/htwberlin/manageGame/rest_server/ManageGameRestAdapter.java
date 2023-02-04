package de.htwberlin.manageGame.rest_server;

import de.htwberlin.manageGame.export.*;
import de.htwberlin.manageVocab.export.CategoryNotFoundException;
import de.htwberlin.manageVocab.export.TranslationNotFoundException;
import de.htwberlin.manageVocab.export.VocabListNotFoundException;
import de.htwberlin.manageVocab.export.VocabNotFoundException;
import de.htwberlin.userManager.export.UserNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/manageGame")
public class ManageGameRestAdapter {

    @Autowired
    private ManageGame manageGame;

    @GetMapping("/createGame/{user1Id}/{user2Id}")
    public Game createGame(@PathVariable("user1Id") int user1Id, @PathVariable("user2Id") int user2Id) throws UserDoesNotExistException, UserNotFoundException {
        return manageGame.createGame(user1Id, user2Id);
    }

    @GetMapping("/getGame/{gameId}")
    public Game getGame(@PathVariable int gameId) throws GameDoesNotExistException {
        return manageGame.getGame(gameId);
    }

    @PutMapping(value = "/updateGame", consumes = "application/json", produces = "application/json")
    public Game updateGame(@RequestBody Game game) throws GameDoesNotExistException {
        return manageGame.updateGame(game);
    }

    @PostMapping("/endGame/{gameId}")
    public void endGame(@PathVariable int gameId) throws GameDoesNotExistException {
        manageGame.endGame(gameId);
    }

    @GetMapping("/getAllOngoingGamesForUser/{userId}")
    public List<Game> getAllOngoingGamesForUser(@PathVariable int userId) throws GameDoesNotExistException, UserDoesNotExistException, UserNotFoundException {
        return manageGame.getAllOngoingGamesForUser(userId);
    }

    @PostMapping("/createRound/{gameId}/{round}/{categoryId}")
    public Round createRound(@PathVariable("gameId") int gameId, @PathVariable("round") int round, @PathVariable("categoryId") int categoryId) throws GameDoesNotExistException, CategoryNotFoundException, VocabNotFoundException, GameQuestionDoesNotExistException, VocabListNotFoundException, TranslationNotFoundException {
        return manageGame.createRound(gameId, round, categoryId);
    }

    @PutMapping(value = "/updateRound", consumes = "application/json", produces = "application/json")
    public Round updateRound(@RequestBody Round round) throws RoundDoesNotExistException {
        return manageGame.updateRound(round);
    }

    @PostMapping("/endRound/{roundId}")
    public void endRound(@PathVariable int roundId) throws RoundDoesNotExistException {
        manageGame.endRound(roundId);
    }

    @PostMapping(value = "/generateQuestions/{categoryId}/{gameId}", consumes = "application/json", produces = "application/json")
    public List<GameQuestion> generateQuestions(@PathVariable("categoryId") int categoryId, @PathVariable("gameId") int gameId, @RequestBody Round round) throws CategoryNotFoundException, VocabListNotFoundException, VocabNotFoundException, TranslationNotFoundException, GameDoesNotExistException {
        return manageGame.generateQuestions(categoryId, gameId, round);
    }

    @PostMapping("/generateAnswers/{gameQuestionId}")
    public List<GameAnswer> generateAnswers(@PathVariable int gameQuestionId) throws GameQuestionDoesNotExistException, VocabNotFoundException, TranslationNotFoundException {
        return manageGame.generateAnswers(gameQuestionId);
    }

    @GetMapping("/getGameQuestionsForRound/{gameId}/{roundId}")
    public List<GameQuestion> getGameQuestionsForRound(@PathVariable("gameId") int gameId, @PathVariable("roundId") int roundId) throws GameDoesNotExistException, RoundDoesNotExistException, GameQuestionDoesNotExistException {
        return manageGame.getGameQuestionsForRound(gameId, roundId);
    }

    @GetMapping("/getGameAnswersForGameQuestion/{gameQuestionId}")
    public List<GameAnswer> getGameAnswersForGameQuestion(@PathVariable int gameQuestionId) throws GameAnswerDoesNotExistException {
        return manageGame.getGameAnswersForGameQuestion(gameQuestionId);
    }

    @PostMapping("/lockInAnswer/{gameAnswerId}/{userId}/{isCorrect}")
    public void lockInAnswer(@PathVariable("gameAnswerId") int gameAnswerId, @PathVariable("userId") int userId, @PathVariable("isCorrect") boolean isCorrect) throws GameAnswerDoesNotExistException, UserNotFoundException {
        manageGame.lockInAnswer(gameAnswerId, userId, isCorrect);
    }

    @GetMapping("/getScoreForUser/{userId}/{gameId}")
    public int getScoreForUser(@PathVariable("userId") int userId, @PathVariable("gameId") int gameId) throws GameDoesNotExistException, UserDoesNotExistException, RoundResultDoesNotExistException, UserNotFoundException {
        return manageGame.getScoreForUser(userId, gameId);
    }

    @GetMapping("/getLatestRoundForGame/{gameId}")
    public int getLatestRoundForGame(@PathVariable int gameId) throws GameDoesNotExistException {
        return manageGame.getLatestRoundForGame(gameId);
    }

    @GetMapping("/getOngoingRoundForGame/{gameId}")
    public Round getOngoingRoundForGame(@PathVariable int gameId) throws RoundDoesNotExistException {
        return manageGame.getOngoingRoundForGame(gameId);
    }
}
