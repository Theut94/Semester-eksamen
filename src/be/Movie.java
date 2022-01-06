package be;

import javafx.beans.property.*;

import java.awt.*;
import java.time.LocalDate;
import java.util.ArrayList;

/**
 *  Movie class, this is where we describe a movie
 */

public class Movie
{
    private StringProperty movieName = new SimpleStringProperty();
    private FloatProperty movieIMDBRating = new SimpleFloatProperty();
    private FloatProperty moviePersonalRating = new SimpleFloatProperty();
    private ArrayList movieCategories;
    private IntegerProperty id = new SimpleIntegerProperty();
    private LocalDate lastview;

    //We still need a Date variable.

    public Movie(int id, String movieName, float movieIMDBRating, float moviePersonalRating, String filelink, LocalDate lastview)
    {
        this.id.set(id);
        this.movieName.set(movieName);
        this.movieIMDBRating.set(movieIMDBRating);
        this.moviePersonalRating.set(moviePersonalRating);

    }

    // Getters and setters for the class properties

    public String getMovieName() {
        return movieName.get();
    }

    public void setMovieName(String movieName) {
        this.movieName.set(movieName);
    }

    public double getMovieIMDBRating() {
        return movieIMDBRating.get();
    }

    public void setMovieIMDBRating(float movieIMDBRating) {
        this.movieIMDBRating.set(movieIMDBRating);
    }

    public double getMoviePersonalRating() {
        return moviePersonalRating.get();
    }

    public void setMoviePersonalRating(float moviePersonalRating) {
        this.moviePersonalRating.set(moviePersonalRating);
    }

    public int getId() {
        return id.get();
    }
}
