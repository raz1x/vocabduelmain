package de.htwberlin.vocab.impl;

import de.htwberlin.vocab.export.*;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class AccessVocabImplTest {
    private static AccessVocabImpl accessVocab;

    @BeforeAll
    public static void init() {
        accessVocab = new AccessVocabImpl();
    }

    @Test
    public void getVocabsFromListId() throws VocabListNotFoundException {
        List<Vocab> vocabs = accessVocab.getVocabsFromListId(1, 3);
        assertEquals(3, vocabs.size());
        assertEquals("DummyVocab", vocabs.get(0).getVocab());
    }

    @Test
    public void getPossibleTranslationsFromVocabId() throws VocabNotFoundException {
        List<Translation> translations = accessVocab.getPossibleTranslationsFromVocabId(1, 4);
        assertEquals(4, translations.size());
        assertEquals("DummyTranslation", translations.get(0).getTranslation());
    }

    @Test
    public void getRandomVocabFromVocabList() throws VocabListNotFoundException {
        Vocab vocab = accessVocab.getRandomVocabFromVocabList(1);
        assertEquals("DummyVocab", vocab.getVocab());
    }

    @Test
    public void getRandomVocabListFromCategory() throws CategoryNotFoundException {
        VocabList vocabList = accessVocab.getRandomVocabListFromCategory(1);
        assertEquals("DummyList", vocabList.getVocabTitle());
    }
}