package ftc.shift.sample.models;

public class Expenses {

    private Integer id;
    //foreign key category
    private Integer categoryId;

    private Integer amount;

    public Expenses() {
    }

    public Expenses(Integer categoryId, Integer amount) {
        this.categoryId = categoryId;
        this.amount = amount;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Integer categoryId) {
        this.categoryId = categoryId;
    }

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }
}
