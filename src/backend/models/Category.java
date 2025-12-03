package backend.models;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Model untuk Category/Kategori
 * Mengelompokkan transaksi (Makanan, Transportasi, Hiburan, dll)
 */
public class Category implements Serializable {
    private static final long serialVersionUID = 1L;
    
    public enum CategoryType {
        INCOME, EXPENSE
    }
    
    private int categoryId;
    private int userId;
    private String categoryName;
    private CategoryType categoryType;
    private String icon; // Path ke icon
    private String color; // Hex color code
    private String description;
    private List<SubCategory> subCategories;
    
    // Constructor
    public Category() {
        this.subCategories = new ArrayList<>();
    }
    
    public Category(int userId, String categoryName, CategoryType categoryType) {
        this.userId = userId;
        this.categoryName = categoryName;
        this.categoryType = categoryType;
        this.subCategories = new ArrayList<>();
    }
    
    // Getters dan Setters
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
    
    public String getCategoryName() {
        return categoryName;
    }
    
    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }
    
    public CategoryType getCategoryType() {
        return categoryType;
    }
    
    public void setCategoryType(CategoryType categoryType) {
        this.categoryType = categoryType;
    }
    
    public String getIcon() {
        return icon;
    }
    
    public void setIcon(String icon) {
        this.icon = icon;
    }
    
    public String getColor() {
        return color;
    }
    
    public void setColor(String color) {
        this.color = color;
    }
    
    public String getDescription() {
        return description;
    }
    
    public void setDescription(String description) {
        this.description = description;
    }
    
    public List<SubCategory> getSubCategories() {
        return subCategories;
    }
    
    public void addSubCategory(SubCategory subCategory) {
        this.subCategories.add(subCategory);
    }
    
    public void removeSubCategory(SubCategory subCategory) {
        this.subCategories.remove(subCategory);
    }
    
    @Override
    public String toString() {
        return "Category{" +
                "categoryId=" + categoryId +
                ", categoryName='" + categoryName + '\'' +
                ", categoryType=" + categoryType +
                ", subCategoriesCount=" + subCategories.size() +
                '}';
    }
}
