package gui;

import be.Category;
import be.Movie;
import bll.util.URLConverter;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
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
    private Button btnFilePathMovie;
    @FXML
    private Button btnFilePathPicture;
    
    private MainSceneModel mainSceneModel;
    private boolean edit;
    private int movieId;
    private LocalDate lastViewedDate;

    public MovieController ()
    {
    }

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

    public void saveMovie(ActionEvent actionEvent)
    {
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        if(!edit)
        {
            LocalDate standardDate = LocalDate.of(1969,04,20);
            Movie movie = new Movie(txtMovieTitle.getText(),Float.parseFloat(txtIMDBRating.getText()),txtMovieFilePath.getText(),standardDate);

            if (!txtPersonalRating.getText().isBlank())
                movie.setMoviePersonalRating(Float.parseFloat(txtPersonalRating.getText()));
            if (!txtPicturePath.getText().isBlank())
                movie.setPictureFilelink(URLConverter.fileLinkToURI(txtPicturePath.getText()));
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
        }

        else
        {
            Movie movie = new Movie(txtMovieTitle.getText(),Float.parseFloat(txtIMDBRating.getText()),txtMovieFilePath.getText(),lastViewedDate);
            movie.setId(movieId);
            if (!txtPersonalRating.getText().isBlank())
                movie.setMoviePersonalRating(Float.parseFloat(txtPersonalRating.getText()));
            if (!txtPicturePath.getText().isBlank())
                movie.setPictureFilelink(URLConverter.fileLinkToURI(txtPicturePath.getText()));
            mainSceneModel.updateMovie(movie);
        }
        ((Stage) btnSave.getScene().getWindow()).close();
    }



    public void setMainSceneModel(MainSceneModel mainscenemodel) {
        mainSceneModel = mainscenemodel;
    }

    public void setMovieValues(int movieId, String movieName, float movieIMDBRating, float moviePersonalRating, String filelink, String picturePath, LocalDate lastview, ArrayList<Category> categories)
    {
        txtMovieFilePath.setText(filelink);
        txtIMDBRating.setText(String.valueOf(movieIMDBRating));
        txtMovieTitle.setText(movieName);
        txtPersonalRating.setText(String.valueOf(moviePersonalRating));
        txtPicturePath.setText(picturePath);
        this.movieId = movieId;
        lastViewedDate = lastview;
        for(Category c : categories)
            lvChosenCategories.getItems().addAll(c.getName());
        edit = true;
    }

    public void setLvAvailableCategories(ObservableList<Category> allCategories) {
        for(Category c : allCategories)
        {
            if(c.getId() !=1)
                lvAvailableCategories.getItems().add(c.getName());
        }

    }

    public void selectCategory(MouseEvent mouseEvent)
    {
        lvChosenCategories.getItems().add(lvAvailableCategories.getSelectionModel().getSelectedItem());
        lvAvailableCategories.getItems().remove(lvAvailableCategories.getSelectionModel().getSelectedItem());
    }

    public void deSelectCategory(MouseEvent mouseEvent)
    {
        lvAvailableCategories.getItems().add(lvChosenCategories.getSelectionModel().getSelectedItem());
        lvChosenCategories.getItems().remove(lvChosenCategories.getSelectionModel().getSelectedItem());
    }
}
