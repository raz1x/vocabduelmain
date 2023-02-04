package de.htwberlin.manageGame.rest_client;

import de.htwberlin.manageGame.export.*;
import de.htwberlin.manageVocab.export.CategoryNotFoundException;
import de.htwberlin.manageVocab.export.TranslationNotFoundException;
import de.htwberlin.manageVocab.export.VocabListNotFoundException;
import de.htwberlin.manageVocab.export.VocabNotFoundException;
import de.htwberlin.userManager.export.UserNotFoundException;
import okhttp3.ResponseBody;
import retrofit2.Converter;
import retrofit2.Response;

import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.Constructor;

public class ErrorUtils {

    public static GameApiError parseError(Response<?> response) throws IOException {
        Converter<ResponseBody, GameApiError> converter = ManageGameRestServiceClientAdapter.rf
                .responseBodyConverter(GameApiError.class, new Annotation[0]);
        GameApiError error;
        try {
            error = converter.convert(response.errorBody());
        } catch (IOException e) {
            return new GameApiError();
        }
        return error;
    }

    public static Exception parseException(GameApiError error) {
        Exception exp = null;
        try {
            Class<?> exceptionClass = Class.forName("de.htwberlin.manageGame.export." + error.getExceptionClass());
            Constructor<?> constructor = exceptionClass.getConstructor(String.class);
            exp = switch (exceptionClass.getSimpleName()) {
                case "UserDoesNotExistException" ->
                        (UserDoesNotExistException) constructor.newInstance(error.getMessage());
                case "UserNotFoundException" ->
                        (UserNotFoundException) constructor.newInstance(error.getMessage());
                case "GameDoesNotExistException" ->
                        (GameDoesNotExistException) constructor.newInstance(error.getMessage());
                case "CategoryNotFoundException" ->
                        (CategoryNotFoundException) constructor.newInstance(error.getMessage());
                case "TranslationNotFoundException" ->
                        (TranslationNotFoundException) constructor.newInstance(error.getMessage());
                case "VocabListNotFoundException" ->
                        (VocabListNotFoundException) constructor.newInstance(error.getMessage());
                case "VocabNotFoundException" ->
                        (VocabNotFoundException) constructor.newInstance(error.getMessage());
                case "GameQuestionDoesNotExistException" ->
                        (GameQuestionDoesNotExistException) constructor.newInstance(error.getMessage());
                case "RoundDoesNotExistException" ->
                        (RoundDoesNotExistException) constructor.newInstance(error.getMessage());
                case "RoundResultDoesNotExistException" ->
                        (RoundResultDoesNotExistException) constructor.newInstance(error.getMessage());
                case "GameAnswerDoesNotExistException" ->
                        (GameAnswerDoesNotExistException) constructor.newInstance(error.getMessage());
                default -> (Exception) constructor.newInstance(error.getMessage());
            };
        } catch (Exception e) {
            e.printStackTrace();
        }
        return exp;
    }
}