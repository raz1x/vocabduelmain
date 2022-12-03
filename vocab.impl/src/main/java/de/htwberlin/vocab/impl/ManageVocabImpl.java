package de.htwberlin.vocab.impl;

import de.htwberlin.vocab.export.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import jakarta.persistence.*;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Component
@Transactional(value = "transactionManager", propagation = Propagation.REQUIRES_NEW, rollbackFor = Throwable.class)
public class ManageVocabImpl implements ManageVocab {

    @Autowired
    VocabDAO vocabDAO;

    @Override
    public VocabList addVocabList(String vocabTitle, int categoryId, int userId, String languageA, String languageB) throws CategoryNotFoundException {
        Category category;
        try {
            category = vocabDAO.getCategory(categoryId);
        } catch (Exception e) {
            throw new CategoryNotFoundException("Category " + categoryId + " does not exist.");
        }
        VocabList vocabList = new VocabList(category,  vocabTitle, languageA, languageB);
        vocabDAO.saveVocabList(vocabList);
        return vocabList;
    }

    @Override
    public VocabList updateVocabList(int vocabListId, String vocabListTitle, int categoryId, String languageA, String languageB) throws VocabListNotFoundException, CategoryNotFoundException {
        VocabList vocabList;
        Category category;
        try {
            vocabList = vocabDAO.getVocabList(vocabListId);
        } catch (Exception e) {
            throw new VocabListNotFoundException("VocabList " + vocabListId + " not found.");
        }
        try {
            category = vocabDAO.getCategory(categoryId);
        } catch (Exception e) {
            throw new CategoryNotFoundException("Category " + categoryId + " does not exist.");
        }
        vocabList.setVocabTitle(vocabListTitle);
        vocabList.setLanguageA(languageA);
        vocabList.setLanguageB(languageB);
        vocabList.setCategory(category);
        vocabDAO.updateVocabList(vocabList);
        return vocabList;

    }

    @Override
    public void removeVocabList(int vocabListId) throws VocabListNotFoundException {
        VocabList vocabList;
        try {
            vocabList = vocabDAO.getVocabList(vocabListId);
        } catch (Exception e) {
            throw new VocabListNotFoundException("VocabList " + vocabListId + " not found.");
        }
        vocabDAO.deleteVocabList(vocabList);
    }

    @Override
    public Vocab addVocab(int vocabListId, String vocab) throws VocabListNotFoundException {
        VocabList vocabList;
        try {
            vocabList = vocabDAO.getVocabList(vocabListId);
        } catch (Exception e) {
            throw new VocabListNotFoundException("VocabList " + vocabListId + " not found.");
        }
        Vocab newVocab = new Vocab(vocabList, vocab);
        vocabDAO.saveVocab(newVocab);
        return newVocab;
    }

    @Override
    public Vocab updateVocab(int vocabId, int vocabListId, String vocab) throws VocabNotFoundException {
        Vocab vocabToUpdate;
        VocabList vocabList;
        try {
            vocabToUpdate = vocabDAO.getVocab(vocabId);
        } catch (Exception e) {
            throw new VocabNotFoundException("Vocab not found");
        }
        try {
            vocabList = vocabDAO.getVocabList(vocabListId);
        } catch (Exception e) {
            throw new VocabNotFoundException("VocabList not found");
        }
        vocabToUpdate.setVocabList(vocabList);
        vocabToUpdate.setVocab(vocab);
        vocabDAO.updateVocab(vocabToUpdate);
        return vocabToUpdate;
    }

    @Override
    public void removeVocab(int vocabId) throws VocabNotFoundException{
        Vocab vocab;
        try {
            vocab = vocabDAO.getVocab(vocabId);
        } catch (Exception e) {
            throw new VocabNotFoundException("Vocab not found");
        }
        vocabDAO.deleteVocab(vocab);
    }

    @Override
    public Translation addTranslation(int vocabId, String translation) throws VocabNotFoundException {
        Vocab vocab;
        try {
            vocab = vocabDAO.getVocab(vocabId);
        } catch (Exception e) {
            throw new VocabNotFoundException("Vocab not found");
        }
        Translation newTranslation = new Translation(vocab, translation);
        vocabDAO.saveTranslation(newTranslation);
        return newTranslation;
    }

    @Override
    public Translation updateTranslation(int translationId, int vocabId, String translation) throws TranslationNotFoundException, VocabNotFoundException {
        Translation translationToUpdate;
        Vocab vocab;
        try {
            translationToUpdate = vocabDAO.getTranslation(translationId);
        } catch (Exception e) {
            throw new TranslationNotFoundException("Translation not found");
        }
        try {
            vocab = vocabDAO.getVocab(vocabId);
        } catch (Exception e) {
            throw new VocabNotFoundException("Vocab not found");
        }
        translationToUpdate.setVocab(vocab);
        translationToUpdate.setTranslation(translation);
        vocabDAO.updateTranslation(translationToUpdate);
        return translationToUpdate;
    }

    @Override
    public void removeTranslation(int translationId) throws TranslationNotFoundException {
        Translation translation;
        try {
            translation = vocabDAO.getTranslation(translationId);
        } catch (Exception e) {
            throw new TranslationNotFoundException("Translation not found");
        }
        vocabDAO.deleteTranslation(translation);
    }

    @Override
    public Category addCategory(String categoryName) throws CategoryAlreadyExistsException {
        Category newCategory = new Category(categoryName);
        try {
            vocabDAO.saveCategory(newCategory);
        } catch (Exception e) {
            throw new CategoryAlreadyExistsException("Category already exists");
        }
        return newCategory;
    }

    @Override
    public Category updateCategory(int categoryId, String categoryName) throws CategoryNotFoundException {
        Category category;
        try {
            category = vocabDAO.getCategory(categoryId);
        } catch (Exception e) {
            throw new CategoryNotFoundException("Category not found");
        }
        category.setCategoryName(categoryName);
        vocabDAO.updateCategory(category);
        return category;
    }

    @Override
    public void removeCategory(int categoryId) throws CategoryNotFoundException {
        Category category;
        try {
            category = vocabDAO.getCategory(categoryId);
        } catch (Exception e) {
            throw new CategoryNotFoundException("Category not found");
        }
        vocabDAO.deleteCategory(category);
    }

    @Override
    public void parseVocabList(String path) throws VocabListAlreadyExistsException {

    }
}
