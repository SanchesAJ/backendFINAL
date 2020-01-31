package ftc.shift.sample.api;


import ftc.shift.sample.models.Сategory;
import ftc.shift.sample.services.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@RestController
public class CategoriesController {
    private static final String CATEGORIES_PATH = "/api/v001/category";
    private CategoryService service;

    @Autowired
    public CategoriesController(CategoryService service) {
        this.service = service;
    }

    /**
     * Добавление новой категории
     */
    @PostMapping(CATEGORIES_PATH)
    public ResponseEntity<Сategory> createCategories(
            @RequestHeader("userId") String userId,
            @RequestBody Сategory сategory) {
        Сategory result = service.createCategories(userId, сategory);
        return ResponseEntity.ok(result);
    }

    /**
     * Получение категории с указанным идентификатором
     *
     * @param userId - Идентификатор пользователя
     * @param bookId - Идентификатор категории
     */
    @GetMapping(CATEGORIES_PATH + "/{categoriesid}")
    public ResponseEntity<Сategory> readeCategory(
            @RequestHeader("userId") String userId,
            @PathVariable String bookId) {
        Сategory сategory = service.provideCategory(userId, bookId);
        return ResponseEntity.ok(сategory);
    }

    /**
     * Обновление существующей книги
     *
     * @param userId       - Идентификатор пользователя
     * @param categoriesId - Идентификатор категории, которую необходимо обновить
     * @param сategory     - Новые данные для категори (название, ФИО  , лимит, затраты)
     */
    @PatchMapping(CATEGORIES_PATH + "/{categoriesId}")
    public ResponseEntity<Сategory> updateCategory(
            @RequestHeader("userId") String userId,
            @PathVariable Integer categoriesId,
            @RequestBody Сategory сategory) {
        Сategory updatedСategory = service.updateCategory(userId, categoriesId, сategory);
        return ResponseEntity.ok(updatedСategory);
    }

    /**
     * Удаление существующей катьегории
     *
     * @param userId       - Идентификатор пользователя
     * @param categoriesId - Идентификатор категории, которую необходимо удалить
     */
    @DeleteMapping(CATEGORIES_PATH + "/{categoriesId}")
    public ResponseEntity<?> deleteCategory(
            @RequestParam("userid") String userid,
            @PathVariable String categoriesId) {
        service.deleteCategory(userid, categoriesId);
        return ResponseEntity.ok().build();
    }

    /**
     * Получение всех категорий пользователя
     *
     * @param userid - Идентификатор пользователя
     */
    @GetMapping(CATEGORIES_PATH)
    public ResponseEntity<Collection<Сategory>> listCategories(
            @RequestParam("userid") String userid) {
        Collection<Сategory> сategories = service.provideCategory(userid);
        return ResponseEntity.ok(сategories);
    }
}