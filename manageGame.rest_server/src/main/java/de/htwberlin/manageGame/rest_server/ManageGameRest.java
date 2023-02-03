package de.htwberlin.manageGame.rest_server;

import de.htwberlin.manageGame.export.*;
import de.htwberlin.manageVocab.export.CategoryNotFoundException;
import de.htwberlin.manageVocab.export.TranslationNotFoundException;
import de.htwberlin.manageVocab.export.VocabListNotFoundException;
import de.htwberlin.manageVocab.export.VocabNotFoundException;
import de.htwberlin.userManager.export.UserNotFoundException;
import org.springframework.web.bind.annotation.*;

import java.util.List;

public interface ManageGameRest {

    @GetMapping("/createGame/{user1Id}/{user2Id}")
    Game createGame(@PathVariable("user1Id") int user1Id, @PathVariable("user2Id") int user2Id) throws UserDoesNotExistException, UserNotFoundException;

    @GetMapping("/getGame/{gameId}")
    Game getGame(@PathVariable int gameId) throws GameDoesNotExistException;

    @PutMapping(value = "/updateGame", consumes = "application/json", produces = "application/json")
    Game updateGame(@RequestBody Game game) throws GameDoesNotExistException;

    @PostMapping("/endGame/{gameId}")
    void endGame(@PathVariable int gameId) throws GameDoesNotExistException;

    @GetMapping("/getAllOngoingGamesForUser/{userId}")
    List<Game> getAllOngoingGamesForUser(@PathVariable int userId) throws GameDoesNotExistException, UserDoesNotExistException, UserNotFoundException;

    @PostMapping("/createRound/{gameId}/{round}/{categoryId}")
    Round createRound(@PathVariable("gameId") int gameId, @PathVariable("round") int round, @PathVariable("categoryId") int categoryId) throws GameDoesNotExistException, CategoryNotFoundException, VocabNotFoundException, GameQuestionDoesNotExistException, VocabListNotFoundException, TranslationNotFoundException;

    @PutMapping(value = "/updateRound", consumes = "application/json", produces = "application/json")
    Round updateRound(@RequestBody Round round) throws RoundDoesNotExistException;

    @PostMapping("/endRound/{roundId}")
    void endRound(@PathVariable int roundId) throws RoundDoesNotExistException;

    @PostMapping(value = "/generateQuestions/{categoryId}/{gameId}", consumes = "application/json", produces = "application/json")
    List<GameQuestion> generateQuestions(@PathVariable("categoryId") int categoryId, @PathVariable("gameId") int gameId, @RequestBody Round round) throws CategoryNotFoundException, VocabListNotFoundException, VocabNotFoundException, TranslationNotFoundException, GameDoesNotExistException;

    @PostMapping("/generateAnswers/{gameQuestionId}")
    List<GameAnswer> generateAnswers(@PathVariable int gameQuestionId) throws GameQuestionDoesNotExistException, VocabNotFoundException, TranslationNotFoundException;

    @GetMapping("/getGameQuestionsForRound/{gameId}/{roundId}")
    List<GameQuestion> getGameQuestionsForRound(@PathVariable("gameId") int gameId, @PathVariable("roundId") int roundId) throws GameDoesNotExistException, RoundDoesNotExistException, GameQuestionDoesNotExistException;

    @GetMapping("/getGameAnswersForGameQuestion/{gameQuestionId}")
    List<GameAnswer> getGameAnswersForGameQuestion(@PathVariable int gameQuestionId) throws GameAnswerDoesNotExistException;

    @PostMapping("/lockInAnswer/{gameAnswerId}/{userId}/{isCorrect}")
    void lockInAnswer(@PathVariable("gameAnswerId") int gameAnswerId, @PathVariable("userId") int userId, @PathVariable("isCorrect") boolean isCorrect) throws GameAnswerDoesNotExistException, UserNotFoundException;

    @GetMapping("/getScoreForUser/{userId}/{gameId}")
    int getScoreForUser(@PathVariable("userId") int userId, @PathVariable("gameId") int gameId) throws GameDoesNotExistException, UserDoesNotExistException, RoundResultDoesNotExistException, UserNotFoundException;

    @GetMapping("/getLatestRoundForGame/{gameId}")
    int getLatestRoundForGame(@PathVariable int gameId) throws GameDoesNotExistException;

    @GetMapping("/getOngoingRoundForGame/{gameId}")
    Round getOngoingRoundForGame(@PathVariable int gameId) throws RoundDoesNotExistException;
}
