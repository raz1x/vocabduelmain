package de.htwberlin.vocab.export;

public class VocabList {
    /**
     * Id (autoincrement)
     */
    private int vocabListId;

    /**
     * Foreign Key of User
     */
    private int userId;

    /**
     * Foreign Key of Category
     */
    private Category category;

    /**
     * Title of the VocabList
     */
    private String vocabTitle;

    /**
     * Name of the language A
     */
    private String languageA;

    /**
     * Name of the language B
     */
    private String languageB;

    /**
     * Constructor for VocabList
     * @param userId Foreign Key of User
     * @param categoryId Foreign Key of Category
     * @param vocabTitle Title of the VocabList
     * @param languageA Name of the language A
     * @param languageB Name of the language B
     */
    public VocabList(int userId, Category category, String vocabTitle, String languageA, String languageB) {
        this.vocabListId = 0;
        this.userId = userId;
        this.category = category;
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

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
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
