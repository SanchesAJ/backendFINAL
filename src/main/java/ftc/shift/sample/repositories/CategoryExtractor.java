package ftc.shift.sample.repositories;

import ftc.shift.sample.models.Expenses;
import ftc.shift.sample.models.Сategory;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class CategoryExtractor implements ResultSetExtractor<List<Сategory>> {
    @Override
    public List<Сategory> extractData(ResultSet rs) throws SQLException, DataAccessException {
        Map<Integer, Сategory> categories = new HashMap<>();

        while (rs.next()) {
            Integer categoriesId = rs.getInt("CATEGORIES_ID");

            Сategory category;
            if (categories.containsKey(categoriesId)) {
                category = categories.get(categoriesId);
            } else {
                category = new Сategory();

                category.setId(categoriesId);
                category.setName(rs.getString("NAME"));
                category.setClient(rs.getString("CLIENT"));
                category.setLimit(rs.getInt("LIMIT_"));
                category.setExpenses(new ArrayList<>());

                categories.put(categoriesId, category);
            }
            Expenses expenses = new Expenses();
            expenses.setCategoryId(categoriesId);
            expenses.setId(rs.getInt("EXPENSES_ID"));
            expenses.setAmount(rs.getInt("EXPENSES"));

            category.getExpenses().add(expenses);
        }

        return new ArrayList<>(categories.values());
    }
}
