package be;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import java.util.ArrayList;

/**
 *  This is the category class, we simple have a name, id and a list of movies here.
 */
public class Category
{
    private int id;
    private StringProperty categoryName = new SimpleStringProperty();
    private ArrayList listOfMovies;

    public Category(String name)
    {
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

    public ArrayList getListOfMovies() {
        return listOfMovies;
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
