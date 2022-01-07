package bll;

import be.Category;
import be.Movie;
import dal.CategoryDAO;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.List;

public class CategoryManager
{
    private CategoryDAO categoryDAO;
    public CategoryManager()
    {

    }

    public Category createCategory(String categoryName)
    {
        return categoryDAO.createCategory(categoryName);
    }

    public ObservableList<Category> getAllMoviesToObservable()
    {
        ObservableList<Category> observableCategories = FXCollections.observableArrayList();
        observableCategories.addAll(categoryDAO.getAllCategories());
        return observableCategories;
    }

    public void updateCategory(Category category)
    {
        categoryDAO.updateCategory(category);
    }
    public void deleteCategory (Category category)
    {
        categoryDAO.deleteCategory(category);
    }
}
