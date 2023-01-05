package de.htwberlin.manageVocab.export;

import java.io.File;
import java.io.IOException;
import java.util.List;

public interface ManageVocab {

    /**
     * Returns a list of all categories
     * @return Returns a list of all categories
     */
    List<Category> getAllCategories() throws CategoryNotFoundException;

    /**
     * Gets a category by its id
     * @param categoryId The id of the category
     * @return The category object
     */
    Category getCategory(int categoryId) throws CategoryNotFoundException;

    /**
     * Parses a text file into a vocab list and saves it to the database
     * @param file The text file.
     */
    public void parseVocabList(File file) throws IOException, VocabDAOException;

    /**
     * Gets a list of possible translations for a vocab
     * @param vocabId The id of the vocab
     * @param numberOfTranslations The number of translations to be returned
     * @return Returns a list of VocabList objects
     */
    List<Translation> getPossibleTranslationsFromVocabId(int vocabId, int numberOfTranslations) throws VocabNotFoundException, VocabDAOException, TranslationNotFoundException;

    /**
     * Gets a random vocab list from a category
     * @param categoryId The id of the vocab
     * @return Returns a list of VocabList objects
     */
    VocabList getRandomVocabListFromCategory(int categoryId) throws CategoryNotFoundException, VocabListNotFoundException;

    /**
     * Gets a random vocab from a vocab list
     * @param vocabListId ID of the vocab list
     * @return The vocab.
     */
    Vocab getRandomVocabFromVocabList(int vocabListId) throws VocabListNotFoundException, VocabNotFoundException;

    /**
     * Gets the translation of a vocab
     * @param vocabId ID of the vocab
     * @return Returns a Translation object
     */
    Translation getTranslationFromVocabId(int vocabId) throws VocabNotFoundException, TranslationNotFoundException;
}
