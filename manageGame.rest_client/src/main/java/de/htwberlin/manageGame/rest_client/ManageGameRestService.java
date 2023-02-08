package de.htwberlin.manageGame.rest_client;

import de.htwberlin.manageGame.export.*;
import de.htwberlin.manageVocab.export.CategoryNotFoundException;
import de.htwberlin.manageVocab.export.TranslationNotFoundException;
import de.htwberlin.manageVocab.export.VocabListNotFoundException;
import de.htwberlin.manageVocab.export.VocabNotFoundException;
import de.htwberlin.userManager.export.UserNotFoundException;
import retrofit2.Call;
import retrofit2.http.*;

import java.util.List;

public interface ManageGameRestService {

    @GET("createGame/{user1Id}/{user2Id}")
    Call<Game> createGame(@Path("user1Id") int user1Id, @Path("user2Id") int user2Id) throws UserNotFoundException;

    @GET("getGame/{gameId}")
    Call<Game> getGame(@Path("gameId") int gameId) throws GameDoesNotExistException;

    @PUT("updateGame")
    Call<Game> updateGame(@Body Game game) throws GameDoesNotExistException;

    @POST("endGame/{gameId}")
    Call<Void> endGame(@Path("gameId") int gameId) throws GameDoesNotExistException;

    @GET("getAllOngoingGamesForUser/{userId}")
    Call<List<Game>> getAllOngoingGamesForUser(@Path("userId") int userId) throws GameDoesNotExistException, UserNotFoundException;

    @POST("createRound/{gameId}/{round}/{categoryId}")
    Call<Round> createRound(@Path("gameId") int gameId, @Path("round") int round, @Path("categoryId") int categoryId) throws GameDoesNotExistException, CategoryNotFoundException, VocabNotFoundException, GameQuestionDoesNotExistException, VocabListNotFoundException, TranslationNotFoundException;

    @PUT("updateRound")
    Call<Round> updateRound(@Body Round round) throws RoundDoesNotExistException;

    @POST("endRound/{roundId}")
    Call<Void> endRound(@Path("roundId") int roundId) throws RoundDoesNotExistException;

    @POST("generateQuestions/{categoryId}/{gameId}")
    Call<List<GameQuestion>> generateQuestions(@Path("categoryId") int categoryId, @Path("gameId") int gameId, @Body Round round) throws CategoryNotFoundException, VocabListNotFoundException, VocabNotFoundException, TranslationNotFoundException, GameDoesNotExistException;

    @POST("generateAnswers/{gameQuestionId}")
    Call<List<GameAnswer>> generateAnswers(@Path("gameQuestionId") int gameQuestionId) throws GameQuestionDoesNotExistException, VocabNotFoundException, TranslationNotFoundException;

    @GET("getGameQuestionsForRound/{gameId}/{roundId}")
    Call<List<GameQuestion>> getGameQuestionsForRound(@Path("gameId") int gameId, @Path("roundId") int roundId) throws GameDoesNotExistException, RoundDoesNotExistException, GameQuestionDoesNotExistException;

    @GET("getGameAnswersForGameQuestion/{gameQuestionId}")
    Call<List<GameAnswer>> getGameAnswersForGameQuestion(@Path("gameQuestionId") int gameQuestionId) throws GameAnswerDoesNotExistException;

    @POST("lockInAnswer/{gameAnswerId}/{userId}/{isCorrect}")
    Call<Void> lockInAnswer(@Path("gameAnswerId") int gameAnswerId, @Path("userId") int userId, @Path("isCorrect") boolean isCorrect) throws GameAnswerDoesNotExistException, UserNotFoundException;

    @GET("getScoreForUser/{userId}/{gameId}")
    Call<Integer> getScoreForUser(@Path("userId") int userId, @Path("gameId") int gameId) throws GameDoesNotExistException, RoundResultDoesNotExistException, UserNotFoundException;

    @GET("getLatestRoundForGame/{gameId}")
    Call<Integer> getLatestRoundForGame(@Path("gameId") int gameId) throws GameDoesNotExistException;

    @GET("getOngoingRoundForGame/{gameId}")
    Call<Round> getOngoingRoundForGame(@Path("gameId") int gameId) throws RoundDoesNotExistException;
}
