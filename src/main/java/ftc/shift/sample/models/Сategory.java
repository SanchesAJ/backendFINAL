package ftc.shift.sample.models;

import java.util.List;

public class Сategory {
    /**
     * Уникальный идентификатор категории
     */
    private Integer id;

    /**
     * Название категории
     */
    private String name;

    /**
     * имя
     */
    private String client;

    /**
     * лимт     */
    private Integer limit;

    /**
     * затраты
     */
    private List<Expenses> expenses;

    public Сategory() {
    }

    public Сategory(String name, String client, Integer limit, List<Expenses> expenses) {

        this.name = name;
        this.client = client;
        this.limit = limit;
        this.expenses = expenses;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getClient() {
        return client;
    }

    public void setClient(String client) {
        this.client = client;
    }

    public Integer getLimit() {
        return limit;
    }

    public void setLimit(Integer limit) {
        this.limit = limit;
    }

    public List<Expenses> getExpenses() {
        return expenses;
    }

    public void setExpenses(List<Expenses> expenses) {
        this.expenses = expenses;
    }

}

