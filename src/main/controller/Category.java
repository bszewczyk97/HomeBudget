package main.controller;

public class Category {
    private String  categoryName;
    public Category(String  categoryName){
        this.categoryName=categoryName;
        createViewAfterClick(categoryName);

    }

    private void createViewAfterClick(String categoryName){
        new MainViewController("list", categoryName);
    }
}
