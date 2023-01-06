package de.htwberlin.manageVocab.impl;

import de.htwberlin.manageVocab.export.*;
import jakarta.persistence.PersistenceException;
import jakarta.persistence.TypedQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
@Transactional(value = "transactionManager", propagation = Propagation.REQUIRES_NEW, rollbackFor = Throwable.class)
public class ManageVocabImpl implements ManageVocab {

    @Autowired
    VocabDAO vocabDAO;

    @Override
    public List<Category> getAllCategories() throws CategoryNotFoundException {
        try {
            return vocabDAO.getAllCategories();
        } catch (CategoryNotFoundException e) {
            throw new CategoryNotFoundException("No categories found");
        }
    }

    public Category getCategory(int categoryId) throws CategoryNotFoundException {
        try {
            return vocabDAO.getCategory(categoryId);
        } catch (Exception e) {
            throw new CategoryNotFoundException("Category not found");
        }
    }

    @Override
    public void parseVocabList(File file) throws IOException {
        final String TITLE_REGEX = "[{]{3}(.*?)[}]{3}";
        final String WORD_REGEX = "[{]{1}(.*?)[}]{1}";

        String textFile = Files.readString(Path.of(file.getAbsolutePath()));
        String[] lines = textFile.split("\n");
        String titleLine = lines[0];
        Pattern titlePattern = Pattern.compile(TITLE_REGEX);
        Matcher titleMatcher = titlePattern.matcher(titleLine);
        List<String> titleMatches = new ArrayList<>();
        while (titleMatcher.find()) {
            titleMatches.add(titleMatcher.group(1));
        }
        String title = titleMatches.get(0);
        String languageA = titleMatches.get(1);
        String languageB = titleMatches.get(2);
        String categoryName = titleMatches.get(3);

        Category category;
        try {
            category = vocabDAO.getCategoryByName(categoryName);
        } catch (Exception e) {
            try {
                category = vocabDAO.saveCategory(new Category(categoryName));
            } catch (VocabDAOException ex) {
                throw new RuntimeException("Persistence error while saving category.");
            }

        }
        VocabList newVocabList = new VocabList(category, title, languageA, languageB);
        VocabList savedVocabList;
        try {
            savedVocabList = vocabDAO.saveVocabList(newVocabList);
        } catch (VocabDAOException e) {
            throw new RuntimeException("Persistence error while saving vocab list.");
        }
        for (int i = 1; i < lines.length; i++) {
            String line = lines[i];
            String[] words = line.split(":");
            Pattern wordPattern = Pattern.compile(WORD_REGEX);
            Matcher vocabMatcher = wordPattern.matcher(words[0]);
            Matcher translationMatcher = wordPattern.matcher(words[1]);
            List<String> vocabMatches = new ArrayList<>();
            List<String> translationMatches = new ArrayList<>();
            while (vocabMatcher.find()) {
                vocabMatches.add(vocabMatcher.group(1));
            }
            while (translationMatcher.find()) {
                translationMatches.add(translationMatcher.group(1));
            }
            Set<Translation> translations = new HashSet<>();
            for (String translation : translationMatches) {
                Translation newTranslation = new Translation(translation);
                translations.add(newTranslation);
            }
            Set<Vocab> vocabs = new HashSet<>();
            for (String vocab : vocabMatches) {
                Vocab newVocab = new Vocab(savedVocabList, vocab);
                newVocab.setTranslations(translations);
                vocabs.add(newVocab);
            }
            for (Translation translation : translations) {
                try {
                    vocabDAO.saveTranslation(translation);
                } catch (VocabDAOException e) {
                    throw new RuntimeException("Persistence error while saving translation.");
                }
            }
            for (Vocab vocab : vocabs) {
                try {
                    vocabDAO.saveVocab(vocab);
                } catch (VocabDAOException e) {
                    throw new RuntimeException("Persistence error while saving vocab.");
                }
            }
        }
    }

    @Override
    public List<Translation> getPossibleTranslationsFromVocabId(int vocabId, int numberOfTranslations) throws VocabNotFoundException, TranslationNotFoundException {
        Vocab vocab;
        try {
            vocab = vocabDAO.getVocab(vocabId);
        } catch (VocabDAOException e) {
            throw new RuntimeException("Persistence error while getting vocab.");
        }
        Set<Translation> vocabTranslations = vocab.getTranslations();
        List<Translation> translations;
        try {
           translations = vocabDAO.getOtherTranslationsForVocabId(vocab);
        } catch (TranslationNotFoundException e) {
            throw new TranslationNotFoundException("No translations found");
        } catch (VocabNotFoundException e) {
            throw new VocabNotFoundException("Vocab not found");
        } catch (VocabDAOException e) {
            throw new RuntimeException("Persistence error while getting other translations for a vocab.");
        }
        Collections.shuffle(translations);
        List<Translation> result = new ArrayList<>();
        for (int i = 0; i < numberOfTranslations; i++) {
            result.add(translations.get(i));
        }
        return result;
    }

    @Override
    public VocabList getRandomVocabListFromCategory(int categoryId) throws CategoryNotFoundException, VocabListNotFoundException {
        Random random = new Random();
        try {
            Category category = vocabDAO.getCategory(categoryId);
            List<VocabList> vocabLists = vocabDAO.getVocabListsForCategory(category);
            return vocabLists.get(random.nextInt(vocabLists.size()));
        } catch (CategoryNotFoundException e ) {
            throw new CategoryNotFoundException("Category not found with id " + categoryId);
        } catch (VocabListNotFoundException e) {
            throw new VocabListNotFoundException("No vocab lists found for category with id " + categoryId);
        }
    }

    @Override
    public Vocab getRandomVocabFromVocabList(int vocabListId) throws VocabListNotFoundException, VocabNotFoundException {
        Random random = new Random();
        List<Vocab> vocabs;
        VocabList vocabList = vocabDAO.getVocabList(vocabListId);

        try {
            vocabs = vocabDAO.getVocabsForVocabList(vocabList);
            return vocabs.get(random.nextInt(vocabs.size()));
        } catch (VocabNotFoundException e) {
            throw new VocabNotFoundException("Could not find vocab for vocab list with id " + vocabListId);
        }
    }

    @Override
    public Translation getTranslationFromVocabId(int vocabId) throws VocabNotFoundException, TranslationNotFoundException {
        try {
            Vocab vocab = vocabDAO.getVocab(vocabId);

            return vocab.getTranslations().iterator().next();
        } catch (PersistenceException e) {
            throw new TranslationNotFoundException("Translation not found for vocab with id " + vocabId);
        } catch (VocabDAOException e) {
            throw new RuntimeException(e);
        }
    }


}
