package de.htwberlin.vocab.impl;

import de.htwberlin.vocab.export.*;
import org.junit.Assert;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class ManageVocabImplTest {

    private static ManageVocabImpl manageVocab;

    @BeforeAll
    public static void init() {
        manageVocab = new ManageVocabImpl();
    }
    @Test
    public void testAddVocabList() {
        VocabList vocabList = manageVocab.addVocabList("testVocabList", 1, 0, "German", "English");
        assertEquals("testVocabList", vocabList.getVocabTitle());
        assertEquals(1, vocabList.getCategoryId());
        assertEquals("German", vocabList.getLanguageA());
        assertEquals("English", vocabList.getLanguageB());
    }

    @Test
    public void testGetVocabList() throws VocabListNotFoundException {
        VocabList vocabList = manageVocab.getVocabList(1);
        assertNotEquals("testVocabList", vocabList.getVocabTitle());
        assertNotEquals(1, vocabList.getUserId());
        assertEquals("English", vocabList.getLanguageA());
        assertEquals("German", vocabList.getLanguageB());
    }

    @Test
    public void testUpdateVocabList() throws VocabListNotFoundException {
        VocabList vocabList = manageVocab.updateVocabList(1, "testVocabList", 1, 0, "German", "English");
        assertEquals("testVocabList", vocabList.getVocabTitle());
        assertEquals(1, vocabList.getCategoryId());
    }

    @Test
    public void testRemoveVocabList() throws VocabListNotFoundException {
        manageVocab.removeVocabList(1);
    }

    @Test
    public void testAddVocab() {
        Vocab vocab = manageVocab.addVocab(1, "testVocab");
        assertEquals("testVocab", vocab.getVocab());
        assertEquals(1, vocab.getVocabListId());
    }

    @Test
    public void testGetVocab() throws VocabNotFoundException {
        Vocab vocab = manageVocab.getVocab(0);
        assertNotEquals("testVocab", vocab.getVocab());
        assertEquals(0, vocab.getVocabListId());
    }

    @Test
    public void testUpdateVocab() throws VocabNotFoundException {
        Vocab vocab = manageVocab.updateVocab(1, 2, "testVocab");
        assertEquals("testVocab", vocab.getVocab());
        assertEquals(2, vocab.getVocabListId());
    }

    @Test
    public void testRemoveVocab() throws VocabNotFoundException {
        manageVocab.removeVocab(1);
    }

    @Test
    public void testAddTranslation() {
        Translation translation = manageVocab.addTranslation(1, "testTranslation");
        assertEquals("testTranslation", translation.getTranslation());
        assertEquals(1, translation.getVocabId());
    }

    @Test
    public void testGetTranslation() throws TranslationNotFoundException {
        Translation translation = manageVocab.getTranslation(0);
        assertNotEquals("testTranslation", translation.getTranslation());
        assertEquals(0, translation.getVocabId());
    }

    @Test
    public void testUpdateTranslation() throws TranslationNotFoundException {
        Translation translation = manageVocab.updateTranslation(1, 2, "testTranslation");
        assertEquals("testTranslation", translation.getTranslation());
        assertEquals(2, translation.getVocabId());
    }

    @Test
    public void testRemoveTranslation() throws TranslationNotFoundException {
        manageVocab.removeTranslation(1);
    }

    @Test
    public void testAddCategory() {
        Category category = manageVocab.addCategory("testCategory");
        assertEquals("testCategory", category.getCategoryName());
        assertEquals(0, category.getCategoryId());
    }

    @Test
    public void testGetCategory() throws CategoryNotFoundException {
        Category category = manageVocab.getCategory(0);
        assertNotEquals("testCategory", category.getCategoryName());
        assertEquals(0, category.getCategoryId());
    }

    @Test
    public void testUpdateCategory() throws CategoryNotFoundException {
        Category category = manageVocab.updateCategory(0, "testCategory");
        assertEquals("testCategory", category.getCategoryName());
        assertEquals(0, category.getCategoryId());
    }

    @Test
    public void testRemoveCategory() throws CategoryNotFoundException {
        manageVocab.removeCategory(1);
    }
}