package de.htwberlin.manageVocab.export;

import jakarta.persistence.*;

@Entity
@Table(name = "Category")
@NamedQuery(name = "Category.getAllCategories", query = "SELECT c FROM Category c")
@NamedQuery(name = "Category.getCategoryByName", query = "SELECT c FROM Category c WHERE c.categoryName = :categoryName")
public class Category {
    /**
     * Id of the category.
     */
    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "categoryId")
    private int categoryId;

    /**
     * Name of the category
     */
    @Column(name = "categoryName")
    private String categoryName;

    @Version
    @Column(name = "version")
    private int version;

    /**
     * Default constructor for Category.
     */
    public Category() {

    }
    /**
     * Constructor for Category
     * @param categoryName Name of the category
     */
    public Category(String categoryName) {
        this.categoryId = 0;
        this.categoryName = categoryName;
    }

    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }
}
