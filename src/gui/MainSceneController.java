package gui;

import be.Category;
import be.Movie;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;

import java.io.IOException;
import java.util.Optional;

public class MainSceneController {

    @FXML
    private ImageView imgMovie;
    @FXML
    private MenuButton menuButtonMovieOptions;
    @FXML
    private MenuButton menuButtonCategoryOptions;
    @FXML
    private TableView<Movie>tvMovies;
    @FXML
    private TableColumn <Movie, String> tcMovieTitle;
    @FXML
    private TableColumn <Movie, Float> tcRatingIMDB;
    @FXML
    private TableColumn <Movie, Float> tcRatingPersonal;
    @FXML
    private TableView<Category>tvCategories;
    @FXML
    private TableColumn<Category, String> tcCategory;
    @FXML
    private DatePicker dpLastView;
    @FXML
    private Label lblMovieName;
    @FXML
    private Label lblRatingIMDB;
    @FXML
    private Label lblRatingPersonal;
    @FXML
    private TextField movieSearch;

    private MainSceneModel mainSceneModel = new MainSceneModel();

    public MainSceneController() throws Exception {
        mainSceneModel = new MainSceneModel();
    }

    public void initialize() {

        //Category initialize
        tcCategory.setCellValueFactory(new PropertyValueFactory<Category, String>("categoryName"));
        try {
            tvCategories.setItems(mainSceneModel.getAllCategories());
        } catch (Exception e) {
            e.printStackTrace();
        }


        //Movie search
        movieSearch.textProperty().addListener((observableValue, oldValue, newValue) -> {
            try {
                mainSceneModel.search(newValue);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    public void PlayMovie(ActionEvent actionEvent) throws IOException {
        mainSceneModel.playMovie();
    }

    public void ChangeRating(ActionEvent actionEvent) {
    }

    public void OptionsMovie(ActionEvent actionEvent)
    {
    }

    public void OptionsCategory(ActionEvent actionEvent) {
    }

    public void RunCleanup(ActionEvent actionEvent) {
    }

    public void newMovie(ActionEvent actionEvent) throws IOException {
        mainSceneModel.newMovie();
    }

    public void editMovie(ActionEvent actionEvent) throws IOException {
        mainSceneModel.editMovie(tvMovies.getSelectionModel().getSelectedItem());
    }

    public void deleteMovie(ActionEvent actionEvent) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION,"Are you sure you want to delete?", ButtonType.YES,ButtonType.NO);
        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.YES) {
            mainSceneModel.deleteMovie(tvMovies.getSelectionModel().getSelectedItem());
            tvMovies.getItems().remove(tvMovies.getSelectionModel().getSelectedItem());
        }
    }

    public void selectCategory(MouseEvent mouseEvent) {
        tcMovieTitle.setCellValueFactory(new PropertyValueFactory<Movie, String>("movieName"));
        tcRatingIMDB.setCellValueFactory(new PropertyValueFactory<Movie, Float>("movieIMDBRating"));
        tcRatingPersonal.setCellValueFactory(new PropertyValueFactory<Movie, Float>("moviePersonalRating"));

        try {
            if(tvCategories.getSelectionModel().getSelectedItem() != null)
            {
                if(tvCategories.getSelectionModel().getSelectedItem().getId() != 1) //all movie id = 1
                    tvMovies.setItems(mainSceneModel.getMoviesFromCategory(tvCategories.getSelectionModel().getSelectedItem()));
                else
                    tvMovies.setItems(mainSceneModel.getAllMovies());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void showMovieInfo(MouseEvent mouseEvent) {
        try{
            Movie selectedItem = tvMovies.getSelectionModel().getSelectedItem();
            if(selectedItem != null){
                lblMovieName.setText(selectedItem.getMovieName());
                lblRatingIMDB.setText(Float.toString(selectedItem.getMovieIMDBRating()));
                lblRatingPersonal.setText(Float.toString(selectedItem.getMoviePersonalRating()));
                if (selectedItem.getPicturePath() != null) {
                    imgMovie.setImage(new Image(selectedItem.getPicturePath()));
                }
                else {
                    //Set image to "NoImage.png", line below doesn't work
                    //imgMovie.setImage(new Image("@../Media/NoImage.png"));
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

