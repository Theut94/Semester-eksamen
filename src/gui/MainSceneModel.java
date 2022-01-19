package gui;

import be.Category;
import be.Movie;
import bll.CatMovieManager;
import bll.CategoryManager;
import bll.MovieManager;
import gui.controllers.CategoryController;
import gui.controllers.MovieController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MainSceneModel
{
    private CategoryManager categoryManager = new CategoryManager();
    private CatMovieManager catMovieManager = new CatMovieManager();
    private MovieManager movieManager = new MovieManager();
    private MovieController movieController;
    private CategoryController categoryController;

    private ObservableList<Movie> allMovies;
    private ObservableList<Category> allCategories;
    private ObservableList<Movie> moviesFromCategories;
    private ObservableList<Movie> searchedMovies;

    public MainSceneModel() throws Exception
    {
        allMovies = FXCollections.observableArrayList();
        allMovies.addAll(movieManager.getAllMoviesToObservable());

        allCategories = FXCollections.observableArrayList();
        allCategories.addAll(categoryManager.getAllCategoriesToObservable());

        moviesFromCategories = FXCollections.observableArrayList();

        searchedMovies = FXCollections.observableArrayList();
    }

    /**
     * Fetches a list of the movies in the provided category
     * @param category - the category to find the movies of
     * @return an observable list of the movies in the category
     */
    public ObservableList<Movie> getMoviesFromCategory(Category category) {
        moviesFromCategories = category.getListOfMovies();
        return moviesFromCategories;
    }

    /**
     * @return an observable list of all movies
     */
    public ObservableList<Movie> getAllMovies()
    {
        return allMovies;
    }

    /**
     * Adds the new movie to the AllMovies list as well as the categories it belongs to.
     * Passes the movie on to be created in the database
     * @param movie - the movie to be created
     */
    public void createMovie(Movie movie)
    {
        allMovies.add(movie);
        movieManager.createMovie(movie);
        for( Category c : movie.getMovieCategories())
        {
            catMovieManager.createCatMovie(movie,c);
            for (Category allc: allCategories)
            {
                if (allc.getName().equals(c.getName()))
                {
                    allc.getListOfMovies().add(movie);
                }
            }
        }
    }

    /**
     * Passes the updated movie be updated in the database and updates which lists it is on
     * @param movie - the movie to be updated
     */
    public void updateMovie(Movie movie) throws IOException {
        List<Movie> moviesToDeleteFromCat = new ArrayList<>();
        catMovieManager.deleteMovieFromCatMovie(movie);
        movieManager.updateMovie(movie);
        for( Category c : movie.getMovieCategories()) {
            catMovieManager.createCatMovie(movie,c);
            moviesToDeleteFromCat.clear();
                for(int i = 0 ; i< c.getListOfMovies().size() ; i++) {
                    if(c.getListOfMovies().get(i).getId() == movie.getId()) {
                        moviesToDeleteFromCat.add(c.getListOfMovies().get(i));
                    }
                }
                if(!moviesToDeleteFromCat.isEmpty())
                    c.getListOfMovies().removeAll(moviesToDeleteFromCat);

            for (Category ac : allCategories) {
                if (ac.getName().contains(c.getName())) {
                    ac.getListOfMovies().add(movie);
                }
            }
        }
    }

    /**
     * Passes the movie to be deleted in the database,
     * then fetches the AllMovies list and replaces the previous one
     * @param movie - the movie to be deleted
     */
    public void deleteMovie(Movie movie)
    {
        catMovieManager.deleteMovieFromCatMovie(movie);
        movieManager.deleteMovie(movie);
        allMovies.clear();
        allMovies.addAll(movieManager.getAllMoviesToObservable());
    }

    /**
     * Creates a new category, adds it to the list of categories and passes it to be created in the database
     * @param categoryName - the name of the category to be created
     */
    public void createCategory(String categoryName) {
        Category cat = new Category(categoryName);
        allCategories.add(cat);
        categoryManager.createCategory(cat);
    }

    /**
     * Updates the name of the category with the new one, then passes it to be updated in the database
     * @param category - the category being updated
     */
    public void updateCategory(Category category) {
        for (Category c : allCategories){
            if(c.getId() == category.getId()){
                c.setName(category.getName());
            }
        }
        categoryManager.updateCategory(category);
    }

    /**
     * Deletes the category from allCategories and clears moviesFromCategories,
     * Then passes the category to be deleted in the database
     * @param category - the category to be deleted
     */
    public void deleteCategory(Category category) {
        catMovieManager.deleteCategoryFromCatMovie(category);
        categoryManager.deleteCategory(category);
        allCategories.remove(category);
        moviesFromCategories.clear();
    }

    /**
     * Handles the search function. Updates searchedMovies to contain the list of movies from the search
     * @param keyChar - the query to search on
     * @param searchBase - the list of movies to be searched in
     */
    public void search(String keyChar, List<Movie> searchBase) {
        List<Movie> result = movieManager.getSearchedMovies(searchBase, keyChar);
        searchedMovies.clear();
        searchedMovies.addAll(result);
    }

    /**
     * Opens a new "Movie" window to create a new movie
     */
    public void newMovie() throws IOException {
        Stage stage = createMovieScene("New Movie");
        movieController.setLvAvailableCategories(getAllCategories());
        stage.show();
    }

    /**
     * Opens a new "Movie" window to edit a movie. The current information of the movie is automatically
     * filled out
     * @param movie - the movie being edited
     */
    public void editMovie(Movie movie) throws IOException
    {
        Stage stage = createMovieScene("Edit Movie");
        movieController.setMovie(movie);
        ObservableList<Category> availableCategories = FXCollections.observableArrayList();
        availableCategories.addAll(allCategories);
        List<Category> deleteTheseCategories = new ArrayList<>();
        for(Category mc : movie.getMovieCategories())
        {
            for (Category c : availableCategories )
            {
                if(mc.getId() ==c.getId())
                    deleteTheseCategories.add(c);
            }
        }
        availableCategories.removeAll(deleteTheseCategories);
        movieController.setLvAvailableCategories(availableCategories);
        stage.showAndWait();
        allCategories.clear();
        allCategories.addAll(categoryManager.getAllCategoriesToObservable());

    }

    /**
     * Opens a new "Category" window to create a new category
     */
    public void newCategory() throws IOException {
        Stage stage = createCategoryScene();
        stage.show();
    }

    /**
     * Opens a new "Category" window to edit a category. The current name of the category is
     * automatically filled out
     * @param category - the category being edited
     */
    public void editCategory(Category category) throws IOException {
        Stage stage = createCategoryScene();
        categoryController.setEditCategory(category);
        stage.showAndWait();
    }

    /**
     * Prepares a "Movie" window
     * @return the stage of the new window
     */
    public Stage createMovieScene(String windowTitle) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource("views/Movie.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.setTitle(windowTitle);
        stage.initModality(Modality.WINDOW_MODAL);
        movieController = fxmlLoader.getController();
        movieController.setMainSceneModel(this);
        return stage;
    }

    /**
     * Prepares a "Category" window
     * @return the stage of the new window
     */
    public Stage createCategoryScene() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource("views/Category.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.setTitle("New/Edit Category");
        stage.initModality(Modality.WINDOW_MODAL);
        categoryController = fxmlLoader.getController();
        categoryController.setMainSceneModel(this);
        return stage;
    }

    public ObservableList<Category> getAllCategories() {
        return allCategories;
    }

    public ObservableList<Movie> getSearchedMovies() {
        return searchedMovies;
    }
}
