package de.htwberlin.manageVocab.rest_client;

import de.htwberlin.manageVocab.export.*;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

import java.io.File;
import java.io.IOException;
import java.util.List;

public interface ManageVocabRestService {
    @GET("getAllCategories")
    Call<List<Category>> getAllCategories() throws CategoryNotFoundException;

    @GET("getCategory/{categoryId}")
    Call<Category> getCategory(@Path("categoryId") int categoryId) throws CategoryNotFoundException;

    @POST("parseVocabList")
    Call<String> parseVocabList(@Body String file) throws IOException, VocabDAOException;

    @GET("getPossibleTranslationsFromVocabId/{vocabId}/{numberOfTranslations}")
    Call<List<Translation>> getPossibleTranslationsFromVocabId(@Path("vocabId") int vocabId, @Path("numberOfTranslations") int numberOfTranslations) throws VocabNotFoundException, TranslationNotFoundException;

    @GET("getRandomVocabListFromCategory/{categoryId}")
    Call<VocabList> getRandomVocabListFromCategory(@Path("categoryId") int categoryId) throws CategoryNotFoundException, VocabListNotFoundException;

    @GET("getRandomVocabFromVocabList/{vocabListId}")
    Call<Vocab> getRandomVocabFromVocabList(@Path("vocabListId") int vocabListId) throws VocabListNotFoundException, VocabNotFoundException;

    @GET("getTranslationFromVocabId/{vocabId}")
    Call<Translation> getTranslationFromVocabId(@Path("vocabId") int vocabId) throws VocabNotFoundException, TranslationNotFoundException;
}
