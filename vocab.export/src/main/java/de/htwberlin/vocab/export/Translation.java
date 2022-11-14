package de.htwberlin.vocab.export;

public class Translation {
    /**
     * Id (autoincrement)
     */
    private int translationId;

    /**
     * Foreign Key of Vocab
     */
    private Vocab vocab;

    /**
     * The word in Language B
     */
    private String translation;

    /**
     * Constructor for Translation
     * @param vocab The Vocab object.
     * @param translation The word in Language B
     */
    public Translation (Vocab vocab, String translation) {
        this.translationId = 0;
        this.vocab = vocab;
        this.translation = translation;
    }

    public int getTranslationId() {
        return translationId;
    }

    public void setTranslationId(int translationId) {
        this.translationId = translationId;
    }

    public Vocab getVocab() {
        return vocab;
    }

    public void setVocab(Vocab vocab) {
        this.vocab = vocab;
    }

    public String getTranslation() {
        return translation;
    }

    public void setTranslation(String translation) {
        this.translation = translation;
    }
}
