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
    private ArrayList<Category> movieCategories;
    private IntegerProperty id = new SimpleIntegerProperty();
    private LocalDate lastview;
    private String movieFilelink;
    private String pictureFilelink;

    //We still need a Date variable.

    public Movie(String movieName, float movieIMDBRating, float moviePersonalRating, String movieFilelink, LocalDate lastview)
    {
        this.movieName.set(movieName);
        this.movieIMDBRating.set(movieIMDBRating);
        this.moviePersonalRating.set(moviePersonalRating);
        this.movieFilelink = movieFilelink;
        this.lastview = lastview;
        id.set(-1);
        movieCategories = new ArrayList();

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

    public String getMovieFilelink() {
        return movieFilelink;
    }

    public void setMovieFilelink(String movieFilelink) {
        this.movieFilelink = movieFilelink;
    }

    public void setId(int id) {
        this.id.set(id);
    }


    public String getCategoriesToString()
    {
        String allCategories = new String();
        for(int i = 0; i< movieCategories.size(); i++)
        {
            String addition = movieCategories.get(i).toString();
            allCategories = allCategories +addition + " ";

        }
        return allCategories;
    }

    public ArrayList<Category> getMovieCategories() {
        return movieCategories;
    }

    public String getPicturePath()
    {
       return pictureFilelink;
    }

    public void setMovieCategories(ArrayList<Category> movieCategories) {
        this.movieCategories = movieCategories;
    }
}
