package de.htwberlin.manageVocab.rest_server;

import de.htwberlin.manageVocab.export.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/manageVocab")
public class ManageVocabRestAdapter implements ManageVocabRest {

    @Autowired
    private ManageVocab manageVocab;

    @Override
    public List<Category> getAllCategories() throws CategoryNotFoundException {
        return manageVocab.getAllCategories();
    }

    @Override
    public Category getCategory(int categoryId) throws CategoryNotFoundException {
        return manageVocab.getCategory(categoryId);
    }

    @Override
    public List<Translation> getPossibleTranslationsFromVocabId(int vocabId, int numberOfTranslations) throws VocabNotFoundException, TranslationNotFoundException {
        return manageVocab.getPossibleTranslationsFromVocabId(vocabId, numberOfTranslations);
    }

    @Override
    public VocabList getRandomVocabListFromCategory(int categoryId) throws CategoryNotFoundException, VocabListNotFoundException {
        return manageVocab.getRandomVocabListFromCategory(categoryId);
    }

    @Override
    public Vocab getRandomVocabFromVocabList(int vocabListId) throws VocabListNotFoundException, VocabNotFoundException {
        return manageVocab.getRandomVocabFromVocabList(vocabListId);
    }

    @Override
    public Translation getTranslationFromVocabId(int vocabId) throws VocabNotFoundException, TranslationNotFoundException {
        return manageVocab.getTranslationFromVocabId(vocabId);
    }
}
