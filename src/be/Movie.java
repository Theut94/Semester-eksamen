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
    private String filelink;

    //We still need a Date variable.

    public Movie(String movieName, float movieIMDBRating, float moviePersonalRating, String filelink, LocalDate lastview)
    {
        this.movieName.set(movieName);
        this.movieIMDBRating.set(movieIMDBRating);
        this.moviePersonalRating.set(moviePersonalRating);
        this.filelink = filelink;
        this.lastview = lastview;
        id.set(-1);

    }

    // Getters and setters for the class properties

    public String getMovieName() {
        return movieName.get();
    }

    public void setMovieName(String movieName) {
        this.movieName.set(movieName);
    }

    public float getMovieIMDBRating() {
        return movieIMDBRating.get();
    }

    public void setMovieIMDBRating(float movieIMDBRating) {
        this.movieIMDBRating.set(movieIMDBRating);
    }

    public float getMoviePersonalRating() {
        return moviePersonalRating.get();
    }

    public void setMoviePersonalRating(float moviePersonalRating) {
        this.moviePersonalRating.set(moviePersonalRating);
    }

    public int getId() {
        return id.get();
    }

    public LocalDate getLastview() {
        return lastview;
    }
    public void setLastview(LocalDate lastview)
    {
        this.lastview = lastview;
    }

    public String getFilelink() {
        return filelink;
    }

    public void setFilelink(String filelink) {
        this.filelink = filelink;
    }

    public void setId(int id) {
        this.id.set(id);
    }
}
