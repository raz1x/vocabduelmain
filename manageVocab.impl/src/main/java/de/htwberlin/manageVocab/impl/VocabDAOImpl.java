package de.htwberlin.manageVocab.impl;

import de.htwberlin.manageVocab.export.*;
import jakarta.persistence.*;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
public class VocabDAOImpl implements VocabDAO {

    @PersistenceContext
    private EntityManager em;

    @Override
    public Vocab saveVocab(Vocab vocab) throws VocabDAOException {
        try {
            em.persist(vocab);
            return vocab;
        } catch (PersistenceException e) {
            throw new VocabDAOException("Could not save vocab with id " + vocab.getVocabId());
        }
    }

    @Override
    public Vocab getVocab(int vocabId) throws VocabNotFoundException, VocabDAOException {
        try {
            Vocab vocab = em.find(Vocab.class, vocabId);
            if (vocab == null) {
                throw new VocabNotFoundException("Could not find vocab with id " + vocabId);
            }
            return vocab;
        } catch (PersistenceException e) {
            throw new VocabDAOException("Persistence error while getting vocab with id " + vocabId);
        }
    }

    @Override
    public Translation saveTranslation(Translation translation) throws VocabDAOException {
        try {
            em.persist(translation);
            return translation;
        } catch (PersistenceException e) {
            throw new VocabDAOException("Could not save translation with id " + translation.getTranslationId());
        }
    }

    @Override
    public List<Translation> getOtherTranslationsForVocabId(Vocab vocab) throws TranslationNotFoundException, VocabDAOException {
        Set<Translation> vocabTranslations = vocab.getTranslations();
        List<Translation> translations;
        try {
            translations = em.createQuery("SELECT t FROM Translation t JOIN t.vocabs v WHERE t != :translation AND v.vocabList = :vocabList", Translation.class)
                    .setParameter("translation", vocabTranslations)
                    .setParameter("vocabList", vocab.getVocabList())
                    .getResultList();
            if (translations == null) {
                throw new TranslationNotFoundException("Could not find other translations for vocab with id " + vocab.getVocabId());
            }
            return translations;
        } catch (PersistenceException e) {
            throw new VocabDAOException("Persistence error while getting other translations for vocab with id " + vocab.getVocabId());
        }
    }

    @Override
    public VocabList saveVocabList(VocabList vocabList) throws VocabDAOException {
        try {
            em.persist(vocabList);
            return vocabList;
        } catch (PersistenceException e) {
            throw new VocabDAOException("Could not save vocabList with id " + vocabList.getVocabListId());
        }
    }

    @Override
    public VocabList getVocabList(int vocabListId) throws VocabListNotFoundException {
        try {
            VocabList vocabList = em.find(VocabList.class, vocabListId);
            if (vocabList == null) {
                throw new VocabListNotFoundException("Could not find vocab list with id " + vocabListId);
            }
            return vocabList;
        } catch (PersistenceException e) {
            throw new VocabListNotFoundException("Could not find vocabList with id " + vocabListId);
        }
    }

    @Override
    public Category saveCategory(Category category) throws VocabDAOException {
        try {
            em.persist(category);
            return category;
        } catch (PersistenceException e) {
            throw new VocabDAOException("Could not save category with id " + category.getCategoryId());
        }
    }

    @Override
    public Category getCategory(int categoryId) throws CategoryNotFoundException, VocabDAOException {
        try {
            Category category = em.find(Category.class, categoryId);
            if (category == null) {
                throw new CategoryNotFoundException("Category with id " + categoryId + " not found");
            }
            return category;
        } catch (PersistenceException e) {
            throw new VocabDAOException("Persistence error while getting category with id " + categoryId);
        }
    }

    @Override
    public List<VocabList> getVocabListsForCategory(Category category) throws VocabListNotFoundException {
        String query = "SELECT v FROM VocabList v WHERE v.category = :category";
        try {
            TypedQuery<VocabList> typedQuery = em.createQuery(query, VocabList.class);
            typedQuery.setParameter("category", category);
            if(typedQuery.getResultList().isEmpty()) {
                throw new VocabListNotFoundException("No vocab lists found for category with id " + category.getCategoryId());
            }
            return typedQuery.getResultList();
        } catch (PersistenceException e) {
            throw new VocabListNotFoundException("Persistence error while getting vocab lists for category with id " + category.getCategoryId());
        }

    }

    @Override
    public List<Vocab> getVocabsForVocabList(VocabList vocabList) throws VocabDAOException, VocabNotFoundException {
        try {
            TypedQuery<Vocab> query = em.createQuery("SELECT v FROM Vocab v WHERE v.vocabList = :vocabList", Vocab.class)
                    .setParameter("vocabList", vocabList);
            if (query.getResultList().isEmpty()) {
                throw new VocabNotFoundException("No vocabs found for vocab list with id " + vocabList.getVocabListId());
            }
            return query.getResultList();
        } catch (PersistenceException e) {
            throw new VocabDAOException("Persistence error while getting vocabs for vocab list with id " + vocabList.getVocabListId());
        }
    }

    @Override
    public Category getCategoryByName(String categoryName) throws CategoryNotFoundException, VocabDAOException {
        try {
            // TODO: replace with named query
            return em.createQuery("SELECT c FROM Category c WHERE c.categoryName = :categoryName", Category.class)
                    .setParameter("categoryName", categoryName)
                    .getSingleResult();
        } catch (NoResultException e) {
            throw new CategoryNotFoundException("Category with name " + categoryName + " not found");
        } catch (PersistenceException e) {
            throw new VocabDAOException("Persistence error while getting category with name " + categoryName);
        }
    }

    @Override
    public List<Category> getAllCategories() throws CategoryNotFoundException, VocabDAOException {
        try {
            List<Category> categories = em.createQuery("SELECT c FROM Category c", Category.class).getResultList();
            if(categories.isEmpty()) {
                throw new CategoryNotFoundException("No categories found");
            }
            return categories;
        } catch (PersistenceException e) {
            throw new VocabDAOException("Persistence error while getting all categories");
        }
    }
}
