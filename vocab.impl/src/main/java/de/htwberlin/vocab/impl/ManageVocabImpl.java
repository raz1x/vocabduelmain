package de.htwberlin.vocab.impl;

import de.htwberlin.vocab.export.*;

public class ManageVocabImpl implements ManageVocab {

    @Override
    public VocabList addVocabList(String vocabListName, int categoryId, int userId, String languageA, String languageB) {
        // save to database
        return new VocabList(userId, categoryId, vocabListName, languageA, languageB);
    }

    @Override
    public VocabList getVocabList(int vocabListId) {
        // get VocabList from database by id
        return new VocabList(0, 0, "DummyList", "English", "German");
    }

    @Override
    public VocabList updateVocabList(int vocabListId, String vocabListName, int categoryId, int userId, String languageA, String languageB) {
        // update VocabList in database by id
        return new VocabList(userId, categoryId, vocabListName, languageA, languageB);
    }

    @Override
    public void removeVocabList(int vocabListId) {
        // remove VocabList from database by id
    }

    @Override
    public Vocab addVocab(int vocabListId, String vocab) {
        // save to database
        return new Vocab(vocabListId, vocab);
    }

    @Override
    public Vocab getVocab(int vocabId) {
        // get Vocab from database by id
        return new Vocab(0, "Dummy");
    }

    @Override
    public Vocab updateVocab(int vocabId, int vocabListId, String vocab) {
        return null;
    }

    @Override
    public void removeVocab(int vocabId) {

    }

    @Override
    public Translation addTranslation(int vocabId, String translation) {
        return null;
    }

    @Override
    public Translation getTranslation(int translationId) {
        return null;
    }

    @Override
    public Translation updateTranslation(int translationId, int vocabId, String translation) {
        return null;
    }

    @Override
    public void removeTranslation(int translationId) {

    }

    @Override
    public Category addCategory(String categoryName) {
        return null;
    }

    @Override
    public Category getCategory(int categoryId) {
        return null;
    }

    public Category updateCategory(int categoryId, String categoryName) {
        // dummy implementation
        System.out.println("Category " + categoryId + " updated");
        return null;
    }

    @Override
    public void removeCategory(int categoryId) {

    }

}
