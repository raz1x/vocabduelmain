package de.htwberlin.manageVocab.rest_server;

import de.htwberlin.manageVocab.export.*;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

public interface ManageVocabRest {

    @GetMapping("/getAllCategories")
    List<Category> getAllCategories() throws CategoryNotFoundException;

    @GetMapping("/getCategory/{categoryId}")
    Category getCategory(@PathVariable int categoryId) throws CategoryNotFoundException;

    @GetMapping("/getPossibleTranslationsFromVocabId/{vocabId}/{numberOfTranslations}")
    List<Translation> getPossibleTranslationsFromVocabId(@PathVariable("vocabId") int vocabId, @PathVariable("numberOfTranslations") int numberOfTranslations) throws VocabNotFoundException, TranslationNotFoundException;

    @GetMapping("/getRandomVocabListFromCategory/{categoryId}")
    VocabList getRandomVocabListFromCategory(@PathVariable int categoryId) throws CategoryNotFoundException, VocabListNotFoundException;

    @GetMapping("/getRandomVocabFromVocabList/{vocabListId}")
    Vocab getRandomVocabFromVocabList(@PathVariable int vocabListId) throws VocabListNotFoundException, VocabNotFoundException;

    @GetMapping("/getTranslationFromVocabId/{vocabId}")
    Translation getTranslationFromVocabId(@PathVariable int vocabId) throws VocabNotFoundException, TranslationNotFoundException;
}
