package be;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 *  This is the category class, we simply have a name, id and a list of movies here.
 */
public class Category
{
    private int id;
    private StringProperty categoryName = new SimpleStringProperty();
    private ObservableList<Movie> listOfMovies;

    public Category(String name)
    {
        listOfMovies = FXCollections.observableArrayList();
        this.categoryName.set(name);
        id=-1;
    }

    // Getters and setters.
    public int getId() {
        return id;
    }

    public String getName() {
        return categoryName.get();
    }

    public ObservableList<Movie> getListOfMovies() {
        return listOfMovies;
    }

    public void setListOfMovies(ObservableList<Movie> listOfMovies) {
        this.listOfMovies = listOfMovies;
    }

    public void setName(String name) {
        this.categoryName.set(name);
    }

    public String getCategoryName() {
        return categoryName.get();
    }

    public void setId(int id) {
        this.id = id;
    }
}
