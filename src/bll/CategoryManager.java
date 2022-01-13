package bll;

import be.Category;
import be.Movie;
import dal.CategoryDAO;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.IOException;
import java.util.ArrayList;
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

    public ArrayList<Category> getCategoriesOfMovie(List<Integer> categoryIds)
    {
        ArrayList<Category> categoriesOfMovie = new ArrayList<>();
        for(Integer i : categoryIds)
        {
            for(Category c : categoryDAO.getAllCategories())
                if(c.getId()==i)
                    categoriesOfMovie.add(c);
        }
        return categoriesOfMovie;
    }



}
