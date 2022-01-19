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
    private CategoryDAO categoryDAO;

    public CategoryManager() throws IOException {
        categoryDAO = new CategoryDAO();
    }

    public void createCategory(Category category) {
        categoryDAO.createCategory(category);
    }

    /**
     * Here we get a list of all Categories
     * @return
     * @throws IOException
     */
    public ObservableList<Category> getAllCategoriesToObservable() throws IOException {
        ObservableList<Category> observableCategories = FXCollections.observableArrayList();
        observableCategories.addAll(categoryDAO.getAllCategories());

        //Here we allow the category to know what movie there is connected to it.
        CatMovieManager catMovieManager = new CatMovieManager();
        for(Category c : observableCategories)
        {
            c.setListOfMovies(catMovieManager.getAllMoviesFromCatToObservable(c));
        }
        return observableCategories;
    }
    /**
     * Connects to CategoryDAO to update the information of a specific movie
     * @param category to be updated
     */
    public void updateCategory(Category category)
    {
        categoryDAO.updateCategory(category);
    }

    /**
     * Connects to CategoryDAO to delete a specific movie
     * @param category to be deleted
     */
    public void deleteCategory (Category category)
    {
        categoryDAO.deleteCategory(category);
    }

    /**
     * Here we get a list of categories, from a list of integers(we have the list of integers from a movie).
     * @param categoryIds - list of category IDs
     * @return a list of categories
     */
    public ArrayList<Category> getCategoriesOfMovie(List<Integer> categoryIds) {
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
