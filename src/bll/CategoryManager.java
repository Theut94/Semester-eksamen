package bll;

import be.Category;
import be.Movie;
import dal.CategoryDAO;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.IOException;
import java.util.List;

public class CategoryManager
{
    private CategoryDAO categoryDAO = new CategoryDAO();

    public CategoryManager() throws IOException {

    }

    public void createCategory(Category category)
    {
        categoryDAO.createCategory(category);
    }

    public ObservableList<Category> getAllCategoriesToObservable()
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
