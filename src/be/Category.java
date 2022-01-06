package be;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import java.util.ArrayList;

public class Category
{
    private int id;
    private StringProperty name = new SimpleStringProperty();
    private ArrayList listOfMovies;

    public Category()
    {

    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name.get();
    }

    public ArrayList getListOfMovies() {
        return listOfMovies;
    }

    public void setName(String name) {
        this.name.set(name);
    }
}
