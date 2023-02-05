package de.htwberlin.manageVocab.rest_server;

import de.htwberlin.manageVocab.export.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.File;
import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/manageVocab")
public class ManageVocabRestAdapter {

    @Autowired
    private ManageVocab manageVocab;

    @GetMapping("/getAllCategories")
    public List<Category> getAllCategories() throws CategoryNotFoundException {
        return manageVocab.getAllCategories();
    }

    @GetMapping("/getCategory/{categoryId}")
    public Category getCategory(@PathVariable int categoryId) throws CategoryNotFoundException {
        return manageVocab.getCategory(categoryId);
    }

    @PostMapping(value = "/parseVocabList", consumes = "text/plain", produces = "text/plain")
    public void parseVocabList(@RequestBody String file) throws IOException, VocabDAOException {
        manageVocab.parseVocabList(new File(String.valueOf(file)));
    }

    @GetMapping("/getPossibleTranslationsFromVocabId/{vocabId}/{numberOfTranslations}")
    public List<Translation> getPossibleTranslationsFromVocabId(@PathVariable("vocabId") int vocabId, @PathVariable("numberOfTranslations") int numberOfTranslations) throws VocabNotFoundException, TranslationNotFoundException {
        return manageVocab.getPossibleTranslationsFromVocabId(vocabId, numberOfTranslations);
    }

    @GetMapping("/getRandomVocabListFromCategory/{categoryId}")
    public VocabList getRandomVocabListFromCategory(@PathVariable int categoryId) throws CategoryNotFoundException, VocabListNotFoundException {
        return manageVocab.getRandomVocabListFromCategory(categoryId);
    }

    @GetMapping("/getRandomVocabFromVocabList/{vocabListId}")
    public Vocab getRandomVocabFromVocabList(@PathVariable int vocabListId) throws VocabListNotFoundException, VocabNotFoundException {
        return manageVocab.getRandomVocabFromVocabList(vocabListId);
    }

    @GetMapping("/getTranslationFromVocabId/{vocabId}")
    public Translation getTranslationFromVocabId(@PathVariable int vocabId) throws VocabNotFoundException, TranslationNotFoundException {
        return manageVocab.getTranslationFromVocabId(vocabId);
    }
}
