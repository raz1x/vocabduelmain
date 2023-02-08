package de.htwberlin.manageVocab.rest_client;

import de.htwberlin.manageVocab.export.*;
import jakarta.persistence.OptimisticLockException;
import okhttp3.OkHttpClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Service
public class ManageVocabRestServiceClientAdapter implements ManageVocab {

    private static final OkHttpClient okHttpClient = new OkHttpClient().newBuilder()
            .connectTimeout(30, TimeUnit.SECONDS)
            .readTimeout(30, TimeUnit.SECONDS)
            .writeTimeout(30, TimeUnit.SECONDS)
            .build();

    private static final String URL = "http://localhost:8080/manageVocab/";
    static Retrofit rf = new Retrofit.Builder()
            .baseUrl(URL)
            .client(okHttpClient)
            .addConverterFactory(ScalarsConverterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .build();

    @Override
    public List<Category> getAllCategories() throws CategoryNotFoundException {
        ManageVocabRestService restService = rf.create(ManageVocabRestService.class);
        Call<List<Category>> call = restService.getAllCategories();
        Response<List<Category>> response = null;
        try {
            response = call.execute();
            if(!response.isSuccessful()) {
                VocabApiError vocabApiError = ManageVocabErrorUtil.parseError(response);
                Exception exp = ManageVocabErrorUtil.parseException(vocabApiError);
                if (exp instanceof CategoryNotFoundException) {
                    throw (CategoryNotFoundException) exp;
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
    public Category getCategory(int categoryId) throws CategoryNotFoundException {
        ManageVocabRestService restService = rf.create(ManageVocabRestService.class);
        Call<Category> call = restService.getCategory(categoryId);
        Response<Category> response = null;
        try {
            response = call.execute();
            if(!response.isSuccessful()) {
                VocabApiError vocabApiError = ManageVocabErrorUtil.parseError(response);
                Exception exp = ManageVocabErrorUtil.parseException(vocabApiError);
                if (exp instanceof CategoryNotFoundException) {
                    throw (CategoryNotFoundException) exp;
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
    public void parseVocabList(File file) throws VocabDAOException, IOException {
        ManageVocabRestService restService = rf.create(ManageVocabRestService.class);
        Call<String> call = restService.parseVocabList(Files.readString(file.toPath()));
        Response<String> response = null;
        try {
            response = call.execute();
            System.out.println(response.body());
            if(!response.isSuccessful()) {
                VocabApiError vocabApiError = ManageVocabErrorUtil.parseError(response);
                Exception exp = ManageVocabErrorUtil.parseException(vocabApiError);
                if (exp instanceof VocabDAOException) {
                    throw (VocabDAOException) exp;
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
    public List<Translation> getPossibleTranslationsFromVocabId(int vocabId, int numberOfTranslations) throws VocabNotFoundException, TranslationNotFoundException {
        ManageVocabRestService restService = rf.create(ManageVocabRestService.class);
        Call<List<Translation>> call = restService.getPossibleTranslationsFromVocabId(vocabId, numberOfTranslations);
        Response<List<Translation>> response = null;
        try {
            response = call.execute();
            if(!response.isSuccessful()) {
                VocabApiError vocabApiError = ManageVocabErrorUtil.parseError(response);
                Exception exp = ManageVocabErrorUtil.parseException(vocabApiError);
                if (exp instanceof VocabNotFoundException) {
                    throw (VocabNotFoundException) exp;
                } else if (exp instanceof TranslationNotFoundException) {
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
    public VocabList getRandomVocabListFromCategory(int categoryId) throws CategoryNotFoundException, VocabListNotFoundException {
        ManageVocabRestService restService = rf.create(ManageVocabRestService.class);
        Call<VocabList> call = restService.getRandomVocabListFromCategory(categoryId);
        Response<VocabList> response = null;
        try {
            response = call.execute();
            if(!response.isSuccessful()) {
                VocabApiError vocabApiError = ManageVocabErrorUtil.parseError(response);
                Exception exp = ManageVocabErrorUtil.parseException(vocabApiError);
                if (exp instanceof CategoryNotFoundException) {
                    throw (CategoryNotFoundException) exp;
                } else if (exp instanceof VocabListNotFoundException) {
                    throw (VocabListNotFoundException) exp;
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
    public Vocab getRandomVocabFromVocabList(int vocabListId) throws VocabListNotFoundException, VocabNotFoundException {
        ManageVocabRestService restService = rf.create(ManageVocabRestService.class);
        Call<Vocab> call = restService.getRandomVocabFromVocabList(vocabListId);
        Response<Vocab> response = null;
        try {
            response = call.execute();
            if(!response.isSuccessful()) {
                VocabApiError vocabApiError = ManageVocabErrorUtil.parseError(response);
                Exception exp = ManageVocabErrorUtil.parseException(vocabApiError);
                if (exp instanceof VocabListNotFoundException) {
                    throw (VocabListNotFoundException) exp;
                } else if (exp instanceof VocabNotFoundException) {
                    throw (VocabNotFoundException) exp;
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
    public Translation getTranslationFromVocabId(int vocabId) throws VocabNotFoundException, TranslationNotFoundException {
        ManageVocabRestService restService = rf.create(ManageVocabRestService.class);
        Call<Translation> call = restService.getTranslationFromVocabId(vocabId);
        Response<Translation> response = null;
        try {
            response = call.execute();
            if(!response.isSuccessful()) {
                VocabApiError vocabApiError = ManageVocabErrorUtil.parseError(response);
                Exception exp = ManageVocabErrorUtil.parseException(vocabApiError);
                if (exp instanceof VocabNotFoundException) {
                    throw (VocabNotFoundException) exp;
                } else if (exp instanceof TranslationNotFoundException) {
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
}
