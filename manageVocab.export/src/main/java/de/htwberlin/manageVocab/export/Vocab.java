package de.htwberlin.manageVocab.export;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


@Entity
@Table(name = "Vocab")
@NamedQuery(name = "Vocab.getVocabsForVocabList", query = "SELECT v FROM Vocab v WHERE v.vocabList = :vocabList")
public class Vocab {
    /**
     * Id of the vocab.
     */
    @Id @GeneratedValue(strategy = GenerationType.AUTO)
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

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "Vocab_Translation",
            joinColumns = @JoinColumn(name = "vocabId"),
            inverseJoinColumns = @JoinColumn(name = "translationId"))
    private Set<Translation> translations = new HashSet<Translation>();

    @Version
    @Column(name = "version")
    private int version;

    /**
     * Default constructor for Vocab.
     */
    public Vocab() {

    }
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

    public Set<Translation> getTranslations() {
        return translations;
    }

    public void setTranslations(Set<Translation> translations) {
        this.translations = translations;
    }

    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }
}
