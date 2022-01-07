package bll;

import be.Movie;
import dal.MovieDAO;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.awt.*;
import java.time.LocalDate;
import java.util.ArrayList;

public class MovieManager
{
    private MovieDAO movieDAO;

    public MovieManager()
    {

    }

    public ObservableList<Movie> getAllMoviesToObservable()
    {
        ObservableList<Movie> observableMovies = FXCollections.observableArrayList();
        observableMovies.addAll(movieDAO.getAllMovies());
        return observableMovies;
    }

    public Movie createMovie(String movieName, float ratingPersonal, float ratingIMDB, String filelink, LocalDate lastview)
    {
        return movieDAO.createMovie(movieName, ratingPersonal, ratingIMDB, filelink, lastview);
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
