package de.htwberlin.manageGame.rest_client;

import de.htwberlin.manageGame.export.*;
import de.htwberlin.manageVocab.export.CategoryNotFoundException;
import de.htwberlin.manageVocab.export.TranslationNotFoundException;
import de.htwberlin.manageVocab.export.VocabListNotFoundException;
import de.htwberlin.manageVocab.export.VocabNotFoundException;
import de.htwberlin.userManager.export.UserNotFoundException;

import jakarta.persistence.OptimisticLockException;
import okhttp3.OkHttpClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Service
public class ManageGameRestServiceClientAdapter implements ManageGame {

    private static final OkHttpClient okHttpClient = new OkHttpClient().newBuilder()
            .connectTimeout(30, TimeUnit.SECONDS)
            .readTimeout(30, TimeUnit.SECONDS)
            .writeTimeout(30, TimeUnit.SECONDS)
            .build();

    private static final String URL = "http://localhost:8080/manageGame/";
    static Retrofit rf = new Retrofit.Builder()
            .baseUrl(URL)
            .client(okHttpClient)
            .addConverterFactory(ScalarsConverterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .build();

    @Override
    public Game createGame(int user1Id, int user2Id) throws UserDoesNotExistException, UserNotFoundException {
        ManageGameRestService restService = rf.create(ManageGameRestService.class);
        Call<Game> call = restService.createGame(user1Id, user2Id);
        Response<Game> response = null;
        try {
            response = call.execute();
            if (!response.isSuccessful()) {
                GameApiError gameApiError = ManageGameErrorUtil.parseError(response);
                Exception exp = ManageGameErrorUtil.parseException(gameApiError);
                if(exp instanceof UserDoesNotExistException) {
                    throw (UserDoesNotExistException) exp;
                } else if(exp instanceof UserNotFoundException) {
                    throw (UserNotFoundException) exp;
                } else if (exp instanceof OptimisticLockException) {
                    throw (OptimisticLockException) exp;
                } else {
                    throw (RuntimeException) exp;
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return response.body();
    }

    @Override
    public Game getGame(int gameId) throws GameDoesNotExistException {
        ManageGameRestService restService = rf.create(ManageGameRestService.class);
        Call<Game> call = restService.getGame(gameId);
        Response<Game> response = null;
        try {
            response = call.execute();
            if (!response.isSuccessful()) {
                GameApiError gameApiError = ManageGameErrorUtil.parseError(response);
                Exception exp = ManageGameErrorUtil.parseException(gameApiError);
                if(exp instanceof GameDoesNotExistException) {
                    throw (GameDoesNotExistException) exp;
                } else if (exp instanceof OptimisticLockException) {
                    throw (OptimisticLockException) exp;
                } else {
                    throw (RuntimeException) exp;
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return response.body();
    }

    @Override
    public Game updateGame(Game game) throws GameDoesNotExistException {
        ManageGameRestService restService = rf.create(ManageGameRestService.class);
        Call<Game> call = restService.updateGame(game);
        Response<Game> response = null;
        try {
            response = call.execute();
            if (!response.isSuccessful()) {
                GameApiError gameApiError = ManageGameErrorUtil.parseError(response);
                Exception exp = ManageGameErrorUtil.parseException(gameApiError);
                if(exp instanceof GameDoesNotExistException) {
                    throw (GameDoesNotExistException) exp;
                } else if (exp instanceof OptimisticLockException) {
                    throw (OptimisticLockException) exp;
                } else {
                    throw (RuntimeException) exp;
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return response.body();
    }

    @Override
    public void endGame(int gameId) throws GameDoesNotExistException {
        ManageGameRestService restService = rf.create(ManageGameRestService.class);
        Call<Void> call = restService.endGame(gameId);
        Response<Void> response = null;
        try {
            response = call.execute();
            if (!response.isSuccessful()) {
                GameApiError gameApiError = ManageGameErrorUtil.parseError(response);
                Exception exp = ManageGameErrorUtil.parseException(gameApiError);
                if(exp instanceof GameDoesNotExistException) {
                    throw (GameDoesNotExistException) exp;
                } else if (exp instanceof OptimisticLockException) {
                    throw (OptimisticLockException) exp;
                } else {
                    throw (RuntimeException) exp;
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Game> getAllOngoingGamesForUser(int userId) throws GameDoesNotExistException, UserDoesNotExistException, UserNotFoundException {
        ManageGameRestService restService = rf.create(ManageGameRestService.class);
        Call<List<Game>> call = restService.getAllOngoingGamesForUser(userId);
        Response<List<Game>> response = null;
        try {
            response = call.execute();
            if (!response.isSuccessful()) {
                GameApiError gameApiError = ManageGameErrorUtil.parseError(response);
                Exception exp = ManageGameErrorUtil.parseException(gameApiError);

                if(exp instanceof GameDoesNotExistException) {
                    throw (GameDoesNotExistException) exp;
                } else if(exp instanceof UserDoesNotExistException) {
                    throw (UserDoesNotExistException) exp;
                } else if(exp instanceof UserNotFoundException) {
                    throw (UserNotFoundException) exp;
                } else if (exp instanceof OptimisticLockException) {
                    throw (OptimisticLockException) exp;
                } else {
                    throw (RuntimeException) exp;
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return response.body();
    }

    @Override
    public Round createRound(int gameId, int round, int categoryId) throws GameDoesNotExistException, CategoryNotFoundException, VocabNotFoundException, GameQuestionDoesNotExistException, VocabListNotFoundException, TranslationNotFoundException {
        ManageGameRestService restService = rf.create(ManageGameRestService.class);
        Call<Round> call = restService.createRound(gameId, round, categoryId);
        Response<Round> response = null;
        try {
            response = call.execute();
            if (!response.isSuccessful()) {
                GameApiError gameApiError = ManageGameErrorUtil.parseError(response);
                Exception exp = ManageGameErrorUtil.parseException(gameApiError);

                if(exp instanceof GameDoesNotExistException) {
                    throw (GameDoesNotExistException) exp;
                } else if(exp instanceof CategoryNotFoundException) {
                    throw (CategoryNotFoundException) exp;
                } else if(exp instanceof VocabNotFoundException) {
                    throw (VocabNotFoundException) exp;
                } else if(exp instanceof GameQuestionDoesNotExistException) {
                    throw (GameQuestionDoesNotExistException) exp;
                } else if(exp instanceof VocabListNotFoundException) {
                    throw (VocabListNotFoundException) exp;
                } else if(exp instanceof TranslationNotFoundException) {
                    throw (TranslationNotFoundException) exp;
                } else if (exp instanceof OptimisticLockException) {
                    throw (OptimisticLockException) exp;
                } else {
                    throw (RuntimeException) exp;
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return response.body();
    }

    @Override
    public Round updateRound(Round round) throws RoundDoesNotExistException {
        ManageGameRestService restService = rf.create(ManageGameRestService.class);
        Call<Round> call = restService.updateRound(round);
        Response<Round> response = null;
        try {
            response = call.execute();
            if (!response.isSuccessful()) {
                GameApiError gameApiError = ManageGameErrorUtil.parseError(response);
                Exception exp = ManageGameErrorUtil.parseException(gameApiError);

                if(exp instanceof RoundDoesNotExistException) {
                    throw (RoundDoesNotExistException) exp;
                } else if (exp instanceof OptimisticLockException) {
                    throw (OptimisticLockException) exp;
                } else {
                    throw (RuntimeException) exp;
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return response.body();
    }

    @Override
    public void endRound(int roundId) throws RoundDoesNotExistException {
        ManageGameRestService restService = rf.create(ManageGameRestService.class);
        Call<Void> call = restService.endRound(roundId);
        Response<Void> response = null;
        try {
            response = call.execute();
            if (!response.isSuccessful()) {
                GameApiError gameApiError = ManageGameErrorUtil.parseError(response);
                Exception exp = ManageGameErrorUtil.parseException(gameApiError);

                if(exp instanceof RoundDoesNotExistException) {
                    throw (RoundDoesNotExistException) exp;
                } else if (exp instanceof OptimisticLockException) {
                    throw (OptimisticLockException) exp;
                } else {
                    throw (RuntimeException) exp;
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<GameQuestion> generateQuestions(int categoryId, int gameId, Round round) throws CategoryNotFoundException, VocabListNotFoundException, VocabNotFoundException, TranslationNotFoundException, GameDoesNotExistException {
        ManageGameRestService restService = rf.create(ManageGameRestService.class);
        Call<List<GameQuestion>> call = restService.generateQuestions(categoryId, gameId, round);
        Response<List<GameQuestion>> response = null;
        try {
            response = call.execute();
            if (!response.isSuccessful()) {
                GameApiError gameApiError = ManageGameErrorUtil.parseError(response);
                Exception exp = ManageGameErrorUtil.parseException(gameApiError);

                if(exp instanceof CategoryNotFoundException) {
                    throw (CategoryNotFoundException) exp;
                } else if(exp instanceof VocabListNotFoundException) {
                    throw (VocabListNotFoundException) exp;
                } else if(exp instanceof VocabNotFoundException) {
                    throw (VocabNotFoundException) exp;
                } else if(exp instanceof TranslationNotFoundException) {
                    throw (TranslationNotFoundException) exp;
                } else if(exp instanceof GameDoesNotExistException) {
                    throw (GameDoesNotExistException) exp;
                } else if (exp instanceof OptimisticLockException) {
                    throw (OptimisticLockException) exp;
                } else {
                    throw (RuntimeException) exp;
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return response.body();
    }

    @Override
    public List<GameAnswer> generateAnswers(int gameQuestionId) throws GameQuestionDoesNotExistException, VocabNotFoundException, TranslationNotFoundException {
        ManageGameRestService restService = rf.create(ManageGameRestService.class);
        Call<List<GameAnswer>> call = restService.generateAnswers(gameQuestionId);
        Response<List<GameAnswer>> response = null;
        try {
            response = call.execute();
            if (!response.isSuccessful()) {
                GameApiError gameApiError = ManageGameErrorUtil.parseError(response);
                Exception exp = ManageGameErrorUtil.parseException(gameApiError);

                if(exp instanceof GameQuestionDoesNotExistException) {
                    throw (GameQuestionDoesNotExistException) exp;
                } else if(exp instanceof VocabNotFoundException) {
                    throw (VocabNotFoundException) exp;
                } else if(exp instanceof TranslationNotFoundException) {
                    throw (TranslationNotFoundException) exp;
                } else if (exp instanceof OptimisticLockException) {
                    throw (OptimisticLockException) exp;
                } else {
                    throw (RuntimeException) exp;
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return response.body();
    }

    @Override
    public List<GameQuestion> getGameQuestionsForRound(int gameId, int roundId) throws GameDoesNotExistException, RoundDoesNotExistException, GameQuestionDoesNotExistException {
        ManageGameRestService restService = rf.create(ManageGameRestService.class);
        Call<List<GameQuestion>> call = restService.getGameQuestionsForRound(gameId, roundId);
        Response<List<GameQuestion>> response = null;
        try {
            response = call.execute();
            if (!response.isSuccessful()) {
                GameApiError gameApiError = ManageGameErrorUtil.parseError(response);
                Exception exp = ManageGameErrorUtil.parseException(gameApiError);

                if(exp instanceof GameDoesNotExistException) {
                    throw (GameDoesNotExistException) exp;
                } else if(exp instanceof RoundDoesNotExistException) {
                    throw (RoundDoesNotExistException) exp;
                } else if(exp instanceof GameQuestionDoesNotExistException) {
                    throw (GameQuestionDoesNotExistException) exp;
                } else if (exp instanceof OptimisticLockException) {
                    throw (OptimisticLockException) exp;
                } else {
                    throw (RuntimeException) exp;
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return response.body();
    }

    @Override
    public List<GameAnswer> getGameAnswersForGameQuestion(int gameQuestionId) throws GameAnswerDoesNotExistException {
        ManageGameRestService restService = rf.create(ManageGameRestService.class);
        Call<List<GameAnswer>> call = restService.getGameAnswersForGameQuestion(gameQuestionId);
        Response<List<GameAnswer>> response = null;
        try {
            response = call.execute();
            if (!response.isSuccessful()) {
                GameApiError gameApiError = ManageGameErrorUtil.parseError(response);
                Exception exp = ManageGameErrorUtil.parseException(gameApiError);

                if(exp instanceof GameAnswerDoesNotExistException) {
                    throw (GameAnswerDoesNotExistException) exp;
                } else if (exp instanceof OptimisticLockException) {
                    throw (OptimisticLockException) exp;
                } else {
                    throw (RuntimeException) exp;
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return response.body();
    }

    @Override
    public void lockInAnswer(int gameAnswerId, int userId, boolean isCorrect) throws GameAnswerDoesNotExistException, UserNotFoundException {
        ManageGameRestService restService = rf.create(ManageGameRestService.class);
        Call<Void> call = restService.lockInAnswer(gameAnswerId, userId, isCorrect);
        Response<Void> response = null;
        try {
            response = call.execute();
            if (!response.isSuccessful()) {
                GameApiError gameApiError = ManageGameErrorUtil.parseError(response);
                Exception exp = ManageGameErrorUtil.parseException(gameApiError);

                if(exp instanceof GameAnswerDoesNotExistException) {
                    throw (GameAnswerDoesNotExistException) exp;
                } else if(exp instanceof UserNotFoundException) {
                    throw (UserNotFoundException) exp;
                } else if (exp instanceof OptimisticLockException) {
                    throw (OptimisticLockException) exp;
                } else {
                    throw (RuntimeException) exp;
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public int getScoreForUser(int userId, int gameId) throws GameDoesNotExistException, UserDoesNotExistException, RoundResultDoesNotExistException, UserNotFoundException {
        ManageGameRestService restService = rf.create(ManageGameRestService.class);
        Call<Integer> call = restService.getScoreForUser(userId, gameId);
        Response<Integer> response = null;
        try {
            response = call.execute();
            if (!response.isSuccessful()) {
                GameApiError gameApiError = ManageGameErrorUtil.parseError(response);
                Exception exp = ManageGameErrorUtil.parseException(gameApiError);

                if(exp instanceof GameDoesNotExistException) {
                    throw (GameDoesNotExistException) exp;
                } else if(exp instanceof UserDoesNotExistException) {
                    throw (UserDoesNotExistException) exp;
                } else if(exp instanceof RoundResultDoesNotExistException) {
                    throw (RoundResultDoesNotExistException) exp;
                } else if(exp instanceof UserNotFoundException) {
                    throw (UserNotFoundException) exp;
                } else if (exp instanceof OptimisticLockException) {
                    throw (OptimisticLockException) exp;
                } else {
                    throw (RuntimeException) exp;
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return response.body();
    }

    @Override
    public int getLatestRoundForGame(int gameId) throws GameDoesNotExistException {
        ManageGameRestService restService = rf.create(ManageGameRestService.class);
        Call<Integer> call = restService.getLatestRoundForGame(gameId);
        Response<Integer> response = null;
        try {
            response = call.execute();
            if (!response.isSuccessful()) {
                GameApiError gameApiError = ManageGameErrorUtil.parseError(response);
                Exception exp = ManageGameErrorUtil.parseException(gameApiError);

                if(exp instanceof GameDoesNotExistException) {
                    throw (GameDoesNotExistException) exp;
                } else if (exp instanceof OptimisticLockException) {
                    throw (OptimisticLockException) exp;
                } else {
                    throw (RuntimeException) exp;
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return response.body();
    }

    @Override
    public Round getOngoingRoundForGame(int gameId) throws RoundDoesNotExistException {
        ManageGameRestService restService = rf.create(ManageGameRestService.class);
        Call<Round> call = restService.getOngoingRoundForGame(gameId);
        Response<Round> response = null;
        try {
            response = call.execute();
            if (!response.isSuccessful()) {
                GameApiError gameApiError = ManageGameErrorUtil.parseError(response);
                Exception exp = ManageGameErrorUtil.parseException(gameApiError);

                if(exp instanceof RoundDoesNotExistException) {
                    throw (RoundDoesNotExistException) exp;
                } else if (exp instanceof OptimisticLockException) {
                    throw (OptimisticLockException) exp;
                } else {
                    throw (RuntimeException) exp;
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return response.body();
    }
}
