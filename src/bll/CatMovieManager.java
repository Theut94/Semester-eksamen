package bll;

import be.Category;
import be.Movie;
import dal.CatMovieDAO;
import dal.MovieDAO;
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

    public void createCatMovie(Movie movie, Category category)
    {
        catMovieDAO.createCatMovie(movie, category);
    }

    public void deleteCatMovie(Movie movie, Category category)
    {
        catMovieDAO.deleteOneCatMovie(movie, category);
    }
    public void deleteMovieFromCatMovie(Movie movie)
    {
        catMovieDAO.deleteMovieFromCatMovie(movie);
    }

    public void deleteCategoryFromCatMovie(Category category)
    {
        catMovieDAO.deleteCategoryFromCatMovie(category);
    }

    /**
     * Here we get a list of movies from a single category.
     * We do so by using catMovieDAO to get a list of integer(MovieIds from a category),
     * and we use movieManager to get movies from the list of Ids
     * @param category
     * @return
     */
    public ObservableList<Movie> getAllMoviesFromCatToObservable(Category category)
    {
        ObservableList<Movie> observableMoviesFromCatMovie = FXCollections.observableArrayList();
        observableMoviesFromCatMovie.addAll(movieManager.getMoviesFromId(catMovieDAO.getMoviesFromCategory(category)));
        return observableMoviesFromCatMovie;
    }

    /**
     * Here we get a list of categoryId's from a single Movie.
     * @param movie
     * @return
     */
    public List<Integer> getCategoryIdsOfMovie(Movie movie)
    {
        return catMovieDAO.getCategoryIdsOfMovie(movie);
    }

}
