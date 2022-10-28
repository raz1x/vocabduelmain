package de.htwberlin.vocab.export;

public class Translation {
    /**
     * Id (autoincrement)
     */
    private int translationId;

    /**
     * Foreign Key of Vocab
     */
    private int vocabId;

    /**
     * The word in Language B
     */
    private String translation;

    /**
     * Constructor for Translation
     * @param vocabId Foreign Key of Vocab
     * @param translation The word in Language B
     */
    public Translation (int vocabId, String translation) {
        this.translationId = 0;
        this.vocabId = vocabId;
        this.translation = translation;
    }

    public int getTranslationId() {
        return translationId;
    }

    public void setTranslationId(int translationId) {
        this.translationId = translationId;
    }

    public int getVocabId() {
        return vocabId;
    }

    public void setVocabId(int vocabId) {
        this.vocabId = vocabId;
    }

    public String getTranslation() {
        return translation;
    }

    public void setTranslation(String translation) {
        this.translation = translation;
    }
}
