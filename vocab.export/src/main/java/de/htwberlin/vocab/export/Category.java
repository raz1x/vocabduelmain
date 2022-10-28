package de.htwberlin.vocab.export;

public class Category {
    /**
     * Id (autoincrement)
     */
    private int categoryId;

    /**
     * Name of the category
     */
    private String categoryName;

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
