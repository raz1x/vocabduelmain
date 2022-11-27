package de.htwberlin.vocab.impl;

import de.htwberlin.vocab.export.*;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class ManageVocabImpl implements ManageVocab {

    @PersistenceContext
    EntityManager entityManager;

    @Override
    public VocabList addVocabList(String vocabTitle, int categoryId, int userId, String languageA, String languageB) throws CategoryNotFoundException {
        Category category;
        try {
            category = entityManager.find(Category.class, categoryId);
        } catch (Exception e) {
            throw new CategoryNotFoundException("Category " + categoryId + " does not exist.");
        }
        VocabList vocabList = new VocabList(category,  vocabTitle, languageA, languageB);
        entityManager.persist(vocabList);
        return vocabList;
    }

    @Override
    public VocabList getVocabList(int vocabListId) throws VocabListNotFoundException {
        try {
            return entityManager.find(VocabList.class, vocabListId);
        } catch (Exception e) {
            throw new VocabListNotFoundException("VocabList " + vocabListId + " not found.");
        }
    }

    @Override
    public VocabList updateVocabList(int vocabListId, String vocabListTitle, int categoryId, String languageA, String languageB) throws VocabListNotFoundException, CategoryNotFoundException {
        VocabList vocabList;
        Category category;
        try {
            vocabList = entityManager.find(VocabList.class, vocabListId);
        } catch (Exception e) {
            throw new VocabListNotFoundException("VocabList " + vocabListId + " not found.");
        }
        try {
            category = entityManager.find(Category.class, categoryId);
        } catch (Exception e) {
            throw new CategoryNotFoundException("Category " + categoryId + " does not exist.");
        }
        vocabList.setVocabTitle(vocabListTitle);
        vocabList.setLanguageA(languageA);
        vocabList.setLanguageB(languageB);
        vocabList.setCategory(category);
        entityManager.persist(vocabList);
        return vocabList;

    }

    @Override
    public void removeVocabList(int vocabListId) throws VocabListNotFoundException {
        VocabList vocabList;
        try {
            vocabList = entityManager.find(VocabList.class, vocabListId);
        } catch (Exception e) {
            throw new VocabListNotFoundException("VocabList " + vocabListId + " not found.");
        }
        entityManager.remove(vocabList);
    }

    @Override
    public Vocab addVocab(int vocabListId, String vocab) throws VocabListNotFoundException {
        VocabList vocabList;
        try {
            vocabList = entityManager.find(VocabList.class, vocabListId);
        } catch (Exception e) {
            throw new VocabListNotFoundException("VocabList " + vocabListId + " not found.");
        }
        Vocab newVocab = new Vocab(vocabList, vocab);
        entityManager.persist(newVocab);
        return newVocab;
    }

    @Override
    public Vocab getVocab(int vocabId) throws VocabNotFoundException {
        try {
            return entityManager.find(Vocab.class, vocabId);
        } catch (Exception e) {
            throw new VocabNotFoundException("Vocab not found");
        }
    }

    @Override
    public Vocab updateVocab(int vocabId, int vocabListId, String vocab) throws VocabNotFoundException {
        Vocab vocabToUpdate;
        VocabList vocabList;
        try {
            vocabToUpdate = entityManager.find(Vocab.class, vocabId);
        } catch (Exception e) {
            throw new VocabNotFoundException("Vocab not found");
        }
        try {
            vocabList = entityManager.find(VocabList.class, vocabListId);
        } catch (Exception e) {
            throw new VocabNotFoundException("VocabList not found");
        }
        vocabToUpdate.setVocabList(vocabList);
        vocabToUpdate.setVocab(vocab);
        entityManager.persist(vocabToUpdate);
        return vocabToUpdate;
    }

    @Override
    public void removeVocab(int vocabId) throws VocabNotFoundException{
        Vocab vocab;
        try {
            vocab = entityManager.find(Vocab.class, vocabId);
        } catch (Exception e) {
            throw new VocabNotFoundException("Vocab not found");
        }
        entityManager.remove(vocab);
    }

    @Override
    public Translation addTranslation(int vocabId, String translation) throws VocabNotFoundException {
        Vocab vocab;
        try {
            vocab = entityManager.find(Vocab.class, vocabId);
        } catch (Exception e) {
            throw new VocabNotFoundException("Vocab not found");
        }
        Translation newTranslation = new Translation(vocab, translation);
        entityManager.persist(newTranslation);
        return newTranslation;
    }

    @Override
    public Translation getTranslation(int translationId) throws TranslationNotFoundException {
        try {
            return entityManager.find(Translation.class, translationId);
        } catch (Exception e) {
            throw new TranslationNotFoundException("Translation not found");
        }
    }

    @Override
    public Translation updateTranslation(int translationId, int vocabId, String translation) throws TranslationNotFoundException, VocabNotFoundException {
        Translation translationToUpdate;
        Vocab vocab;
        try {
            translationToUpdate = entityManager.find(Translation.class, translationId);
        } catch (Exception e) {
            throw new TranslationNotFoundException("Translation not found");
        }
        try {
            vocab = entityManager.find(Vocab.class, vocabId);
        } catch (Exception e) {
            throw new VocabNotFoundException("Vocab not found");
        }
        translationToUpdate.setVocab(vocab);
        translationToUpdate.setTranslation(translation);
        entityManager.persist(translationToUpdate);
        return translationToUpdate;
    }

    @Override
    public void removeTranslation(int translationId) throws TranslationNotFoundException {
        Translation translation;
        try {
            translation = entityManager.find(Translation.class, translationId);
        } catch (Exception e) {
            throw new TranslationNotFoundException("Translation not found");
        }
        entityManager.remove(translation);
    }

    @Override
    public Category addCategory(String categoryName) {
        Category newCategory = new Category(categoryName);
        entityManager.persist(newCategory);
        return newCategory;
    }

    @Override
    public Category getCategory(int categoryId) throws CategoryNotFoundException {
        Category category;
        try {
            category = entityManager.find(Category.class, categoryId);
        } catch (Exception e) {
            throw new CategoryNotFoundException("Category not found");
        }
        return category;
    }

    @Override
    public Category updateCategory(int categoryId, String categoryName) throws CategoryNotFoundException {
        Category category;
        try {
            category = entityManager.find(Category.class, categoryId);
        } catch (Exception e) {
            throw new CategoryNotFoundException("Category not found");
        }
        category.setCategoryName(categoryName);
        entityManager.persist(category);
        return category;
    }

    @Override
    public void removeCategory(int categoryId) throws CategoryNotFoundException {
        Category category;
        try {
            category = entityManager.find(Category.class, categoryId);
        } catch (Exception e) {
            throw new CategoryNotFoundException("Category not found");
        }
        entityManager.remove(category);
    }
}
