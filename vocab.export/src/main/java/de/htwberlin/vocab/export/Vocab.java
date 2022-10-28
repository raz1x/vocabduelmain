package de.htwberlin.vocab.export;

public class Vocab {
    private int vocabId;
    private int vocabListId;
    private String vocab;

    public Vocab(int vocabId, int vocabListId, String vocab) {
        this.vocabId = vocabId;
        this.vocabListId = vocabListId;
        this.vocab = vocab;
    }

    public int getVocabId() {
        return vocabId;
    }

    public void setVocabId(int vocabId) {
        this.vocabId = vocabId;
    }

    public int getVocabListId() {
        return vocabListId;
    }

    public void setVocabListId(int vocabListId) {
        this.vocabListId = vocabListId;
    }

    public String getVocab() {
        return vocab;
    }

    public void setVocab(String vocab) {
        this.vocab = vocab;
    }
}
