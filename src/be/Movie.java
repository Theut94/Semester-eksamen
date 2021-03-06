package be;

import javafx.beans.property.*;

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


    public Movie(String movieName, float movieIMDBRating, String movieFilelink, LocalDate lastview)
    {
        this.movieName.set(movieName);
        this.movieIMDBRating.set(movieIMDBRating);
        this.movieFilelink = movieFilelink;
        this.lastview = lastview;
        id.set(-1);
        moviePersonalRating.set(-1);
        movieCategories = new ArrayList<>();
        pictureFilelink = "";
    }

    /**
     * Converts the movie's list of categories to a string
     * @return a string containing the categories of the movie
     */
    public String getCategoriesToString() {
        String allCategories = "";
        if(movieCategories.size() != 0)
        {
            for (Category movieCategory : movieCategories) {
                String addition = movieCategory.getName();
                allCategories = allCategories + addition + " ";
            }
        }
        return allCategories;
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

    public ArrayList<Category> getMovieCategories() {
        return movieCategories;
    }

    public String getPictureFilelink() {
       return pictureFilelink;
    }

    public void setMovieCategories(ArrayList<Category> movieCategories) {
        this.movieCategories = movieCategories;
    }

    public void setPictureFilelink(String pictureFilelink) {
        this.pictureFilelink = pictureFilelink;
    }
}
