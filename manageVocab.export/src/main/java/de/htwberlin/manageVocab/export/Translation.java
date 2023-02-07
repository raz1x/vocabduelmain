package de.htwberlin.manageVocab.export;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "Translation")
@NamedQuery(name = "Translation.getOtherTranslations", query = "SELECT t FROM Translation t JOIN t.vocabs v WHERE t NOT IN(:translation) AND v.vocabList = :vocabList")
public class Translation {
    /**
     * Id of the translation.
     */
    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "translationId")
    private int translationId;

    /**
     * The word in Language B
     */
    @Column(name = "translation")
    private String translation;

    /**
     * Foreign Key of Vocab
     */
    @ManyToMany(mappedBy = "translations")
    private Set<Vocab> vocabs = new HashSet<>();

    @Version
    @Column(name = "version")
    private int version;
    /**
     * Default constructor for Translation.
     */
    public Translation() {

    }

    /**
     * Constructor for Translation
     * @param translation The word in Language B
     */
    public Translation (String translation) {
        this.translationId = 0;
        this.translation = translation;
    }

    public int getTranslationId() {
        return translationId;
    }

    public void setTranslationId(int translationId) {
        this.translationId = translationId;
    }

    public String getTranslation() {
        return translation;
    }

    public void setTranslation(String translation) {
        this.translation = translation;
    }

    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }
}
