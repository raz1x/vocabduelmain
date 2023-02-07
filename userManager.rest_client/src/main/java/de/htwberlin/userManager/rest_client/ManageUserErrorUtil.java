package de.htwberlin.userManager.rest_client;

import de.htwberlin.userManager.export.UserAlreadyExistsException;
import de.htwberlin.userManager.export.UserApiError;
import de.htwberlin.userManager.export.UserNotFoundException;
import de.htwberlin.userManager.export.WrongPasswordException;
import okhttp3.ResponseBody;
import retrofit2.Converter;
import retrofit2.Response;

import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.Constructor;
import java.util.Objects;

public class ManageUserErrorUtil {

    public static UserApiError parseError(Response<?> response) throws IOException {
        Converter<ResponseBody, UserApiError> converter = ManageUserRestServiceClientAdapter.rf
                .responseBodyConverter(UserApiError.class, new Annotation[0]);
        UserApiError error;
        try {
            error = converter.convert(response.errorBody());
        } catch (IOException e) {
            return new UserApiError();
        }
        return error;
    }

    public static Exception parseException(UserApiError error) {
        Exception exp = null;
        if (Objects.equals(error.getExceptionClass(), "RuntimeException")) {
            return new RuntimeException(error.getMessage());
        }
        try {
            Class<?> exceptionClass = Class.forName("de.htwberlin.userManager.export." + error.getExceptionClass());
            Constructor<?> constructor = exceptionClass.getConstructor(String.class);
            exp = switch (exceptionClass.getSimpleName()) {
                case "UserAlreadyExistsException" ->
                        (UserAlreadyExistsException) constructor.newInstance(error.getMessage());
                case "UserNotFoundException" ->
                        (UserNotFoundException) constructor.newInstance(error.getMessage());
                case "WrongPasswordException" ->
                        (WrongPasswordException) constructor.newInstance(error.getMessage());
                default -> (Exception) constructor.newInstance(error.getMessage());
            };
        } catch (Exception e) {
            e.printStackTrace();
        }
        return exp;
    }
}
