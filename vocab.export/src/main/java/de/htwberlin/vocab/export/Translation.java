package de.htwberlin.vocab.export;

public class Translation {
    private int translationId;
    private int vocabId;
    private String translation;

    public Translation(int translationId, int vocabId, String translation) {
        this.translationId = translationId;
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
