package de.htwberlin.vocab.impl;

import de.htwberlin.vocab.export.*;

import java.util.ArrayList;
import java.util.List;

public class AccessVocabImpl implements AccessVocab {

    @Override
    public List<Vocab> getVocabsFromListId(int vocabListId, int numberOfVocabs) throws VocabListNotFoundException {
        List<Vocab> vocabs = new ArrayList<>();
        for(int i = 0; i < numberOfVocabs; i++) {
            vocabs.add(getRandomVocabFromVocabList(vocabListId));
        }
        return vocabs;
    }

    @Override
    public List<Translation> getPossibleTranslationsFromVocabId(int vocabId, int numberOfTranslations) throws VocabNotFoundException {
        List<Translation> translations = new ArrayList<>();
        try {
            for(int i = 0; i < numberOfTranslations; i++) {
                // get Translation for vocabId from database
                // dummy implementation
                translations.add(new Translation(vocabId, "DummyTranslation"));
            }
        } catch (Exception e) {
            throw new VocabNotFoundException("Vocab not found");
        }
        return translations;
    }

    @Override
    public Vocab getRandomVocabFromVocabList(int vocabListId) throws VocabListNotFoundException {
        try {
            // get random Vocab for vocabListId from database
            // random implementation
            return new Vocab(vocabListId, "DummyVocab");
        } catch (Exception e) {
            throw new VocabListNotFoundException("VocabList not found");
        }
    }

    @Override
    public VocabList getRandomVocabListFromCategory(int categoryId) throws CategoryNotFoundException {
        try {
            // get random VocabList for categoryId from database
            // random implementation
            return new VocabList(0, categoryId, "DummyList", "English", "German");
        } catch (Exception e) {
            throw new CategoryNotFoundException("Category not found");
        }
    }
}
