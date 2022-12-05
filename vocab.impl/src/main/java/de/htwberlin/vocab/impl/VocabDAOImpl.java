package de.htwberlin.vocab.impl;

import de.htwberlin.vocab.export.*;
import jakarta.persistence.*;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
public class VocabDAOImpl implements VocabDAO {

    @PersistenceContext
    private EntityManager em;

    @Override
    public Vocab saveVocab(Vocab vocab) {
        try {
            em.persist(vocab);
            return vocab;
        } catch (Exception e) {
            throw new PersistenceException("Could not save vocab");
        }
    }

    @Override
    public Vocab updateVocab(Vocab vocab) {
        try {
            return em.merge(vocab);
        } catch (Exception e) {
            throw new PersistenceException("Could not update vocab");
        }
    }

    @Override
    public void deleteVocab(Vocab vocab) {
        try {
            em.remove(vocab);
        } catch (Exception e) {
            throw new PersistenceException("Could not delete vocab");
        }
    }

    @Override
    public Vocab getVocab(int vocabId) throws VocabNotFoundException {
        try {
            return em.find(Vocab.class, vocabId);
        } catch (Exception e) {
            throw new VocabNotFoundException("Could not find vocab with id " + vocabId);
        }
    }

    @Override
    public Vocab getRandomVocabFromVocabList(int vocabListId) throws Exception {
        Random random = new Random();
        List<Vocab> vocabs;
        VocabList vocabList = em.find(VocabList.class, vocabListId);
        if (vocabList == null) {
            throw new VocabListNotFoundException("Could not find vocab list with id " + vocabListId);
        }
        try {
            vocabs = em.createQuery("SELECT v FROM Vocab v WHERE v.vocabList = :vocabList", Vocab.class)
                    .setParameter("vocabList", vocabList)
                    .getResultList();
            return vocabs.get(random.nextInt(vocabs.size()));
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }

    @Override
    public Translation saveTranslation(Translation translation) {
        try {
            em.persist(translation);
            return translation;
        } catch (Exception e) {
            throw new PersistenceException("Could not save translation");
        }
    }

    @Override
    public Translation updateTranslation(Translation translation) {
        try {
            return em.merge(translation);
        } catch (Exception e) {
            throw new PersistenceException("Could not update translation");
        }
    }

    @Override
    public void deleteTranslation(Translation translation) {
        try {
            em.remove(translation);
        } catch (Exception e) {
            throw new PersistenceException("Could not delete translation");
        }
    }

    @Override
    public Translation getTranslation(int translationId) throws TranslationNotFoundException {
        try {
            return em.find(Translation.class, translationId);
        } catch (Exception e) {
            throw new TranslationNotFoundException("Could not find translation with id " + translationId);
        }
    }

    @Override
    public List<Translation> getPossibleTranslationsFromVocabId(int vocabId, int numberOfTranslations) throws VocabNotFoundException {
        System.out.println(vocabId);
        Vocab vocab = getVocab(vocabId);
        Set<Translation> vocabTranslations = vocab.getTranslations();
        List<Translation> translations;
        try {
            translations = em.createQuery("SELECT t FROM Translation t WHERE t != :translation", Translation.class)
                .setParameter("translation", vocabTranslations)
                .getResultList();
        } catch (Exception e) {
            throw new VocabNotFoundException("Vocab not found with id " + vocabId);
        }
        Collections.shuffle(translations);
        List<Translation> result = new ArrayList<>();
        for(int i = 0; i < numberOfTranslations; i++) {
            result.add(translations.get(i));
        }
        return result;
    }

    @Override
    public Translation getTranslationFromVocabId(int vocabId) throws VocabNotFoundException {
        try {
            Vocab vocab = em.find(Vocab.class, vocabId);
            return vocab.getTranslations().iterator().next();
        } catch (Exception e) {
            throw new VocabNotFoundException("Vocab not found with id " + vocabId);
        }
    }

    @Override
    public VocabList saveVocabList(VocabList vocabList) {
        try {
            em.persist(vocabList);
            return vocabList;
        } catch (Exception e) {
            throw new PersistenceException("Could not save vocabList");
        }
    }

    @Override
    public VocabList updateVocabList(VocabList vocabList) {
        try {
            return em.merge(vocabList);
        } catch (Exception e) {
            throw new PersistenceException("Could not update vocabList");
        }
    }

    @Override
    public void deleteVocabList(VocabList vocabList) {
        try {
            em.remove(vocabList);
        } catch (Exception e) {
            throw new PersistenceException("Could not delete vocabList");
        }
    }

    @Override
    public VocabList getVocabList(int vocabListId) throws VocabListNotFoundException {
        try {
            return em.find(VocabList.class, vocabListId);
        } catch (Exception e) {
            throw new VocabListNotFoundException("Could not find vocabList with id " + vocabListId);
        }
    }

    @Override
    public VocabList getRandomVocabListFromCategory(int categoryId) throws CategoryNotFoundException {
        Random random = new Random();
        try {
            Category category = em.find(Category.class, categoryId);
            if(category == null) {
                throw new CategoryNotFoundException("Category with id " + categoryId + " not found");
            }
            String query = "SELECT v FROM VocabList v WHERE v.category = :category";
            TypedQuery<VocabList> typedQuery = em.createQuery(query, VocabList.class);
            typedQuery.setParameter("category", category);
            List<VocabList> vocabLists = typedQuery.getResultList();
            return vocabLists.get(random.nextInt(vocabLists.size()));
        } catch (Exception e) {
            System.out.println(e.getMessage());
            throw new CategoryNotFoundException("Category not found with id " + categoryId);
        }
    }

    @Override
    public Category saveCategory(Category category) {
        try {
            em.persist(category);
            return category;
        } catch (Exception e) {
            throw new PersistenceException("Could not save category");
        }
    }

    @Override
    public Category updateCategory(Category category) {
        try {
            return em.merge(category);
        } catch (Exception e) {
            throw new PersistenceException("Could not update category");
        }
    }

    @Override
    public void deleteCategory(Category category) {
        try {
            em.remove(category);
        } catch (Exception e) {
            throw new PersistenceException("Could not delete category");
        }
    }

    @Override
    public Category getCategory(int categoryId) throws CategoryNotFoundException {
        try {
            return em.find(Category.class, categoryId);
        } catch (Exception e) {
            throw new CategoryNotFoundException("Could not find category with id " + categoryId);
        }
    }

    @Override
    public Category getCategoryByName(String categoryName) throws CategoryNotFoundException {
        try {
            return em.createQuery("SELECT c FROM Category c WHERE c.categoryName = :categoryName", Category.class)
                    .setParameter("categoryName", categoryName)
                    .getSingleResult();
        } catch (Exception e) {
            throw new CategoryNotFoundException("Could not find category with name " + categoryName);
        }
    }
}
