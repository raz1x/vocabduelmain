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
    public VocabList addVocabList(String vocabTitle, int categoryId, int userId, String languageA, String languageB) throws CategoryNotFoundException, VocabDAOException {
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
    public VocabList updateVocabList(int vocabListId, String vocabListTitle, int categoryId, String languageA, String languageB) throws VocabListNotFoundException, CategoryNotFoundException, VocabDAOException {
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
    public void removeVocabList(int vocabListId) throws VocabListNotFoundException, VocabDAOException {
        VocabList vocabList;
        try {
            vocabList = vocabDAO.getVocabList(vocabListId);
        } catch (Exception e) {
            throw new VocabListNotFoundException("VocabList " + vocabListId + " not found.");
        }
        vocabDAO.deleteVocabList(vocabList);
    }

    @Override
    public Vocab addVocab(int vocabListId, String vocab) throws VocabListNotFoundException, VocabDAOException {
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
    public Vocab updateVocab(int vocabId, int vocabListId, String vocab) throws VocabNotFoundException, VocabDAOException {
        Vocab vocabToUpdate;
        VocabList vocabList;
        try {
            vocabToUpdate = vocabDAO.getVocab(vocabId);
        } catch (Exception e) {
            throw new VocabNotFoundException("Vocab not found with id " + vocabId);
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
    public void removeVocab(int vocabId) throws VocabNotFoundException, VocabDAOException {
        Vocab vocab;
        try {
            vocab = vocabDAO.getVocab(vocabId);
        } catch (Exception e) {
            throw new VocabNotFoundException("Vocab not found with id " + vocabId);
        }
        vocabDAO.deleteVocab(vocab);
    }

    @Override
    public Translation addTranslation(int vocabId, String translation) throws VocabNotFoundException, VocabDAOException {
        Vocab vocab;
        try {
            vocab = vocabDAO.getVocab(vocabId);
        } catch (Exception e) {
            throw new VocabNotFoundException("Vocab not found with id " + vocabId);
        }
        Translation newTranslation = new Translation(translation);
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
            throw new VocabNotFoundException("Vocab not found with id " + vocabId);
        }
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
    public Category updateCategory(int categoryId, String categoryName) throws CategoryNotFoundException, VocabDAOException {
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
    public void removeCategory(int categoryId) throws CategoryNotFoundException, VocabDAOException {
        Category category;
        try {
            category = vocabDAO.getCategory(categoryId);
        } catch (Exception e) {
            throw new CategoryNotFoundException("Category not found");
        }
        vocabDAO.deleteCategory(category);
    }

    @Override
    public List<Category> getAllCategories() throws CategoryNotFoundException {
        try {
            return vocabDAO.getAllCategories();
        } catch (Exception e) {
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
    public void parseVocabList(File file) throws IOException, VocabDAOException {
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
            category = vocabDAO.saveCategory(new Category(categoryName));
        }
        VocabList newVocabList = new VocabList(category, title, languageA, languageB);
        VocabList savedVocabList = vocabDAO.saveVocabList(newVocabList);
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
                vocabDAO.saveTranslation(translation);
            }
            for (Vocab vocab : vocabs) {
                vocabDAO.saveVocab(vocab);
            }
        }
    }

    @Override
    public List<Translation> getPossibleTranslationsFromVocabId(int vocabId, int numberOfTranslations) throws VocabNotFoundException {
        Vocab vocab = vocabDAO.getVocab(vocabId);
        Set<Translation> vocabTranslations = vocab.getTranslations();
        List<Translation> translations = vocabDAO.getOtherTranslationsForVocabId(vocab);
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
}
