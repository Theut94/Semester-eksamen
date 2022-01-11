package bll;

import be.Movie;
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
}
