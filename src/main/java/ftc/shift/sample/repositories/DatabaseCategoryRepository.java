package ftc.shift.sample.repositories;

import ftc.shift.sample.models.Expenses;
import ftc.shift.sample.models.Сategory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Repository;

import javax.annotation.PostConstruct;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

/**
 * Реализиция, хранящая все данные в БД
 */
@Repository
@ConditionalOnProperty(name = "use.database", havingValue = "true")
public class DatabaseCategoryRepository implements CategoryRepository {
    private NamedParameterJdbcTemplate jdbcTemplate;
    private CategoryExtractor categoryExtractor;

    @Autowired
    public DatabaseCategoryRepository(NamedParameterJdbcTemplate jdbcTemplate,
                                      CategoryExtractor categoryExtractor) {
        this.jdbcTemplate = jdbcTemplate;
        this.categoryExtractor = categoryExtractor;
    }

    @PostConstruct
    public void initialize() {
        // Подразумевается, что H2 работает в in-memory режиме и таблицы необходимо создавать при каждом старте приложения
        // SQL запросы для создания таблиц
        String createGenerateBookIdSequenceSql = "create sequence CATEGORIES_ID_GENERATOR";

        String createBookTableSql = "create table CATEGORY (" +
                "CATEGORIES_ID  INTEGER default CATEGORIES_ID_GENERATOR.nextval," +
                "USER_ID  VARCHAR(64)," +
                "NAME     VARCHAR(64)," +
                "CLIENT   VARCHAR(64)," +
                "LIMIT_    INTEGER)";

        String createGenresTableSql = "create table EXPENSES (" +
                "EXPENSES_ID  INTEGER default CATEGORIES_ID_GENERATOR.nextval," +
                "CATEGORIES_ID  INTEGER," +
                "EXPENSES    INTEGER" +
                ")";

        jdbcTemplate.update(createGenerateBookIdSequenceSql, new MapSqlParameterSource());
        jdbcTemplate.update(createBookTableSql, new MapSqlParameterSource());
        jdbcTemplate.update(createGenresTableSql, new MapSqlParameterSource());


        List<Expenses> expenses = Arrays.asList(
                new Expenses(1, 50),
                new Expenses( 1, 99),
                new Expenses( 1, 150));

        // Заполним таблицы тестовыми данными
        createCategory("UserA", new Сategory("Название 1", "Автор Авторович", 12,
                expenses));

        createCategory("UserA", new Сategory( "Название 2", "Автор Писателевич", 48,
                Collections.singletonList(new Expenses( 2, 20))));

        createCategory("UserB", new Сategory( "Название 3", "Писатель Авторович", 24,
                Collections.singletonList(new Expenses( 3, 30))));
    }

    @Override
    public Сategory createCategory(String userId, Сategory сategory) {
        // Добавляем книгу
        String insertBookSql = "insert into CATEGORY (USER_ID, NAME, CLIENT, LIMIT_) " +
                "values (:userId, :name, :client, :limit)";

        // (!) При этом мы не указываем значения для столбца BOOK_ID.
        // Он будет сгенерирован автоматически на стороне БД
        MapSqlParameterSource categoryParams = new MapSqlParameterSource()
                .addValue("userId", userId)
                .addValue("name", сategory.getName())
                .addValue("client", сategory.getClient())
                .addValue("limit", сategory.getLimit());

        // Класс, который позволит получить сгенерированный expenses_id
        GeneratedKeyHolder generatedKeyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(insertBookSql, categoryParams, generatedKeyHolder);

        Integer categoriesId = (Integer) generatedKeyHolder.getKeys().get("CATEGORIES_ID");
        сategory.setId(categoriesId);

        insertExpanse(categoriesId, сategory);

        return сategory;
    }

    private void insertExpanse(Integer categoriesId, Сategory сategory) {
        if(сategory.getExpenses() == null){
            return;
        }
        for (Expenses expenses : сategory.getExpenses()) {
            String insertGenreSql = "insert into EXPENSES (CATEGORIES_ID, EXPENSES) values (:categoriesId, :expenses)";

            GeneratedKeyHolder generatedKeyHolder = new GeneratedKeyHolder();

            // Он будет сгенерирован автоматически на стороне БД
            MapSqlParameterSource expensesParams = new MapSqlParameterSource()
                    .addValue("categoriesId", categoriesId)
                    .addValue("expenses", expenses.getAmount());

            jdbcTemplate.update(insertGenreSql, expensesParams, generatedKeyHolder);

            Integer expensesId = (Integer) generatedKeyHolder.getKeys().get("EXPENSES_ID");
            expenses.setId(expensesId);
        }
    }

    @Override
    public Сategory updateCategory(String userId, Integer categoriesId, Сategory сategory) {
        // 1) Обновляем информацию о книге
        String updateBookSql = "update CATEGORY " +
                "set USER_ID=:userId, " +
                "NAME=:name, " +
                "CLIENT=:client, " +
                "LIMIT=:limit " +
                "where CATEGORIES_ID=:categoriesId";

        MapSqlParameterSource bookParams = new MapSqlParameterSource()
                .addValue("categoriesId", categoriesId)
                .addValue("userId", userId)
                .addValue("name", сategory.getName())
                .addValue("client", сategory.getClient())
                .addValue("limit", сategory.getLimit());

        jdbcTemplate.update(updateBookSql, bookParams);

        // 2) Удаляем старые жанры
        String deleteGenresSql = "delete from EXPENSES where CATEGORIES_ID=:categoriesId";

        MapSqlParameterSource params = new MapSqlParameterSource()
                .addValue("categoriesId", categoriesId);

        jdbcTemplate.update(deleteGenresSql, params);

        // 3) Добавляем новые расходы
        insertExpanse(categoriesId, сategory);

        return сategory;
    }

    @Override
    public Collection<Сategory> getAllCategories(String userId) {
        String sql = "select *" +
                "from CATEGORY cat " +
                "left join EXPENSES exp on cat.CATEGORIES_ID = exp.CATEGORIES_ID " +
                "where cat.USER_ID = :userId ";

        MapSqlParameterSource params = new MapSqlParameterSource()
                .addValue("userId", userId);

        return jdbcTemplate.query(sql, params, categoryExtractor);
    }











    @Override
    public Сategory fetchCategory(String userId, String categoryId) {
        String sql = "select USER_ID, CATEGORY.CATEGORIES_ID, NAME, CLIENT, LIMIT_, EXPENSES " +
                "from CATEGORY, EXPENSES " +
                "where CATEGORY.CATEGORIES_ID = EXPENSES.EXPENSES_ID and CATEGORY.CATEGORIES_ID=:categoryId and CATEGORY.USER_ID=:userId";

        MapSqlParameterSource params = new MapSqlParameterSource()
                .addValue("userId", userId)
                .addValue("categoryId", categoryId);

        List<Сategory> сategories = jdbcTemplate.query(sql, params, categoryExtractor);

        if (сategories.isEmpty()) {
            return null;
        }

        return сategories.get(0);
    }

    @Override
    public void deleteCategory(String userId, String categoriesid) {
        String deleteGenresSql = "delete from EXPENSES where CATEGORIES_ID=:categoriesId";
        String deleteBookSql = "delete from CATEGORY where CATEGORIES_ID=:categoriesId";

        MapSqlParameterSource params = new MapSqlParameterSource()
                .addValue("categoriesId", categoriesid);

        jdbcTemplate.update(deleteGenresSql, params);
        jdbcTemplate.update(deleteBookSql, params);
    }




}
