package main.model;

import java.util.Date;

public class Expense {
    private Date date;
    private String product;
    private Integer amount;
    private String  category;
    private String person;
    private Double cost;
    private String description;
    private Boolean done;

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getProduct() {
        return product;
    }

    public void setProduct(String product) {
        this.product = product;
    }

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getPerson() {
        return person;
    }

    public void setPerson(String person) {
        this.person = person;
    }

    public Double getCost() {
        return cost;
    }

    public void setCost(Double cost) {
        this.cost = cost;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Boolean getDone() {
        return done;
    }

    public void setDone(Boolean done) {
        this.done = done;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static final class Builder {
        private Date date;
        private String product;
        private Integer amount;
        private String category;
        private String person;
        private Double cost;
        private String description;
        private Boolean done;

        public Builder date(Date date) {
            this.date = date;
            return this;
        }

        public Builder category(String category) {
            this.category = category;
            return this;
        }

        public Builder product(String product) {
            this.product = product;
            return this;
        }


        public Builder amount(Integer amount) {
            this.amount = amount;
            return this;
        }

        public Builder person(String person) {
            this.person = person;
            return this;
        }

        public Builder cost(Double cost) {
            this.cost = cost;
            return this;
        }

        public Builder description(String description) {
            this.description = description;
            return this;
        }

        public Builder done(Boolean done) {
            this.done = done;
            return this;
        }

        public Expense build() {
            Expense expense = new Expense();
            expense.date = this.date;
            expense.product = this.product;
            expense.amount = this.amount;
            expense.category = this.category;
            expense.person = this.person;
            expense.cost = this.cost;
            expense.description = this.description;
            expense.done = this.done;
            return expense;
        }

    }
}
