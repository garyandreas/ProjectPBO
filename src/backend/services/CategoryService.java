package backend.services;

import backend.models.*;
import java.util.*;
import java.io.*;
import java.util.stream.Collectors;

/**
 * Service untuk mengelola Category dan SubCategory
 * Menangani pengelompokan transaksi
 */
public class CategoryService {
    private List<Category> categories = new ArrayList<>();
    private List<SubCategory> subCategories = new ArrayList<>();
    private int nextCategoryId = 1;
    private int nextSubCategoryId = 1;
    
    private static final String CATEGORIES_DATA_FILE = "categories_data.dat";
    private static final String SUBCATEGORIES_DATA_FILE = "subcategories_data.dat";
    
    public CategoryService() {
        loadCategoriesFromFile();
        loadSubCategoriesFromFile();
    }
    
    /**
     * Membuat kategori baru
     */
    public Category createCategory(int userId, String categoryName, 
                                    Category.CategoryType categoryType) {
        Category newCategory = new Category(userId, categoryName, categoryType);
        newCategory.setCategoryId(nextCategoryId++);
        categories.add(newCategory);
        System.out.println("Kategori " + categoryName + " berhasil dibuat!");
        saveCategoriestoFile();
        return newCategory;
    }
    
    /**
     * Membuat sub kategori
     */
    public SubCategory createSubCategory(int categoryId, String subCategoryName) {
        Category category = getCategoryById(categoryId);
        if (category != null) {
            SubCategory newSubCategory = new SubCategory(categoryId, subCategoryName);
            newSubCategory.setSubCategoryId(nextSubCategoryId++);
            subCategories.add(newSubCategory);
            category.addSubCategory(newSubCategory);
            System.out.println("Sub Kategori " + subCategoryName + " berhasil dibuat!");
            saveSubCategoriesToFile();
            saveCategoriestoFile();
            return newSubCategory;
        }
        return null;
    }
    
    /**
     * Dapatkan kategori berdasarkan ID
     */
    public Category getCategoryById(int categoryId) {
        return categories.stream()
                .filter(c -> c.getCategoryId() == categoryId)
                .findFirst()
                .orElse(null);
    }
    
    /**
     * Dapatkan sub kategori berdasarkan ID
     */
    public SubCategory getSubCategoryById(int subCategoryId) {
        return subCategories.stream()
                .filter(sc -> sc.getSubCategoryId() == subCategoryId)
                .findFirst()
                .orElse(null);
    }
    
    /**
     * Dapatkan semua kategori pengguna
     */
    public List<Category> getCategoriesByUser(int userId) {
        return categories.stream()
                .filter(c -> c.getUserId() == userId)
                .collect(Collectors.toList());
    }
    
    /**
     * Dapatkan kategori berdasarkan tipe
     */
    public List<Category> getCategoriesByType(int userId, Category.CategoryType type) {
        return getCategoriesByUser(userId).stream()
                .filter(c -> c.getCategoryType() == type)
                .collect(Collectors.toList());
    }
    
    /**
     * Update kategori
     */
    public boolean updateCategory(Category category) {
        Category existingCategory = getCategoryById(category.getCategoryId());
        if (existingCategory != null) {
            existingCategory.setCategoryName(category.getCategoryName());
            existingCategory.setColor(category.getColor());
            existingCategory.setIcon(category.getIcon());
            existingCategory.setDescription(category.getDescription());
            saveCategoriestoFile();
            return true;
        }
        return false;
    }
    
    /**
     * Delete kategori
     */
    public boolean deleteCategory(int categoryId) {
        Category category = getCategoryById(categoryId);
        if (category != null) {
            // Hapus sub kategori terkait
            subCategories.removeIf(sc -> sc.getCategoryId() == categoryId);
            boolean result = categories.remove(category);
            if (result) {
                saveCategoriestoFile();
                saveSubCategoriesToFile();
            }
            return result;
        }
        return false;
    }
    
    /**
     * Delete sub kategori
     */
    public boolean deleteSubCategory(int subCategoryId) {
        SubCategory subCategory = getSubCategoryById(subCategoryId);
        if (subCategory != null) {
            Category category = getCategoryById(subCategory.getCategoryId());
            if (category != null) {
                category.removeSubCategory(subCategory);
            }
            boolean result = subCategories.remove(subCategory);
            if (result) {
                saveSubCategoriesToFile();
                if (category != null) {
                    saveCategoriestoFile();
                }
            }
            return result;
        }
        return false;
    }
    
    /**
     * Dapatkan kategori default untuk pengguna baru
     */
    public void createDefaultCategories(int userId) {
        // Income categories
        createCategory(userId, "Gaji", Category.CategoryType.INCOME);
        createCategory(userId, "Side-Work", Category.CategoryType.INCOME);
        createCategory(userId, "Bonus", Category.CategoryType.INCOME);
        createCategory(userId, "Jualan", Category.CategoryType.INCOME);
        createCategory(userId, "Investasi", Category.CategoryType.INCOME);
        createCategory(userId, "Lainnya", Category.CategoryType.INCOME);
        
        // Expense categories
        createCategory(userId, "Konsumsi", Category.CategoryType.EXPENSE);
        createCategory(userId, "Transportasi", Category.CategoryType.EXPENSE);
        createCategory(userId, "Iuran", Category.CategoryType.EXPENSE);
        createCategory(userId, "Tak Terduga", Category.CategoryType.EXPENSE);
        createCategory(userId, "Hiburan", Category.CategoryType.EXPENSE);
        createCategory(userId, "Kesehatan", Category.CategoryType.EXPENSE);
        createCategory(userId, "Pendidikan", Category.CategoryType.EXPENSE);
        createCategory(userId, "Lainnya", Category.CategoryType.EXPENSE);
    }
    
    /**
     * Dapatkan semua kategori
     */
    public List<Category> getAllCategories() {
        return new ArrayList<>(categories);
    }
    
    /**
     * Load kategori dari file
     */
    @SuppressWarnings("unchecked")
    private void loadCategoriesFromFile() {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(CATEGORIES_DATA_FILE))) {
            categories = (List<Category>) ois.readObject();
            
            // Rebuild nextCategoryId
            if (!categories.isEmpty()) {
                nextCategoryId = categories.stream()
                        .mapToInt(Category::getCategoryId)
                        .max()
                        .orElse(0) + 1;
            }
            System.out.println("Categories loaded from file: " + categories.size() + " categories");
        } catch (FileNotFoundException e) {
            System.out.println("Categories file not found. Starting with empty list.");
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("Error loading categories: " + e.getMessage());
        }
    }
    
    /**
     * Load sub kategori dari file
     */
    @SuppressWarnings("unchecked")
    private void loadSubCategoriesFromFile() {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(SUBCATEGORIES_DATA_FILE))) {
            subCategories = (List<SubCategory>) ois.readObject();
            
            // Rebuild nextSubCategoryId
            if (!subCategories.isEmpty()) {
                nextSubCategoryId = subCategories.stream()
                        .mapToInt(SubCategory::getSubCategoryId)
                        .max()
                        .orElse(0) + 1;
            }
            System.out.println("SubCategories loaded from file: " + subCategories.size() + " subcategories");
        } catch (FileNotFoundException e) {
            System.out.println("SubCategories file not found. Starting with empty list.");
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("Error loading subcategories: " + e.getMessage());
        }
    }
    
    /**
     * Save kategori ke file
     */
    private void saveCategoriestoFile() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(CATEGORIES_DATA_FILE))) {
            oos.writeObject(categories);
            oos.flush();
            System.out.println("Categories saved to file");
        } catch (IOException e) {
            System.err.println("Error saving categories: " + e.getMessage());
        }
    }
    
    /**
     * Save sub kategori ke file
     */
    private void saveSubCategoriesToFile() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(SUBCATEGORIES_DATA_FILE))) {
            oos.writeObject(subCategories);
            oos.flush();
            System.out.println("SubCategories saved to file");
        } catch (IOException e) {
            System.err.println("Error saving subcategories: " + e.getMessage());
        }
    }
    
    /**
     * Reload categories dari file
     */
    public void reloadFromFile() {
        loadCategoriesFromFile();
        loadSubCategoriesFromFile();
    }
}
