package be;

import javafx.beans.property.*;

import java.awt.*;
import java.util.ArrayList;


public class Movie
{
    private StringProperty movieName = new SimpleStringProperty();
    private FloatProperty movieIMDBRating = new SimpleFloatProperty();
    private FloatProperty moviePersonalRating = new SimpleFloatProperty();
    private ArrayList movieCategories;
    private IntegerProperty id = new SimpleIntegerProperty();

    public Movie(int id, String movieName, float movieIMDBRating, float moviePersonalRating, ArrayList movieCategories)
    {
        this.id.set(id);
        this.movieName.set(movieName);
        this.movieIMDBRating.set(movieIMDBRating);
        this.moviePersonalRating.set(moviePersonalRating);
        this.movieCategories.addAll(movieCategories);

    }

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
