package de.htwberlin.vocab.export;

import javax.persistence.*;

@Entity
@Table(name = "Vocab")
public class Vocab {
    /**
     * Id of the vocab.
     */
    @Id @GeneratedValue
    @Column(name = "vocabId")
    private int vocabId;

    /**
     * Foreign Key of VocabList
     */
    @ManyToOne
    @JoinColumn(name = "vocabListId")
    private VocabList vocabList;

    /**
     * The word in language A
     */
    @Column(name = "vocab")
    private String vocab;

    /**
     * Constructor for Vocab
     * @param vocabList The VocabList object.
     * @param vocab The word in language A
     */
    public Vocab(VocabList vocabList, String vocab) {
        this.vocabId = 0;
        this.vocabList = vocabList;
        this.vocab = vocab;
    }

    public int getVocabId() {
        return vocabId;
    }

    public void setVocabId(int vocabId) {
        this.vocabId = vocabId;
    }

    public VocabList getVocabList() {
        return vocabList;
    }

    public void setVocabList(VocabList vocabList) {
        this.vocabList = vocabList;
    }

    public String getVocab() {
        return vocab;
    }

    public void setVocab(String vocab) {
        this.vocab = vocab;
    }
}
