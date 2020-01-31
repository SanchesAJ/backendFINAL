package ftc.shift.sample.repositories;

import ftc.shift.sample.models.Сategory;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.util.Collection;

/**
 * Интерфейс для получения данных по категориям
 */
public interface CategoryRepository {

  Сategory fetchCategory(String userId, String bookId);

  Сategory updateCategory(String userId, Integer categoriesId, Сategory сategory);

  void deleteCategory(String userId, String bookId);

  Сategory createCategory(String userId, Сategory сategory);

  Collection<Сategory> getAllCategories(String userId);


}
