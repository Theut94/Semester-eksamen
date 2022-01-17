package gui;

import be.Category;
import be.Movie;
import bll.CatMovieManager;
import bll.CategoryManager;
import bll.MovieManager;
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


    // All CatMovieDAO Functions are here.
    public ObservableList<Movie> getMoviesFromCategory(Category category)
    {
        moviesFromCategories = category.getListOfMovies();
        return moviesFromCategories;
    }

    public void createCatMovie( Movie movie,Category category)
    {
        catMovieManager.createCatMovie(movie,category);
    }

    public void deleteCatMovie(Movie movie, Category category)
    {
        catMovieManager.deleteCatMovie(movie, category);
    }

    // Get all Movies function.
    public ObservableList<Movie> getAllMovies()
    {
        return allMovies;
    }

    // All MovieDAO functions are here
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

    public void updateMovie(Movie movie)
    {
        catMovieManager.deleteMovieFromCatMovie(movie);
        movieManager.updateMovie(movie);
        for( Category c : movie.getMovieCategories())
        {
            catMovieManager.createCatMovie(movie,c);
            for (Category ac : allCategories)
            {
                if(ac.getListOfMovies().contains(movie))
                {
                    ac.getListOfMovies().remove(movie);
                }
                if (ac.getName().contains(c.getName()))
                {
                    ac.getListOfMovies().add(movie);
                }

            }

        }

    }

    public void deleteMovie(Movie movie)
    {
        catMovieManager.deleteMovieFromCatMovie(movie);
        movieManager.deleteMovie(movie);
    }

    // Category functions start here !
    public void createCategory(String categoryName)
    {
        Category cat = new Category(categoryName);
        allCategories.add(cat);
        categoryManager.createCategory(cat);
    }

    public void updateCategory(Category category)
    {
        categoryManager.updateCategory(category);
    }

    public void deleteCategory(Category category)
    {
        categoryManager.deleteCategory(category);
    }

    public ObservableList<Category> getAllCategories() {
        return allCategories;
    }

    //Search function
    public void search(String keyChar, Category category) {
        List<Movie> searchBase;
        if(category.getId() == 1)
            searchBase = allMovies;
        else
            searchBase = moviesFromCategories;
        List<Movie> result = movieManager.getSearchedMovies(searchBase, keyChar);
        searchedMovies.clear();
        searchedMovies.addAll(result);
    }


    public ObservableList<Movie> getSearchedMovies() {
        return searchedMovies;
    }

    // MovieController functions
    public Stage createMovieScene(String windowTitle) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource("Movie.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.setTitle(windowTitle);
        stage.initModality(Modality.WINDOW_MODAL);
        movieController = fxmlLoader.getController();
        movieController.setMainSceneModel(this);
        return stage;
    }

    public void newMovie() throws IOException {
        Stage stage = createMovieScene("New Movie");
        movieController.setLvAvailableCategories(getAllCategories());
        stage.show();
    }

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
        allMovies.clear();
        allMovies.addAll(movieManager.getAllMoviesToObservable());
    }

    public void playMovie() throws IOException {
        movieManager.playMovie();
    }
}
