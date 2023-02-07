package de.htwberlin.userManager.rest_client;

import de.htwberlin.userManager.export.*;
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
public class ManageUserRestServiceClientAdapter implements ManageUser {

    private static final OkHttpClient okHttpClient = new OkHttpClient().newBuilder()
            .connectTimeout(30, TimeUnit.SECONDS)
            .readTimeout(30, TimeUnit.SECONDS)
            .writeTimeout(30, TimeUnit.SECONDS)
            .build();

    private static final String URL = "http://localhost:8080/manageUser/";
    static Retrofit rf = new Retrofit.Builder()
            .baseUrl(URL)
            .client(okHttpClient)
            .addConverterFactory(ScalarsConverterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .build();

    @Override
    public User registerUser(String userName, String password) throws UserAlreadyExistsException {
        ManageUserRestService restService = rf.create(ManageUserRestService.class);
        Call<User> call = restService.registerUser(userName, password);
        Response<User> response = null;
        try {
            response = call.execute();
            if(!response.isSuccessful()) {
                UserApiError userApiError = ManageUserErrorUtil.parseError(response);
                Exception exp = ManageUserErrorUtil.parseException(userApiError);
                if (exp instanceof UserAlreadyExistsException) {
                    throw (UserAlreadyExistsException) exp;
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
    public User loginUser(String userName, String password) throws UserNotFoundException, WrongPasswordException {
        ManageUserRestService restService = rf.create(ManageUserRestService.class);
        Call<User> call = restService.loginUser(userName, password);
        Response<User> response = null;
        try {
            response = call.execute();
            if(!response.isSuccessful()) {
                UserApiError userApiError = ManageUserErrorUtil.parseError(response);
                Exception exp = ManageUserErrorUtil.parseException(userApiError);
                if (exp instanceof UserNotFoundException) {
                    throw (UserNotFoundException) exp;
                } else if (exp instanceof WrongPasswordException) {
                    throw (WrongPasswordException) exp;
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
    public void logoutUser(int userId) throws UserNotFoundException {
        ManageUserRestService restService = rf.create(ManageUserRestService.class);
        Call<Void> call = restService.logoutUser(userId);
        Response<Void> response = null;
        try {
            response = call.execute();
            if(!response.isSuccessful()) {
                UserApiError userApiError = ManageUserErrorUtil.parseError(response);
                Exception exp = ManageUserErrorUtil.parseException(userApiError);
                if (exp instanceof UserNotFoundException) {
                    throw (UserNotFoundException) exp;
                } else {
                    throw (RuntimeException) exp;
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void deleteUser(int userId) throws UserNotFoundException {
        ManageUserRestService restService = rf.create(ManageUserRestService.class);
        Call<Void> call = restService.deleteUser(userId);
        Response<Void> response = null;
        try {
            response = call.execute();
            if(!response.isSuccessful()) {
                UserApiError userApiError = ManageUserErrorUtil.parseError(response);
                Exception exp = ManageUserErrorUtil.parseException(userApiError);
                if (exp instanceof UserNotFoundException) {
                    throw (UserNotFoundException) exp;
                } else {
                    throw (RuntimeException) exp;
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public User updateUserName(int userId, String userName) throws UserNotFoundException, UserAlreadyExistsException {
        ManageUserRestService restService = rf.create(ManageUserRestService.class);
        Call<User> call = restService.updateUserName(userId, userName);
        Response<User> response = null;
        try {
            response = call.execute();
            if(!response.isSuccessful()) {
                UserApiError userApiError = ManageUserErrorUtil.parseError(response);
                Exception exp = ManageUserErrorUtil.parseException(userApiError);
                if (exp instanceof UserNotFoundException) {
                    throw (UserNotFoundException) exp;
                } else if (exp instanceof UserAlreadyExistsException) {
                    throw (UserAlreadyExistsException) exp;
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
    public User updatePassword(int userId, String newPassword) throws UserNotFoundException {
        ManageUserRestService restService = rf.create(ManageUserRestService.class);
        Call<User> call = restService.updatePassword(userId, newPassword);
        Response<User> response = null;
        try {
            response = call.execute();
            if(!response.isSuccessful()) {
                UserApiError userApiError = ManageUserErrorUtil.parseError(response);
                Exception exp = ManageUserErrorUtil.parseException(userApiError);
                if (exp instanceof UserNotFoundException) {
                    throw (UserNotFoundException) exp;
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
    public User getByName(String userName) throws UserNotFoundException {
        ManageUserRestService restService = rf.create(ManageUserRestService.class);
        Call<User> call = restService.getByName(userName);
        Response<User> response = null;
        try {
            response = call.execute();
            if(!response.isSuccessful()) {
                UserApiError userApiError = ManageUserErrorUtil.parseError(response);
                Exception exp = ManageUserErrorUtil.parseException(userApiError);
                if (exp instanceof UserNotFoundException) {
                    throw (UserNotFoundException) exp;
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
    public User getById(int userId) throws UserNotFoundException {
        ManageUserRestService restService = rf.create(ManageUserRestService.class);
        Call<User> call = restService.getById(userId);
        Response<User> response = null;
        try {
            response = call.execute();
            if(!response.isSuccessful()) {
                UserApiError userApiError = ManageUserErrorUtil.parseError(response);
                Exception exp = ManageUserErrorUtil.parseException(userApiError);
                if (exp instanceof UserNotFoundException) {
                    throw (UserNotFoundException) exp;
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
    public List<User> getAllUsers() throws UserNotFoundException {
        ManageUserRestService restService = rf.create(ManageUserRestService.class);
        Call<List<User>> call = restService.getAllUsers();
        Response<List<User>> response = null;
        try {
            response = call.execute();
            if(!response.isSuccessful()) {
                UserApiError userApiError = ManageUserErrorUtil.parseError(response);
                Exception exp = ManageUserErrorUtil.parseException(userApiError);
                if (exp instanceof UserNotFoundException) {
                    throw (UserNotFoundException) exp;
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
    public List<User> getOpponents(int currentUserId) throws UserNotFoundException {
        ManageUserRestService restService = rf.create(ManageUserRestService.class);
        Call<List<User>> call = restService.getOpponents(currentUserId);
        Response<List<User>> response = null;
        try {
            response = call.execute();
            if(!response.isSuccessful()) {
                UserApiError userApiError = ManageUserErrorUtil.parseError(response);
                Exception exp = ManageUserErrorUtil.parseException(userApiError);
                if (exp instanceof UserNotFoundException) {
                    throw (UserNotFoundException) exp;
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
    public boolean userExists(String userName) {
        ManageUserRestService restService = rf.create(ManageUserRestService.class);
        Call<Boolean> call = restService.userExists(userName);
        Response<Boolean> response = null;
        try {
            response = call.execute();
            if(!response.isSuccessful()) {
                UserApiError userApiError = ManageUserErrorUtil.parseError(response);
                Exception exp = ManageUserErrorUtil.parseException(userApiError);
                throw (RuntimeException) exp;
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return response.body();
    }
}
