package gui;

import be.Movie;
import bll.util.URLConverter;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;

public class MovieController implements Initializable {
    @FXML
    private TextField txtMovieTitle;
    @FXML
    private TextArea textAreaCategories;
    @FXML
    private ComboBox comboBoxCategories;
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
    private String lastViewedDate;

    public MovieController ()
    {
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
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy/MM/dd");
        if(!edit)
        {
            LocalDate standardDate = LocalDate.of(1969,04,20);
            mainSceneModel.createMovie(txtMovieTitle.getText(),Float.parseFloat(txtIMDBRating.getText()),Float.parseFloat(txtPersonalRating.getText()),txtMovieFilePath.getText(),standardDate);
        }
        else
        {
            LocalDate ViewedDate = LocalDate.parse(lastViewedDate,dateTimeFormatter);
            Movie movie = new Movie(txtMovieTitle.getText(),Float.parseFloat(txtIMDBRating.getText()),Float.parseFloat(txtPersonalRating.getText()),txtMovieFilePath.getText(),ViewedDate);
            movie.setId(movieId);
            mainSceneModel.updateMovie(movie);
        }
        ((Stage) btnSave.getScene().getWindow()).close();
    }



    public void setMainSceneModel(MainSceneModel mainscenemodel) {
        mainSceneModel = mainscenemodel;
    }

    public void setMovieValues(int movieId, String movieName, float movieIMDBRating, float moviePersonalRating, String filelink, String picturePath, LocalDate lastview, String categories)
    {
        txtMovieFilePath.setText(filelink);
        txtIMDBRating.setText(String.valueOf(movieIMDBRating));
        txtMovieTitle.setText(movieName);
        txtPersonalRating.setText(String.valueOf(moviePersonalRating));
        txtPicturePath.setText(picturePath);
        textAreaCategories.setText(categories);
        this.movieId = movieId;
        lastViewedDate = lastview.toString();
        edit = true;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources)
    {
        txtMovieFilePath.setText("");
        txtIMDBRating.setText("");
        txtMovieTitle.setText("");
        txtPersonalRating.setText("");
        txtPicturePath.setText("");
        textAreaCategories.setText("");
        edit = false;

    }

}
