package ftc.shift.sample.services;

import ftc.shift.sample.models.Сategory;
import ftc.shift.sample.repositories.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
public class CategoryService {
    private final CategoryRepository categoryRepository;

    @Autowired
    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    public Сategory provideCategory(String userId, String categoriesid) {
        return categoryRepository.fetchCategory(userId, categoriesid);
    }

    public Сategory updateCategory(String userId, Integer categoriesId, Сategory сategory) {
        return categoryRepository.updateCategory(userId, categoriesId, сategory);
    }

    public void deleteCategory(String userId, String categoriesid) {
        categoryRepository.deleteCategory(userId, categoriesid);
    }

    public Сategory createCategories(String userId, Сategory сategory) {
        return categoryRepository.createCategory(userId, сategory);
    }

    public Collection<Сategory> provideCategory(String userId) {
        return categoryRepository.getAllCategories(userId);
    }
}
