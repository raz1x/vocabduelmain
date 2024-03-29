package de.htwberlin.manageVocab.impl;

import de.htwberlin.manageVocab.export.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ManageVocabImplTest {

    @Mock
    private VocabDAO vocabDAO;

    @InjectMocks
    private ManageVocabImpl manageVocab;

    @BeforeEach
    void setUp() {
    }

    @Test
    void getAllCategories() throws CategoryNotFoundException, VocabDAOException {
        // 1. Arrange
        List<Category> expected = new ArrayList<>();
        when(vocabDAO.getAllCategories()).thenReturn(expected);
        // 2. Act
        List<Category> categories = manageVocab.getAllCategories();
        // 3. Assert
        assertEquals(expected, categories);
        verify(vocabDAO, times(1)).getAllCategories();
    }

    @Test
    void getAllCategoriesPersistenceException() throws CategoryNotFoundException, VocabDAOException {
        // 1. Arrange
        doThrow(VocabDAOException.class).when(vocabDAO).getAllCategories();
        // 2. Act
        Assertions.assertThrows(RuntimeException.class, ()-> manageVocab.getAllCategories());
        // 3. Assert
        verify(vocabDAO, times(1)).getAllCategories();
    }

    @Test
    void getCategory() throws CategoryNotFoundException, VocabDAOException {
        // 1. Arrange
        Category expected = new Category("test");
        when(vocabDAO.getCategory(1)).thenReturn(expected);
        // 2. Act
        Category category = manageVocab.getCategory(1);
        // 3. Assert
        assertEquals(expected, category);
        verify(vocabDAO, times(1)).getCategory(1);
    }

    @Test
    void getCategoryPersistenceException() throws CategoryNotFoundException, VocabDAOException {
        // 1. Arrange
        doThrow(VocabDAOException.class).when(vocabDAO).getCategory(1);
        // 2. Act
        Assertions.assertThrows(RuntimeException.class, ()-> manageVocab.getCategory(1));
        // 3. Assert
        verify(vocabDAO, times(1)).getCategory(1);
    }

    @Test
    void parseVocabList() throws VocabDAOException, CategoryNotFoundException, URISyntaxException, IOException {
        // 1. Arrange
        ClassLoader classLoader = getClass().getClassLoader();
        URL resource = classLoader.getResource("animals_farm_zoo.txt");
        File file = new File(resource.toURI());
        // 2. Act
        manageVocab.parseVocabList(file);
        // 3. Assert
        verify(vocabDAO, times(1)).getCategoryByName(any());
        verify(vocabDAO, times(1)).saveVocabList(any());
    }

    @Test
    void getPossibleTranslationsFromVocabId() throws TranslationNotFoundException, VocabNotFoundException, VocabDAOException {
        // 1. Arrange
        List<Translation> expected = new ArrayList<>();
        expected.add(new Translation("test"));
        Category category = new Category("test");
        Vocab vocab = new Vocab(new VocabList(category, "testList", "testA", "testB"), "testVocab");
        when(vocabDAO.getVocab(1)).thenReturn(vocab);
        when(vocabDAO.getOtherTranslationsForVocabId(vocab)).thenReturn(expected);
        // 2. Act
        List<Translation> result = manageVocab.getPossibleTranslationsFromVocabId(1, 1);
        // 3. Assert
        assertEquals(expected, result);
        verify(vocabDAO, times(1)).getVocab(1);
        verify(vocabDAO, times(1)).getOtherTranslationsForVocabId(vocab);
    }

    @Test
    void getPossibleTranslationsFromVocabIdPersistenceExceptionWhileGettingVocab() throws TranslationNotFoundException, VocabNotFoundException, VocabDAOException {
        // 1. Arrange
        when(vocabDAO.getVocab(1)).thenThrow(VocabDAOException.class);
        // 2. Act
        Assertions.assertThrows(RuntimeException.class, ()-> manageVocab.getPossibleTranslationsFromVocabId(1, 1));
        // 3. Assert
        verify(vocabDAO, times(1)).getVocab(1);
    }

    @Test
    void getPossibleTranslationsFromVocabIdPersistenceExceptionWhileGettingTranslations() throws TranslationNotFoundException, VocabNotFoundException, VocabDAOException {
        // 1. Arrange
        Category category = new Category("test");
        Vocab vocab = new Vocab(new VocabList(category, "testList", "testA", "testB"), "testVocab");
        when(vocabDAO.getVocab(1)).thenReturn(vocab);
        when(vocabDAO.getOtherTranslationsForVocabId(vocab)).thenThrow(VocabDAOException.class);
        // 2. Act
        Assertions.assertThrows(RuntimeException.class, ()-> manageVocab.getPossibleTranslationsFromVocabId(1, 1));
        // 3. Assert
        verify(vocabDAO, times(1)).getVocab(1);
        verify(vocabDAO, times(1)).getOtherTranslationsForVocabId(vocab);
    }


    @Test
    void getRandomVocabListFromCategory() throws VocabListNotFoundException, CategoryNotFoundException, VocabDAOException {
        // 1. Arrange
        Category category = new Category("test");
        List<VocabList> expected = new ArrayList<>();
        expected.add(new VocabList(category, "testList", "testA", "testB"));
        when(vocabDAO.getCategory(1)).thenReturn(category);
        when(vocabDAO.getVocabListsForCategory(category)).thenReturn(expected);
        // 2. Act
        VocabList result = manageVocab.getRandomVocabListFromCategory(1);
        // 3. Assert
        assertEquals(expected.get(0), result);
        verify(vocabDAO, times(1)).getCategory(1);
        verify(vocabDAO, times(1)).getVocabListsForCategory(category);
    }

    @Test
    void getRandomVocabListFromCategoryPersistenceException() throws VocabListNotFoundException, CategoryNotFoundException, VocabDAOException {
        // 1. Arrange
        Category category = new Category("test");
        when(vocabDAO.getCategory(1)).thenReturn(category);
        // 2. Act
        Assertions.assertThrows(RuntimeException.class, ()-> manageVocab.getRandomVocabListFromCategory(1));
        // 3. Assert
        verify(vocabDAO, times(1)).getCategory(1);
        verify(vocabDAO, times(1)).getVocabListsForCategory(category);
    }

    @Test
    void getRandomVocabFromVocabList() throws VocabNotFoundException, VocabListNotFoundException, VocabDAOException {
        // 1. Arrange
        VocabList vocabList = new VocabList(new Category("test"), "testList", "testA", "testB");
        List<Vocab> expected = new ArrayList<>();
        expected.add(new Vocab(vocabList, "testVocab"));
        when(vocabDAO.getVocabList(1)).thenReturn(vocabList);
        when(vocabDAO.getVocabsForVocabList(vocabList)).thenReturn(expected);
        // 2. Act
        Vocab result = manageVocab.getRandomVocabFromVocabList(1);
        // 3. Assert
        assertEquals(expected.get(0), result);
        verify(vocabDAO, times(1)).getVocabsForVocabList(vocabList);
    }

    @Test
    void getRandomVocabFromVocabListPersistenceError() throws VocabNotFoundException, VocabListNotFoundException, VocabDAOException {
        // 1. Arrange
        VocabList vocabList = new VocabList(new Category("test"), "testList", "testA", "testB");
        when(vocabDAO.getVocabList(1)).thenReturn(vocabList);
        when(vocabDAO.getVocabsForVocabList(vocabList)).thenThrow(VocabDAOException.class);
        // 2. Act
        Assertions.assertThrows(RuntimeException.class, ()-> manageVocab.getRandomVocabFromVocabList(1));
        // 3. Assert
        verify(vocabDAO, times(1)).getVocabsForVocabList(vocabList);
    }

    @Test
    void getTranslationFromVocabId() throws VocabDAOException, VocabNotFoundException, TranslationNotFoundException {
        // 1. Arrange
        Translation expected = new Translation("test");
        Set<Translation> translations = new HashSet<>();
        translations.add(expected);
        Vocab vocab = new Vocab(new VocabList(new Category("test"), "testList", "testA", "testB"), "testVocab");
        vocab.setTranslations(translations);
        when(vocabDAO.getVocab(1)).thenReturn(vocab);
        // 2. Act
        Translation result = manageVocab.getTranslationFromVocabId(1);
        // 3. Assert
        assertEquals(expected, result);
        verify(vocabDAO, times(1)).getVocab(1);
    }

    @Test
    void getTranslationFromVocabIdPersistenceException() throws VocabDAOException, VocabNotFoundException, TranslationNotFoundException {
        // 1. Arrange
        Translation expected = new Translation("test");
        Set<Translation> translations = new HashSet<>();
        translations.add(expected);
        Vocab vocab = new Vocab(new VocabList(new Category("test"), "testList", "testA", "testB"), "testVocab");
        vocab.setTranslations(translations);
        when(vocabDAO.getVocab(1)).thenThrow(VocabDAOException.class);
        // 2. Act
        Assertions.assertThrows(RuntimeException.class, ()-> manageVocab.getTranslationFromVocabId(1));
        // 3. Assert
        verify(vocabDAO, times(1)).getVocab(1);
    }
}