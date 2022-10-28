package de.htwberlin.vocab.export;

public class Vocab {
    /**
     * Id (autoincrement)
     */
    private int vocabId;

    /**
     * Foreign Key of VocabList
     */
    private int vocabListId;

    /**
     * The word in language A
     */
    private String vocab;

    /**
     * Constructor for Vocab
     * @param vocabListId Foreign Key of VocabList
     * @param vocab The word in language A
     */
    public Vocab(int vocabListId, String vocab) {
        this.vocabId = 0;
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
