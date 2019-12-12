package main.model;


public class OperationFilter {
    private String startDate;
    private String endDate;
    private String product;
    private Integer amount;
    private String  category;
    private String person;
    private Double minCost;
    private Double maxCost;
    private String description;
    private Boolean done;

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public Double getMinCost() {
        return minCost;
    }

    public void setMinCost(Double minCost) {
        this.minCost = minCost;
    }

    public Double getMaxCost() {
        return maxCost;
    }

    public void setMaxCost(Double maxCost) {
        this.maxCost = maxCost;
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


    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Boolean isDone() {
        return done;
    }

    public void setDone(Boolean done) {
        this.done = done;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static final class Builder {
        private String startDate;
        private String endDate;
        private String product;
        private Integer amount;
        private String  category;
        private String person;
        private Double minCost;
        private Double maxCost;
        private String description;
        private Boolean done;

        public Builder startDate(String startDate) {
            this.startDate = startDate;
            return this;
        }

        public Builder endDate(String endDate) {
            this.endDate = endDate;
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

        public Builder minCost(Double minCost) {
            this.minCost = minCost;
            return this;
        }

        public Builder maxCost(Double maxCost) {
            this.maxCost = maxCost;
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

        public OperationFilter build() {
            OperationFilter operation = new OperationFilter();
            operation.startDate = this.startDate;
            operation.endDate = this.endDate;
            operation.product = this.product;
            operation.amount = this.amount;
            operation.category = this.category;
            operation.person = this.person;
            operation.minCost = this.minCost;
            operation.maxCost = this.maxCost;
            operation.description = this.description;
            operation.done = this.done;
            return operation;
        }

    }
}
