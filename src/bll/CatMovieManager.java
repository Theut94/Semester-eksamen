package bll;

import be.Category;
import be.Movie;
import dal.CatMovieDAO;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.IOException;

public class CatMovieManager
{
    private CatMovieDAO catMovieDAO = new CatMovieDAO();

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

    public ObservableList<Movie> getAllMoviesFromCatToObservable(Category category)
    {
        ObservableList<Movie> observableMoviesFromCatMovie = FXCollections.observableArrayList();
        observableMoviesFromCatMovie.addAll(catMovieDAO.getMoviesFromCategory(category));
        return observableMoviesFromCatMovie;
    }
}
