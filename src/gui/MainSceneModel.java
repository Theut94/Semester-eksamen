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
import java.time.LocalDate;
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

    public MainSceneModel() throws Exception
    {
        allMovies = FXCollections.observableArrayList();
        allMovies.addAll(movieManager.getAllMoviesToObservable());

        allCategories = FXCollections.observableArrayList();
        allCategories.addAll(categoryManager.getAllCategoriesToObservable());

        moviesFromCategories = FXCollections.observableArrayList();
    }


    // All CatMovieDAO Functions are here.
    public ObservableList<Movie> getMoviesFromCategory(Category category)
    {
        moviesFromCategories.clear();
        moviesFromCategories.addAll(catMovieManager.getAllMoviesFromCatToObservable(category));
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
    public void createMovie(String movieName, float movieIMDBRating, float moviePersonalRating, String filelink, LocalDate lastview)
    {
        Movie movie = new Movie(movieName, movieIMDBRating, moviePersonalRating,filelink,lastview);
        allMovies.add(movie);
        movieManager.createMovie(movie);
    }
    public void updateMovie(Movie movie)
    {
        movieManager.updateMovie(movie);
    }
    public void deleteMovie(Movie movie)
    {
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

    //Search function
    public void search(String keyChar) {
        List<Movie> allMovies = movieManager.getAllMoviesToObservable();
        List<Movie> result = movieManager.getSearchedMovies(allMovies, keyChar);
        moviesFromCategories.clear();
        moviesFromCategories.addAll(result);
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
        stage.showAndWait();
    }

    public void editMovie(Movie movie) throws IOException
    {
        Stage stage = createMovieScene("Edit Movie");
        movieController.setMovieValues(movie.getId(),movie.getMovieName(),movie.getMovieIMDBRating(),movie.getMoviePersonalRating(),movie.getMovieFilelink(),movie.getPicturePath(), movie.getLastview(), movie.getCategories() );
        stage.showAndWait();
    }
}
