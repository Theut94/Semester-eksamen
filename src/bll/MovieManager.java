package bll;

import be.Movie;
import dal.MovieDAO;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.awt.*;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;

public class MovieManager
{
    private MovieDAO movieDAO = new MovieDAO();

    public MovieManager() throws IOException {

    }

    public ObservableList<Movie> getAllMoviesToObservable()
    {
        ObservableList<Movie> observableMovies = FXCollections.observableArrayList();
        observableMovies.addAll(movieDAO.getAllMovies());
        return observableMovies;
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
