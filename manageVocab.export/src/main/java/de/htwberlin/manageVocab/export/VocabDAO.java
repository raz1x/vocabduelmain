package de.htwberlin.manageVocab.export;

import java.util.List;

public interface VocabDAO {
    /**
     * Saves a vocab in the database.
     * @param vocab The vocab to be saved.
     * @return The saved vocab.
     */
    public Vocab saveVocab(Vocab vocab) throws VocabDAOException;

    /**
     * Updates a vocab from the database.
     * @param vocab The vocab to be updated.
     * @return The updated vocab.
     */
    public Vocab updateVocab(Vocab vocab) throws VocabDAOException;

    /**
     * Deletes a vocab from the database.
     * @param vocab The vocab to be deleted.
     */
    public void deleteVocab(Vocab vocab) throws VocabDAOException;

    /**
     * Returns a vocab from the database.
     * @param vocabId The id of the vocab.
     * @return The vocab.
     */
    public Vocab getVocab(int vocabId) throws VocabNotFoundException;

    /**
     * Gets a random vocab from a vocab list
     * @param vocabListId ID of the vocab list
     * @return The vocab.
     */
    public Vocab getRandomVocabFromVocabList(int vocabListId) throws VocabListNotFoundException, VocabNotFoundException;

    /**
     * Saves a translation in the database.
     * @param translation The translation to be saved.
     * @return The saved translation.
     */
    public Translation saveTranslation(Translation translation) throws VocabDAOException;

    /**
     * Updates a translation from the database.
     * @param translation The translation to be updated.
     * @return The updated translation.
     */
    public Translation updateTranslation(Translation translation);

    /**
     * Deletes a translation from the database.
     * @param translation The translation to be deleted.
     */
    public void deleteTranslation(Translation translation);

    /**
     * Returns a translation from the database.
     * @param translationId The id of the translation.
     * @return The translation.
     */
    public Translation getTranslation(int translationId) throws TranslationNotFoundException;

    /**
     * Gets a list of possible Translations from a vocab
     * @param vocabId Id of the vocab
     * @param numberOfTranslations Number of translations to be used for the answers
     * @return Returns a list of Translation objects
     */
    public List<Translation> getPossibleTranslationsFromVocabId(int vocabId, int numberOfTranslations) throws VocabNotFoundException;

    /**
     * Gets the translation of a vocab
     * @param vocabId ID of the vocab
     * @return Returns a Translation object
     */
    public Translation getTranslationFromVocabId(int vocabId) throws VocabNotFoundException, TranslationNotFoundException;

    /**
     * Saves a vocab list in the database.
     * @param vocabList The vocab list to be saved.
     * @return The saved vocab list.
     */
    public VocabList saveVocabList(VocabList vocabList) throws VocabDAOException;

    /**
     * Updates a vocab list from the database.
     * @param vocabList The vocab list to be updated.
     * @return The updated vocab list.
     */
    public VocabList updateVocabList(VocabList vocabList) throws VocabDAOException;

    /**
     * Deletes a vocab list from the database.
     * @param vocabList The vocab list to be deleted.
     */
    public void deleteVocabList(VocabList vocabList) throws VocabDAOException;


    /**
     * Returns a vocab list from the database.
     * @param vocabListId The id of the vocab list.
     * @return The vocab list.
     */
    public VocabList getVocabList(int vocabListId) throws VocabListNotFoundException;

    /**
     * Gets a random vocabList from a category
     * @param categoryId ID of the category
     * @return Returns a VocabList object
     */
    public VocabList getRandomVocabListFromCategory(int categoryId) throws CategoryNotFoundException;

    /**
     * Saves a category in the database.
     * @param category The category to be saved.
     * @return The saved category.
     */
    public Category saveCategory(Category category) throws VocabDAOException;

    /**
     * Updates a category from the database.
     * @param category The category to be updated.
     * @return The updated category.
     */
    public Category updateCategory(Category category) throws VocabDAOException;

    /**
     * Deletes a category from the database.
     * @param category The category to be deleted.
     */
    public void deleteCategory(Category category) throws VocabDAOException;

    /**
     * Returns a category from the database.
     * @param categoryId The id of the category.
     * @return The category.
     */
    public Category getCategory(int categoryId) throws CategoryNotFoundException;

    /**
     * Returns a category from the database.
     * @param categoryName The name of the category.
     * @return The category.
     */
    public Category getCategoryByName(String categoryName) throws CategoryNotFoundException;

    /**
     * Gets a list of all categories
     * @return Returns a list of all categories
     */
    public List<Category> getAllCategories() throws CategoryNotFoundException;
}
