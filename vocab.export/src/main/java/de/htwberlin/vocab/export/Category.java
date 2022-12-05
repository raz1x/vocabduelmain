package de.htwberlin.vocab.export;

import jakarta.persistence.*;

@Entity
@Table(name = "Category")
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
}
