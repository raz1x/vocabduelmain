package de.htwberlin.manageVocab.impl;

import de.htwberlin.manageVocab.export.*;

import java.util.List;

public interface VocabDAO {
    /**
     * Saves a vocab in the database.
     * @param vocab The vocab to be saved.
     * @return The saved vocab.
     */
    Vocab saveVocab(Vocab vocab) throws VocabDAOException;

    /**
     * Returns a vocab from the database.
     * @param vocabId The id of the vocab.
     * @return The vocab.
     */
    Vocab getVocab(int vocabId) throws VocabNotFoundException;

    /**
     * Saves a translation in the database.
     * @param translation The translation to be saved.
     * @return The saved translation.
     */
    Translation saveTranslation(Translation translation) throws VocabDAOException;

    /**
     * Gets a list of false Translations for a vocab
     * @param vocab The vocab
     * @return Returns a list of Translation objects
     */
    List<Translation> getOtherTranslationsForVocabId(Vocab vocab) throws VocabNotFoundException;


    /**
     * Saves a vocab list in the database.
     * @param vocabList The vocab list to be saved.
     * @return The saved vocab list.
     */
    VocabList saveVocabList(VocabList vocabList) throws VocabDAOException;

    /**
     * Returns a vocab list from the database.
     * @param vocabListId The id of the vocab list.
     * @return The vocab list.
     */
    VocabList getVocabList(int vocabListId) throws VocabListNotFoundException;

    /**
     * Saves a category in the database.
     * @param category The category to be saved.
     * @return The saved category.
     */
    Category saveCategory(Category category) throws VocabDAOException;

    /**
     * Returns a category from the database.
     * @param categoryId The id of the category.
     * @return The category.
     */
    Category getCategory(int categoryId) throws CategoryNotFoundException;

    /**
     * Returns a category from the database.
     * @param categoryName The name of the category.
     * @return The category.
     */
    Category getCategoryByName(String categoryName) throws CategoryNotFoundException;

    /**
     * Gets a list of all categories
     * @return Returns a list of all categories
     */
    List<Category> getAllCategories() throws CategoryNotFoundException;

    /**
     * Gets a list of all vocab lists for a category
     * @return Returns the list of vocab lists
     */
    List<VocabList> getVocabListsForCategory(Category category) throws VocabListNotFoundException;

    List<Vocab> getVocabsForVocabList(VocabList vocabList) throws VocabNotFoundException;
}
