package de.htwberlin.vocab.export;

public class VocabList {
    private int vocabListId;
    private int categoryId;
    private int userId;
    private String vocabTitle;
    private String languageA;
    private String languageB;

    public VocabList(int vocabListId, int categoryId, int userId, String vocabTitle, String languageA, String languageB) {
        this.vocabListId = vocabListId;
        this.categoryId = categoryId;
        this.userId = userId;
        this.vocabTitle = vocabTitle;
        this.languageA = languageA;
        this.languageB = languageB;
    }
    public int getVocabListId() {
        return vocabListId;
    }

    public void setVocabListId(int vocabListId) {
        this.vocabListId = vocabListId;
    }

    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getVocabTitle() {
        return vocabTitle;
    }

    public void setVocabTitle(String vocabTitle) {
        this.vocabTitle = vocabTitle;
    }

    public String getLanguageA() {
        return languageA;
    }

    public void setLanguageA(String languageA) {
        this.languageA = languageA;
    }

    public String getLanguageB() {
        return languageB;
    }

    public void setLanguageB(String languageB) {
        this.languageB = languageB;
    }
}
