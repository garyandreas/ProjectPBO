package backend.models;

import java.io.Serializable;

/**
 * Model untuk SubCategory/SubKategori
 * Detail kategori yang lebih spesifik (contoh: Restoran, Angkutan dibawah Makanan dan Transportasi)
 */
public class SubCategory implements Serializable {
    private static final long serialVersionUID = 1L;
    
    private int subCategoryId;
    private int categoryId;
    private String subCategoryName;
    private String icon;
    private String description;
    
    // Constructor
    public SubCategory() {
    }
    
    public SubCategory(int categoryId, String subCategoryName) {
        this.categoryId = categoryId;
        this.subCategoryName = subCategoryName;
    }
    
    // Getters dan Setters
    public int getSubCategoryId() {
        return subCategoryId;
    }
    
    public void setSubCategoryId(int subCategoryId) {
        this.subCategoryId = subCategoryId;
    }
    
    public int getCategoryId() {
        return categoryId;
    }
    
    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }
    
    public String getSubCategoryName() {
        return subCategoryName;
    }
    
    public void setSubCategoryName(String subCategoryName) {
        this.subCategoryName = subCategoryName;
    }
    
    public String getIcon() {
        return icon;
    }
    
    public void setIcon(String icon) {
        this.icon = icon;
    }
    
    public String getDescription() {
        return description;
    }
    
    public void setDescription(String description) {
        this.description = description;
    }
    
    @Override
    public String toString() {
        return "SubCategory{" +
                "subCategoryId=" + subCategoryId +
                ", subCategoryName='" + subCategoryName + '\'' +
                ", categoryId=" + categoryId +
                '}';
    }
}
