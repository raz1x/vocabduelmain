package de.htwberlin.manageVocab.export;

import jakarta.persistence.*;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "Translation")
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

    public Set<Vocab> getVocabs() {
        return vocabs;
    }

    public void setVocabs(Set<Vocab> vocabs) {
        this.vocabs = vocabs;
    }
}
