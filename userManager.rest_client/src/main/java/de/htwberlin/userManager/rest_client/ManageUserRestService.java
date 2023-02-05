package de.htwberlin.userManager.rest_client;

import de.htwberlin.userManager.export.User;
import de.htwberlin.userManager.export.UserAlreadyExistsException;
import de.htwberlin.userManager.export.UserNotFoundException;
import de.htwberlin.userManager.export.WrongPasswordException;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

import java.util.List;

public interface ManageUserRestService {
    @POST("registerUser/{userName}/{password}")
    Call<User> registerUser(@Path("userName") String userName, @Path("password") String password) throws UserAlreadyExistsException;

    @POST("loginUser/{userName}/{password}")
    Call<User> loginUser(@Path("userName") String userName, @Path("password") String password) throws UserNotFoundException, WrongPasswordException;

    @POST("logoutUser/{userId}")
    Call<Void> logoutUser(@Path("userId") int userId) throws UserNotFoundException;

    @POST("deleteUser/{userId}")
    Call<Void> deleteUser(@Path("userId") int userId) throws UserNotFoundException;

    @POST("updateUserName/{userId}/{userName}")
    Call<User> updateUserName(@Path("userId") int userId, @Path("userName") String userName) throws UserNotFoundException, UserAlreadyExistsException;

    @POST("updatePassword/{userId}/{newPassword}")
    Call<User> updatePassword(@Path("userId") int userId, @Path("newPassword") String newPassword) throws UserNotFoundException;

    @GET("getByName/{userName}")
    Call<User> getByName(@Path("userName") String userName) throws UserNotFoundException;

    @GET("getById/{userId}")
    Call<User> getById(@Path("userId") int userId) throws UserNotFoundException;

    @GET("getAllUsers")
    Call<List<User>> getAllUsers() throws UserNotFoundException;

    @GET("getOpponents/{currentUserId}")
    Call<List<User> >getOpponents(@Path("currentUserId") int currentUserId) throws UserNotFoundException;

    @GET("userExists/{userName}")
    Call<Boolean> userExists(@Path("userName") String userName);
}
