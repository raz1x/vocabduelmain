package de.htwberlin.manageVocab.export;

import jakarta.persistence.*;

@Entity
@Table(name = "VocabList")
@NamedQuery(name = "VocabList.getVocabListByCategory", query = "SELECT v FROM VocabList v WHERE v.category = :category")
public class VocabList {
    /**
     * Id of the vocabList.
     */
    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "vocabListId")
    private int vocabListId;

    /**
     * Foreign Key of Category
     */
    @ManyToOne
    @JoinColumn(name = "categoryId")
    private Category category;

    /**
     * Title of the VocabList
     */
    @Column(name = "vocabTitle")
    private String vocabTitle;

    /**
     * Name of the language A
     */
    @Column(name = "languageA")
    private String languageA;

    /**
     * Name of the language B
     */
    @Column(name = "languageB")
    private String languageB;

    @Version
    @Column(name = "version")
    private int version;

    /**
     * Default constructor for VocabList.
     */
    public VocabList() {

    }
    /**
     * Constructor for VocabList
     * @param category Foreign Key of Category
     * @param vocabTitle Title of the VocabList
     * @param languageA Name of the language A
     * @param languageB Name of the language B
     */
    public VocabList(Category category, String vocabTitle, String languageA, String languageB) {
        this.vocabListId = 0;
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

    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }
}
