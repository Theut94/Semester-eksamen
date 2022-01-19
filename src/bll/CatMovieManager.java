package bll;

import be.Category;
import be.Movie;
import dal.CatMovieDAO;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.IOException;
import java.util.List;

public class CatMovieManager
{
    private CatMovieDAO catMovieDAO = new CatMovieDAO();
    private MovieManager movieManager = new MovieManager();

    public CatMovieManager() throws IOException {
    }

    /**
     * Connects to CatMovieDAO to create the connection between the given movie and category
     */
    public void createCatMovie(Movie movie, Category category) {
        catMovieDAO.createCatMovie(movie, category);
    }

    /**
     * Connects to CatMovieDAO to delete a specific connection between the given movie and category
     */
    public void deleteCatMovie(Movie movie, Category category) {
        catMovieDAO.deleteOneCatMovie(movie, category);
    }

    /**
     * Connects to CatMovieDAO to delete all connections to the given movie
     */
    public void deleteMovieFromCatMovie(Movie movie)
    {
        catMovieDAO.deleteMovieFromCatMovie(movie);
    }

    /**
     * Connects to CatMovieDAO to delete all connections to the given category
     */
    public void deleteCategoryFromCatMovie(Category category)
    {
        catMovieDAO.deleteCategoryFromCatMovie(category);
    }

    /**
     * Connects to CatMovieDAO to get all IDs of movies belonging to the given category,
     * then connects to MovieManager to get the IDs converted to Movie objects.
     * The movies are the added to an observable list
     * @param category - the category to find the movies of
     * @return list of movies belonging to the category
     */
    public ObservableList<Movie> getAllMoviesFromCatToObservable(Category category) {
        ObservableList<Movie> observableMoviesFromCatMovie = FXCollections.observableArrayList();
        observableMoviesFromCatMovie.addAll(movieManager.getMoviesFromId(catMovieDAO.getMoviesFromCategory(category)));
        return observableMoviesFromCatMovie;
    }

    /**
     * Connects to CatMovieDAO to get a list of category IDs on which the given movie appears
     * @param movie - the movie to find the categories of
     * @return list of category IDs
     */
    public List<Integer> getCategoryIdsOfMovie(Movie movie) {
        return catMovieDAO.getCategoryIdsOfMovie(movie);
    }
}
