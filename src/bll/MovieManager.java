package bll;

import be.Category;
import be.Movie;
import bll.util.MoviePlayer;
import bll.util.SearchMovie;
import dal.MovieDAO;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.awt.*;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class MovieManager
{
    private MovieDAO movieDAO = new MovieDAO();
    SearchMovie filter;

    public MovieManager() throws IOException {
        filter = new SearchMovie();
    }

    public ObservableList<Movie> getAllMoviesToObservable()
    {
        ObservableList<Movie> observableMovies = FXCollections.observableArrayList();
        observableMovies.addAll(movieDAO.getAllMovies());
        return observableMovies;
    }

    public List<Movie> getSearchedMovies(List<Movie> movieList, String keyChar)
    {
        return filter.search(movieList, keyChar);
    }

    public void createMovie(Movie movie)
    {
        movieDAO.createMovie(movie);
    }

    public void updateMovie(Movie movie)
    {
        movieDAO.updateMovie(movie);
    }

    public void deleteMovie(Movie movie)
    {
        movieDAO.deleteMovie(movie);
    }

    public List<Movie> getMoviesFromId(List<Integer> movieId)
    {
        return movieDAO.getMovieFromId(movieId);
    }

    public void playMovie() throws IOException {
        MoviePlayer.main();
    }

    public ArrayList<Category> getCategoriesOfMovie(Movie movie) throws IOException {
        CategoryManager categoryManager = new CategoryManager();
        CatMovieManager catMovieManager = new CatMovieManager();

        List<Integer> categoryIds = catMovieManager.getCategoryIdsOfMovie(movie);
        if(categoryIds.size()!=0)
        return categoryManager.getCategoriesOfMovie(categoryIds);
        else
            return null;
    }
    
}
