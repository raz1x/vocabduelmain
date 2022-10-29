package de.htwberlin.vocab.export;

import java.util.List;

public interface AccessVocab {
    /**
     * Gets a list of vocabularies from a vocab list
     * @param vocabListId ID of the vocab list
     * @param numberOfVocabs Number of vocabs to be used for the questions
     * @return Returns a list of Vocab objects
     */
    public List<Vocab> getVocabsFromListId(int vocabListId, int numberOfVocabs) throws VocabListNotFoundException;

    /**
     * Gets a list of possible Translations from a vocab
     * @param vocabId ID of the vocab
     * @param numberOfTranslations Number of translations to be used for the answers
     * @return Returns a list of Translation objects
     */
    public List<Translation> getPossibleTranslationsFromVocabId(int vocabId, int numberOfTranslations) throws VocabNotFoundException;

    /**
     * Gets a random vocab from a vocabList
     * @param vocabListId ID of the vocabList
     * @return Returns a Vocab object
     */
    public Vocab getRandomVocabFromVocabList(int vocabListId) throws VocabListNotFoundException;

    /**
     * Gets a random vocabList from a category
     * @param categoryId ID of the category
     * @return Returns a VocabList object
     */
    public VocabList getRandomVocabListFromCategory(int categoryId) throws CategoryNotFoundException;

    public Translation getTranslationFromVocabId(int vocabId) throws VocabNotFoundException;
}

