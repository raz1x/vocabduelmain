package de.htwberlin.vocab.export;

import java.util.List;

public interface VocabDAO {
    /**
     * Saves a vocab in the database.
     * @param vocab The vocab to be saved.
     * @return The saved vocab.
     */
    public Vocab saveVocab(Vocab vocab);

    /**
     * Updates a vocab from the database.
     * @param vocab The vocab to be updated.
     * @return The updated vocab.
     */
    public Vocab updateVocab(Vocab vocab);

    /**
     * Deletes a vocab from the database.
     * @param vocab The vocab to be deleted.
     */
    public void deleteVocab(Vocab vocab);

    /**
     * Returns a vocab from the database.
     * @param vocabId The id of the vocab.
     * @return The vocab.
     */
    public Vocab getVocab(int vocabId) throws VocabNotFoundException;

    /**
     * Gets a list of vocabularies from a vocab list
     * @param vocabListId ID of the vocab list
     * @param numberOfVocabs Number of vocabs to be used for the questions
     * @return A list of vocabs.
     */
    public List<Vocab> getVocabsFromListId(int vocabListId, int numberOfVocabs) throws VocabListNotFoundException;

    /**
     * Gets a random vocab from a vocab list
     * @param vocabListId ID of the vocab list
     * @return The vocab.
     */
    public Vocab getRandomVocabFromVocabList(int vocabListId) throws VocabListNotFoundException;

    /**
     * Saves a translation in the database.
     * @param translation The translation to be saved.
     * @return The saved translation.
     */
    public Translation saveTranslation(Translation translation);

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
    public Translation getTranslationFromVocabId(int vocabId) throws VocabNotFoundException;

    /**
     * Saves a vocab list in the database.
     * @param vocabList The vocab list to be saved.
     * @return The saved vocab list.
     */
    public VocabList saveVocabList(VocabList vocabList);

    /**
     * Updates a vocab list from the database.
     * @param vocabList The vocab list to be updated.
     * @return The updated vocab list.
     */
    public VocabList updateVocabList(VocabList vocabList);

    /**
     * Deletes a vocab list from the database.
     * @param vocabList The vocab list to be deleted.
     */
    public void deleteVocabList(VocabList vocabList);


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
    public Category saveCategory(Category category);

    /**
     * Updates a category from the database.
     * @param category The category to be updated.
     * @return The updated category.
     */
    public Category updateCategory(Category category);

    /**
     * Deletes a category from the database.
     * @param category The category to be deleted.
     */
    public void deleteCategory(Category category);

    /**
     * Returns a category from the database.
     * @param categoryId The id of the category.
     * @return The category.
     */
    public Category getCategory(int categoryId) throws CategoryNotFoundException;

}
