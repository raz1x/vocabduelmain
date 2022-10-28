package de.htwberlin.vocab.export;

public interface ManageVocab {
    public void addVocabList(VocabList vocabList);

    public void removeVocabList(VocabList vocabList);

    public void addVocab(Vocab vocab);

    public void removeVocab(Vocab vocab);

    public void addTranslation(Translation translation);

    public void removeTranslation(Translation translation);

    public void addCategory(Category category);

    public void removeCategory(Category category);

    public void addVocabToVocabList(VocabList vocabList, Vocab vocab);
}
