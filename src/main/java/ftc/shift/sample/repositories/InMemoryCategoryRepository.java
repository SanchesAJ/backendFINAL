package ftc.shift.sample.repositories;

import ftc.shift.sample.exception.NotFoundException;
import ftc.shift.sample.models.Сategory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * Реализиция, хранящая все данные в памяти приложения
 */
@Repository
@ConditionalOnProperty(name = "use.database", havingValue = "false")
public class InMemoryCategoryRepository /*implements CategoryRepository */{
    /**
     * Ключ - имя пользователя, значение - все книги, которые есть у пользователя
     */
  /*  private Map<String, Map<Integer, Сategory>> CategoryCache = new HashMap<>();

    public InMemoryCategoryRepository() {
        // Заполним репозиторий тестовыми данными
        // В тестовых данных существует всего 3 пользователя: UserA / UserB / UserC

        CategoryCache.put("UserA", new HashMap<>());
        CategoryCache.get("UserA").put("1", new Сategory(1, "Название 1", "евгений", 2000,
                Collections.singletonList("400")));
        CategoryCache.get("UserA").put("2", new Сategory(2, "Название 2", "Автор Писателевич", 2424,
                Collections.singletonList("Детектив")));

        CategoryCache.put("UserB", new HashMap<>());
        CategoryCache.get("UserB").put("3", new Сategory(3, "Название 3", " челик", 24,
                Collections.singletonList("500")));

        CategoryCache.put("UserC", new HashMap<>());
    }

    @Override
    public Сategory fetchCategory(String userId, String bookId) {
        if (!CategoryCache.containsKey(userId)) {
            // Пользователь не найден
            throw new NotFoundException();
        }

        Map<String, Сategory> userBooks = CategoryCache.get(userId);

        if (!userBooks.containsKey(bookId)) {
            // У пользователя не найдена категория
            throw new NotFoundException();
        }

        return userBooks.get(bookId);
    }

    @Override
    public Сategory updateCategory(String userId, Integer categoriesId, Сategory сategory) {
        if (!CategoryCache.containsKey(userId)) {
            // Пользователь не найден
            throw new NotFoundException();
        }

        Map<Integer, Сategory> userBooks = CategoryCache.get(userId);

        if (!userBooks.containsKey(categoriesId)) {
            // У пользователя не найдена категория
            throw new NotFoundException();
        }

        сategory.setId(categoriesId);
        userBooks.put(categoriesId, сategory);
        return сategory;
    }

    @Override
    public void deleteCategory(String userId, String bookId) {
        if (!CategoryCache.containsKey(userId)) {
            // Пользователь не найден
            throw new NotFoundException();
        }

        Map<String, Сategory> userBooks = CategoryCache.get(userId);

        if (!userBooks.containsKey(bookId)) {
            // У пользователя не найдена категоря
            throw new NotFoundException();
        }

        CategoryCache.remove(bookId);
    }

    @Override
    public Сategory createCategory(String userId, Сategory сategory) {
        if (!CategoryCache.containsKey(userId)) {
            // Пользователь не найден
            throw new NotFoundException();
        }

        Map<String, Сategory> userBooks = CategoryCache.get(userId);

        // Плохой способ генерирования случайных идентификаторов, использовать только для примеров
        сategory.setId(String.valueOf(System.currentTimeMillis()));
        userBooks.put(сategory.getId(), сategory);
        return сategory;
    }

    @Override
    public Collection<Сategory> getAllCategories(String userId) {
        if (!CategoryCache.containsKey(userId)) {
            // Пользователь не найден
            throw new NotFoundException();
        }

        return CategoryCache.get(userId).values();
    }
    */
}
