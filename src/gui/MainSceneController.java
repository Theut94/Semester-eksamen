package gui;

import be.Category;
import be.Movie;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;

import java.io.IOException;
import java.util.Optional;

public class MainSceneController {

    public MenuButton menuButtonMovieOptions;
    public MenuButton menuButtonCategoryOptions;
    public TableView<Movie>tvMovies;
    public TableColumn <Movie, String> tcMovieTitle;
    public TableColumn <Movie, Float> tcRatingIMDB;
    public TableColumn <Movie, Float> tcRatingPersonal;
    public TableView<Category>tvCategories;
    public TableColumn<Category, String> tcCategory;
    private MainSceneModel mainSceneModel = new MainSceneModel();

    @FXML
    private TextField movieSearch;

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

    public void PlayMovie(ActionEvent actionEvent) {

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
            if(tvCategories.getSelectionModel().getSelectedItem().getId()!=1)
            tvMovies.setItems(mainSceneModel.getMoviesFromCategory(tvCategories.getSelectionModel().getSelectedItem()));
            else
                tvMovies.setItems(mainSceneModel.getAllMovies());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

