package gui.controllers;

import be.Category;
import be.Movie;
import gui.AlertHandler;
import gui.MainSceneModel;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class MovieController implements Initializable {
    @FXML
    private ListView<String> lvChosenCategories;
    @FXML
    private ListView<String> lvAvailableCategories;
    @FXML
    private TextField txtMovieTitle;
    @FXML
    private TextField txtIMDBRating;
    @FXML
    private TextField txtPersonalRating;
    @FXML
    private TextField txtMovieFilePath;
    @FXML
    private TextField txtPicturePath;
    @FXML
    private Button btnSave;
    @FXML

    private MainSceneModel mainSceneModel;
    private Movie editableMovie;
    private boolean edit;


    public MovieController () throws Exception {
    }

    /**
     * Initializes the text field values, so current information can be autofilled when editing a movie
     */
    @Override
    public void initialize(URL location, ResourceBundle resources)
    {
        txtMovieFilePath.setText("");
        txtIMDBRating.setText("");
        txtMovieTitle.setText("");
        txtPersonalRating.setText("");
        txtPicturePath.setText("");
        edit = false;
    }

    /**
     * Opens a file chooser to allow the user to pick a movie to add,
     * then fills out the absolute path to the selected movie as well as its name
     */
    public void chooseMoviePath(ActionEvent actionEvent)
    {
        FileChooser fc = new FileChooser();
        fc.setInitialDirectory(new java.io.File("."));
        fc.setTitle("Choose a movie");

        fc.getExtensionFilters().add(new FileChooser.ExtensionFilter("Movie Files", "*.mp4", "*.mpeg4"));
        File selectedFile = fc.showOpenDialog(null);
        if (selectedFile != null) {
            txtMovieFilePath.setText(selectedFile.getAbsolutePath());
            txtMovieTitle.setText(selectedFile.getName());
        }
    }

    /**
     * Opens a file chooser to allow the user to pick an image to add, only .jpeg and .mpeg4 files allowed,
     * then fills out the absolute path to the selected image
     */
    public void choosePicturePath(ActionEvent actionEvent) {
        FileChooser fc = new FileChooser();
        fc.setInitialDirectory(new java.io.File("."));
        fc.setTitle("Choose a Picture");

        fc.getExtensionFilters().add(new FileChooser.ExtensionFilter("Picture Files", "*.jpeg", "*.png"));
        File selectedFile = fc.showOpenDialog(null);
        if (selectedFile != null) {
            txtPicturePath.setText(selectedFile.getAbsolutePath());
        }
    }

    /**
     * When button is clicked, either creates a new movie with the information in the text fields and lists
     * or updates the information on the movie being edited.
     * Then closes the window
     */
    public void saveMovie(ActionEvent actionEvent) throws IOException {
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        boolean isStringANumber = true;
        try{
            Float.parseFloat(txtIMDBRating.getText());
        }
        catch (NumberFormatException e){
            isStringANumber = false;
        }
        ArrayList<String> alertList = new ArrayList<>();
        if(txtMovieTitle.getText().trim().equals("") || txtMovieFilePath.getText().trim().equals("") || !isStringANumber )
        {
            if(txtMovieTitle.getText().trim().equals(""))
                alertList.add("Movie needs a name.");

            if (txtMovieFilePath.getText().trim().equals(""))
                alertList.add("Movie needs a filepath.");
            if(!isStringANumber)
                alertList.add("Please check the set rating is a number. (Use Dot for separation)");
            String alertmessage = "";
            for (int i = 0 ; i<alertList.size() ; i++)
                alertmessage = alertmessage + " " + alertList.get(i);
            AlertHandler.informationAlert(alertmessage);
        }
        else if(!edit)
        {

            LocalDate standardDate = LocalDate.of(1969,04,20);
            Movie movie = new Movie(txtMovieTitle.getText(),Float.parseFloat(txtIMDBRating.getText()),txtMovieFilePath.getText(),standardDate);

            if (!txtPersonalRating.getText().isBlank())
                movie.setMoviePersonalRating(Float.parseFloat(txtPersonalRating.getText()));
            if (!txtPicturePath.getText().isBlank())
                movie.setPictureFilelink(txtPicturePath.getText());
            //Sets categories for the movie created
            ArrayList<Category> categoryList = new ArrayList<>();
            for(String s : lvChosenCategories.getItems())
            {
                for (Category c : mainSceneModel.getAllCategories())
                {
                    if (s.equals(c.getName()))
                        categoryList.add(c);
                }
            }
            movie.setMovieCategories(categoryList);
            mainSceneModel.createMovie(movie);
            ((Stage) btnSave.getScene().getWindow()).close();
        }

        else
        {
           editableMovie.setMovieName(txtMovieTitle.getText());
           editableMovie.setMovieIMDBRating(Float.parseFloat(txtIMDBRating.getText()));
           editableMovie.setMovieFilelink(txtMovieFilePath.getText());
            //Sets categories for the movie created
            ArrayList<Category> categoryList = editableMovie.getMovieCategories();
            ObservableList<Category> allCategories = mainSceneModel.getAllCategories();
            categoryList.clear();
            for (Category c : allCategories)
            {
                for (String s : lvChosenCategories.getItems()) {
                    {
                        if (s.equals(c.getName()))
                            categoryList.add(c);
                    }
                }
            }
            editableMovie.setMovieCategories(categoryList);
            if (!txtPersonalRating.getText().isBlank())
                editableMovie.setMoviePersonalRating(Float.parseFloat(txtPersonalRating.getText()));
            if (!txtPicturePath.getText().isBlank())
                editableMovie.setPictureFilelink(txtPicturePath.getText());
            mainSceneModel.updateMovie(editableMovie);
            ((Stage) btnSave.getScene().getWindow()).close();
        }

    }

    /**
     * Sets the reference to MainSceneModel
     */
    public void setMainSceneModel(MainSceneModel mainscenemodel) {
        mainSceneModel = mainscenemodel;
    }

    /**
     * Sets the information of the currently selected movie, so the fields are automatically filled when trying to edit a movie
     * @param movie - the selected movie from main view
     */
    public void setMovie(Movie movie)
    {
        editableMovie = movie;
        txtMovieFilePath.setText(movie.getMovieFilelink());
        txtIMDBRating.setText(String.valueOf(movie.getMovieIMDBRating()));
        txtMovieTitle.setText(movie.getMovieName());
        txtPersonalRating.setText(String.valueOf(movie.getMoviePersonalRating()));
        txtPicturePath.setText(movie.getPicturePath());
        for(Category c : movie.getMovieCategories())
            lvChosenCategories.getItems().addAll(c.getName());
        edit = true;
    }

    /**
     * Adds all categories except "All Movies" to the movies available categories
     */
    public void setLvAvailableCategories(ObservableList<Category> allCategories) {
        for(Category c : allCategories)
        {
            if(c.getId() !=1)
                lvAvailableCategories.getItems().add(c.getName());
        }
    }

    /**
     * Moves the clicked item from "Available Categories" to "Chosen Categories"
     */
    public void selectCategory(MouseEvent mouseEvent)
    {
        if (lvAvailableCategories.getSelectionModel().getSelectedItem()!=null)
        {
            lvChosenCategories.getItems().add(lvAvailableCategories.getSelectionModel().getSelectedItem());
            lvAvailableCategories.getItems().remove(lvAvailableCategories.getSelectionModel().getSelectedItem());
            lvAvailableCategories.getSelectionModel().clearSelection();
        }
    }
    
    /**
     * Moves the clicked item from "Chosen Categories" to "Available Categories"
     */
    public void deSelectCategory(MouseEvent mouseEvent)
    {
        if (lvChosenCategories.getSelectionModel().getSelectedItem()!= null)
        {
            lvAvailableCategories.getItems().add(lvChosenCategories.getSelectionModel().getSelectedItem());
            lvChosenCategories.getItems().remove(lvChosenCategories.getSelectionModel().getSelectedItem());
            lvChosenCategories.getSelectionModel().clearSelection();
        }
    }
}
