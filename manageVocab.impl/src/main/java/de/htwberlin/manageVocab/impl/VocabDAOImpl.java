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
    public Vocab updateVocab(Vocab vocab) throws VocabDAOException {
        try {
            return em.merge(vocab);
        } catch (PersistenceException e) {
            throw new VocabDAOException("Could not update vocab with id " + vocab.getVocabId());
        }
    }

    @Override
    public void deleteVocab(Vocab vocab) throws VocabDAOException {
        try {
            em.remove(vocab);
        } catch (PersistenceException e) {
            throw new VocabDAOException("Could not delete vocab with id " + vocab.getVocabId());
        }
    }

    @Override
    public Vocab getVocab(int vocabId) throws VocabNotFoundException {
        try {
            return em.find(Vocab.class, vocabId);
        } catch (PersistenceException e) {
            throw new VocabNotFoundException("Could not find vocab with id " + vocabId);
        }
    }

    @Override
    public Vocab getRandomVocabFromVocabList(int vocabListId) throws VocabListNotFoundException, VocabNotFoundException {
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
        } catch (PersistenceException e) {
            throw new VocabNotFoundException("Could not find vocab for vocab list with id " + vocabListId);
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
    public Translation updateTranslation(Translation translation) {
        try {
            return em.merge(translation);
        } catch (PersistenceException e) {
            throw new PersistenceException("Could not update translation");
        }
    }

    @Override
    public void deleteTranslation(Translation translation) {
        try {
            em.remove(translation);
        } catch (PersistenceException e) {
            throw new PersistenceException("Could not delete translation");
        }
    }

    @Override
    public Translation getTranslation(int translationId) throws TranslationNotFoundException {
        try {
            return em.find(Translation.class, translationId);
        } catch (PersistenceException e) {
            throw new TranslationNotFoundException("Could not find translation with id " + translationId);
        }
    }

    @Override
    public List<Translation> getPossibleTranslationsFromVocabId(int vocabId, int numberOfTranslations) throws VocabNotFoundException {
        Vocab vocab = getVocab(vocabId);
        Set<Translation> vocabTranslations = vocab.getTranslations();
        List<Translation> translations;
        try {
            translations = em.createQuery("SELECT t FROM Translation t JOIN t.vocabs v WHERE t != :translation AND v.vocabList = :vocabList", Translation.class)
                    .setParameter("translation", vocabTranslations)
                    .setParameter("vocabList", vocab.getVocabList())
                    .getResultList();
        } catch (PersistenceException e) {
            throw new VocabNotFoundException("Vocab not found with id " + vocabId);
        }
        Collections.shuffle(translations);
        List<Translation> result = new ArrayList<>();
        for (int i = 0; i < numberOfTranslations; i++) {
            result.add(translations.get(i));
        }
        return result;
    }

    @Override
    public Translation getTranslationFromVocabId(int vocabId) throws VocabNotFoundException, TranslationNotFoundException {
        try {
            Vocab vocab = em.find(Vocab.class, vocabId);
            if (vocab == null) {
                throw new VocabNotFoundException("Could not find vocab with id " + vocabId);
            }
            return vocab.getTranslations().iterator().next();
        } catch (PersistenceException e) {
            throw new TranslationNotFoundException("Translation not found for vocab with id " + vocabId);
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
    public VocabList updateVocabList(VocabList vocabList) throws VocabDAOException {
        try {
            return em.merge(vocabList);
        } catch (PersistenceException e) {
            throw new VocabDAOException("Could not update vocabList with id " + vocabList.getVocabListId());
        }
    }

    @Override
    public void deleteVocabList(VocabList vocabList) throws VocabDAOException {
        try {
            em.remove(vocabList);
        } catch (PersistenceException e) {
            throw new VocabDAOException("Could not delete vocabList with id " + vocabList.getVocabListId());
        }
    }

    @Override
    public VocabList getVocabList(int vocabListId) throws VocabListNotFoundException {
        try {
            return em.find(VocabList.class, vocabListId);
        } catch (PersistenceException e) {
            throw new VocabListNotFoundException("Could not find vocabList with id " + vocabListId);
        }
    }

    @Override
    public VocabList getRandomVocabListFromCategory(int categoryId) throws CategoryNotFoundException {
        Random random = new Random();
        try {
            Category category = em.find(Category.class, categoryId);
            if (category == null) {
                throw new CategoryNotFoundException("Category with id " + categoryId + " not found");
            }
            String query = "SELECT v FROM VocabList v WHERE v.category = :category";
            TypedQuery<VocabList> typedQuery = em.createQuery(query, VocabList.class);
            typedQuery.setParameter("category", category);
            List<VocabList> vocabLists = typedQuery.getResultList();
            return vocabLists.get(random.nextInt(vocabLists.size()));
        } catch (PersistenceException e) {
            System.out.println(e.getMessage());
            throw new CategoryNotFoundException("Category not found with id " + categoryId);
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
    public Category updateCategory(Category category) throws VocabDAOException {
        try {
            return em.merge(category);
        } catch (PersistenceException e) {
            throw new VocabDAOException("Could not update category with id " + category.getCategoryId());
        }
    }

    @Override
    public void deleteCategory(Category category) throws VocabDAOException {
        try {
            em.remove(category);
        } catch (PersistenceException e) {
            throw new VocabDAOException("Could not delete category with id" + category.getCategoryId());
        }
    }

    @Override
    public Category getCategory(int categoryId) throws CategoryNotFoundException {
        try {
            return em.find(Category.class, categoryId);
        } catch (PersistenceException e) {
            throw new CategoryNotFoundException("Could not find category with id " + categoryId);
        }
    }

    @Override
    public Category getCategoryByName(String categoryName) throws CategoryNotFoundException {
        try {
            // TODO: replace with named query
            return em.createQuery("SELECT c FROM Category c WHERE c.categoryName = :categoryName", Category.class)
                    .setParameter("categoryName", categoryName)
                    .getSingleResult();
        } catch (PersistenceException e) {
            throw new CategoryNotFoundException("Could not find category with name " + categoryName);
        }
    }

    @Override
    public List<Category> getAllCategories() throws CategoryNotFoundException {
        try {
            return em.createQuery("SELECT c FROM Category c", Category.class).getResultList();
        } catch (PersistenceException e) {
            throw new CategoryNotFoundException("Could not find any categories");
        }
    }
}
