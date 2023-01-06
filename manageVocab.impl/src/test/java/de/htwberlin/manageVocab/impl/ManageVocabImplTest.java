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
import java.util.ArrayList;
import java.util.List;

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
    void getAllCategories() throws CategoryNotFoundException {
        // 1. Arrange
        List<Category> expected = new ArrayList<>();
        when(vocabDAO.getAllCategories()).thenReturn(expected);
        // 2. Act
        List<Category> categories = manageVocab.getAllCategories();
        // 3. Assert
        assertEquals(expected, categories);
        verify(vocabDAO, times(1)).getAllCategories();
    }

    //TODO: geht nicht
    @Test
    void getAllCategoriesCategoryNotFound() throws CategoryNotFoundException {
        // 1. Arrange
        // 2. Act
        Assertions.assertThrows(CategoryNotFoundException.class, ()-> manageVocab.getAllCategories());
        // 3. Assert
    }

    @Test
    void getCategory() throws CategoryNotFoundException {
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
    void parseVocabList() throws VocabDAOException, IOException, CategoryNotFoundException {
        File file = new File("../textFiles/animals_farm_zoo.txt");
        manageVocab.parseVocabList(file);
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
    void getRandomVocabListFromCategory() {
    }

    @Test
    void getRandomVocabFromVocabList() {
    }

    @Test
    void getTranslationFromVocabId() {
    }
}