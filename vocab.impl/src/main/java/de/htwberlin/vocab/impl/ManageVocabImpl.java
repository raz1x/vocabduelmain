package de.htwberlin.vocab.impl;

import de.htwberlin.vocab.export.*;

public class ManageVocabImpl implements ManageVocab {

    @Override
    public VocabList addVocabList(String vocabTitle, int categoryId, int userId, String languageA, String languageB) {
        // save to database
        return new VocabList(userId, categoryId, vocabTitle, languageA, languageB);
    }

    @Override
    public VocabList getVocabList(int vocabListId) throws VocabListNotFoundException {
        try {
            // get VocabList from database
            return new VocabList(0, 0, "DummyList", "English", "German");
        } catch (Exception e) {
            throw new VocabListNotFoundException("VocabList not found");
        }
    }

    @Override
    public VocabList updateVocabList(int vocabListId, String vocabListName, int categoryId, int userId, String languageA, String languageB) throws VocabListNotFoundException {
        try {
            // update VocabList in database by id
            return new VocabList(userId, categoryId, vocabListName, languageA, languageB);
        } catch (Exception e) {
            throw new VocabListNotFoundException("VocabList not found");
        }
    }

    @Override
    public void removeVocabList(int vocabListId) throws VocabListNotFoundException {
        try {
            // remove VocabList from database by id
            System.out.println("VocabList removed");
        } catch (Exception e) {
            throw new VocabListNotFoundException("VocabList not found");
        }
        // remove VocabList from database by id
    }

    @Override
    public Vocab addVocab(int vocabListId, String vocab) {
        // save to database
        return new Vocab(vocabListId, vocab);
    }

    @Override
    public Vocab getVocab(int vocabId) throws VocabNotFoundException {
        try {
            // get Vocab from database
            return new Vocab(0, "DummyVocab");
        } catch (Exception e) {
            throw new VocabNotFoundException("Vocab not found");
        }
    }

    @Override
    public Vocab updateVocab(int vocabId, int vocabListId, String vocab) throws VocabNotFoundException {
        try {
            // update Vocab in database by id
            return new Vocab(vocabListId, vocab);
        } catch (Exception e) {
            throw new VocabNotFoundException("Vocab not found");
        }
    }

    @Override
    public void removeVocab(int vocabId) throws VocabNotFoundException{
        try {
            // remove Vocab from database by id
            System.out.println("Vocab removed");
        } catch (Exception e) {
            throw new VocabNotFoundException("Vocab not found");
        }
    }

    @Override
    public Translation addTranslation(int vocabId, String translation) {
        // save to database
        return new Translation(vocabId, translation);
    }

    @Override
    public Translation getTranslation(int translationId) throws TranslationNotFoundException {
        try {
            // get Translation from database
            return new Translation(0, "DummyTranslation");
        } catch (Exception e) {
            throw new TranslationNotFoundException("Translation not found");
        }
    }

    @Override
    public Translation updateTranslation(int translationId, int vocabId, String translation) throws TranslationNotFoundException {
        try {
            // update Translation in database by id
            return new Translation(vocabId, translation);
        } catch (Exception e) {
            throw new TranslationNotFoundException("Translation not found");
        }
    }

    @Override
    public void removeTranslation(int translationId) throws TranslationNotFoundException {
        try {
            // remove Translation from database by id
            System.out.println("Translation removed");
        } catch (Exception e) {
            throw new TranslationNotFoundException("Translation not found");
        }
    }

    @Override
    public Category addCategory(String categoryName) {
        // save to database
        return new Category(categoryName);
    }

    @Override
    public Category getCategory(int categoryId) throws CategoryNotFoundException {
        try {
            // get Category from database by id
            return new Category("DummyCategory");
        } catch (Exception e) {
            throw new CategoryNotFoundException("Category not found");
        }
    }

    @Override
    public Category updateCategory(int categoryId, String categoryName) throws CategoryNotFoundException {
        try {
            // update Category in database by id
            return new Category(categoryName);
        } catch (Exception e) {
            throw new CategoryNotFoundException("Category not found");
        }
    }

    @Override
    public void removeCategory(int categoryId) throws CategoryNotFoundException {
        try {
            // remove Category from database by id
            System.out.println("Category removed");
        } catch (Exception e) {
            throw new CategoryNotFoundException("Category not found");
        }
    }
}
