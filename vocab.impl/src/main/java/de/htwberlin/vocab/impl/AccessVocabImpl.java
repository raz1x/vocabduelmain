package de.htwberlin.vocab.impl;

import de.htwberlin.vocab.export.*;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

@Component
public class AccessVocabImpl implements AccessVocab {

    @PersistenceContext
    EntityManager entityManager;

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
        List<Translation> translations;
        try {
            Vocab vocab = entityManager.find(Vocab.class, vocabId);
            translations = entityManager.createQuery("SELECT t FROM Translation t JOIN WHERE t.vocabId != :vocabId", Translation.class)
                    .setParameter("vocabId", vocab.getVocabId())
                    .getResultList();
        } catch (Exception e) {
            throw new VocabNotFoundException("Vocab not found");
        }
        Collections.shuffle(translations);
        List<Translation> result = new ArrayList<>();
        for(int i = 0; i < numberOfTranslations; i++) {
            result.add(translations.get(i));
        }
        return result;
    }

    @Override
    public Vocab getRandomVocabFromVocabList(int vocabListId) throws VocabListNotFoundException {
        Random random = new Random();
        List<Vocab> vocabs;
        try {
            VocabList vocabList = entityManager.find(VocabList.class, vocabListId);
            vocabs = entityManager.createQuery("SELECT v FROM Vocab v WHERE v.vocabListId = :vocabListId", Vocab.class)
                    .setParameter("vocabListId", vocabListId)
                    .getResultList();
            return vocabs.get(random.nextInt(vocabs.size()));
        } catch (Exception e) {
            throw new VocabListNotFoundException("VocabList not found");
        }
    }

    @Override
    public VocabList getRandomVocabListFromCategory(int categoryId) throws CategoryNotFoundException {
        Random random = new Random();
        List<VocabList> vocabLists;
        try {
            Category category = entityManager.find(Category.class, categoryId);
            vocabLists = entityManager.createQuery("SELECT vl FROM VocabList vl WHERE vl.categoryId = :categoryId", VocabList.class)
                    .setParameter("categoryId", categoryId)
                    .getResultList();
            return vocabLists.get(random.nextInt(vocabLists.size()));
        } catch (Exception e) {
            throw new CategoryNotFoundException("Category not found");
        }
    }

    @Override
    public Translation getTranslationFromVocabId(int vocabId) throws VocabNotFoundException {
        try {
            Vocab vocab = entityManager.find(Vocab.class, vocabId);
            return entityManager.createQuery("SELECT t FROM Translation t WHERE t.vocabId = :vocabId", Translation.class)
                    .setParameter("vocabId", vocabId)
                    .getSingleResult();
        } catch (Exception e) {
            throw new VocabNotFoundException("Vocab not found");
        }
    }

}
