package de.htwberlin.manageVocab.rest_client;

import de.htwberlin.manageVocab.export.*;
import okhttp3.ResponseBody;
import retrofit2.Converter;
import retrofit2.Response;

import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.Constructor;

public class ManageVocabErrorUtil {

    public static VocabApiError parseError(Response<?> response) throws IOException {
        Converter<ResponseBody, VocabApiError> converter = ManageVocabRestServiceClientAdapter.rf
                .responseBodyConverter(VocabApiError.class, new Annotation[0]);
        VocabApiError error;
        try {
            error = converter.convert(response.errorBody());
        } catch (IOException e) {
            return new VocabApiError();
        }
        return error;
    }

    public static Exception parseException(VocabApiError error) {
        Exception exp = null;
        try {
            Class<?> exceptionClass = Class.forName("de.htwberlin.manageVocab.export." + error.getExceptionClass());
            Constructor<?> constructor = exceptionClass.getConstructor(String.class);
            exp = switch (exceptionClass.getSimpleName()) {
                case "CategoryNotFoundException" ->
                        (CategoryNotFoundException) constructor.newInstance(error.getMessage());
                case "VocabNotFoundException" ->
                        (VocabNotFoundException) constructor.newInstance(error.getMessage());
                case "TranslationNotFoundException" ->
                        (TranslationNotFoundException) constructor.newInstance(error.getMessage());
                case "VocabListNotFoundException" ->
                        (VocabListNotFoundException) constructor.newInstance(error.getMessage());
                case "VocabDAOException" ->
                        (VocabDAOException) constructor.newInstance(error.getMessage());
                default -> (Exception) constructor.newInstance(error.getMessage());
            };
        } catch (Exception e) {
            e.printStackTrace();
        }
        return exp;
    }
}
