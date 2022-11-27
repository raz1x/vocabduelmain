package de.htwberlin.vocab.export;

public interface ManageVocab {
    /**
     * Adds a new VocabList to the database
     * @param vocabListName Name of the VocabList
     * @param categoryId Foreign Key of Category
     * @param userId Foreign Key of User (if not set, set to 0)
     * @param languageA Name of the language A
     * @param languageB Name of the language B
     * @return Returns the object of the new VocabList
     */
    public VocabList addVocabList(String vocabListName, int categoryId, int userId, String languageA, String languageB) throws CategoryNotFoundException;

    /**
     * Get a VocabList by its id
     * @param vocabListId ID of the VocabList
     * @return Returns the object of the VocabList
     */
    public VocabList getVocabList(int vocabListId) throws VocabListNotFoundException;

    /**
     * Updates an existing VocabList
     * @param vocabListId ID of the VocabList
     * @param vocabTitle Name of the VocabList
     * @param categoryId Foreign Key of Category
     * @param userId Foreign Key of User
     * @param languageA Name of the language A
     * @param languageB Name of the language B
     * @return Returns the object of the new VocabList
     */
    public VocabList updateVocabList(int vocabListId, String vocabTitle, int categoryId, String languageA, String languageB) throws VocabListNotFoundException, CategoryNotFoundException;

    /**
     * Removes the VocabList from the database
     * @param vocabListId ID of the VocabList
     */
    public void removeVocabList(int vocabListId) throws VocabListNotFoundException;

    /**
     * Adds a new Vocab to the database
     * @param vocabListId Foreign Key of VocabList
     * @param vocab The word in language A
     * @return Returns the object of the new Vocab
     */
    public Vocab addVocab(int vocabListId, String vocab) throws VocabListNotFoundException;

    /**
     * Get a Vocab by its id
     * @param vocabId ID of the Vocab
     * @return Returns the object of the Vocab
     */
    public Vocab getVocab(int vocabId) throws VocabNotFoundException;
    /**
     * Updates an existing Vocab
     * @param vocabId ID of the Vocab
     * @param vocabListId Foreign Key of VocabList
     * @param vocab The word in language A
     * @return Returns the object of the new Vocab
     */
    public Vocab updateVocab(int vocabId, int vocabListId, String vocab) throws VocabNotFoundException;

    /**
     * Removes the Vocab from the database
     * @param vocabId ID of the Vocab
     */
    public void removeVocab(int vocabId) throws VocabNotFoundException;

    /**
     * Adds a new Translation to the Database
     * @param vocabId Foreign Key of Vocab
     * @param translation The word in language B
     * @return Returns the object of the new Translation
     */
    public Translation addTranslation(int vocabId, String translation) throws VocabNotFoundException;

    /**
     * Get a Translation by its id
     * @param translationId ID of the Translation
     * @return Returns the object of the Translation
     */
    public Translation getTranslation(int translationId) throws TranslationNotFoundException;
    /**
     * Updates an existing Translation
     * @param translationId ID of the Translation
     * @param vocabId Foreign Key of Vocab
     * @param translation The word in language B
     * @return Returns the object of the new Translation
     */
    public Translation updateTranslation(int translationId, int vocabId, String translation) throws TranslationNotFoundException, VocabNotFoundException;

    /**
     * Removes the Translation from the database
     * @param translationId ID of the Translation
     */
    public void removeTranslation(int translationId) throws TranslationNotFoundException;

    /**
     * Adds a new Category to the database
     * @param categoryName Name of the Category
     * @return Returns the object of the new Category
     */
    public Category addCategory(String categoryName) throws CategoryAlreadyExistsException;

    /**
     * Get a Category by its id
     * @param categoryId ID of the Category
     * @return Returns the object of the Category
     */
    public Category getCategory(int categoryId) throws CategoryNotFoundException;
    /**
     * Updates an existing Category
     * @param categoryId ID of the Category
     * @param categoryName Name of the Category
     * @return Returns the object of the new Category
     */
    public Category updateCategory(int categoryId, String categoryName) throws CategoryNotFoundException;

    /**
     * Removes the Category from the database
     * @param categoryId ID of the Category
     */
    public void removeCategory(int categoryId) throws CategoryNotFoundException;

}
