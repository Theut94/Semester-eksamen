package gui;

import be.Movie;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.MenuButton;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

import java.io.IOException;

public class MainSceneController {

    public MenuButton menuButtonMovieOptions;
    public MenuButton menuButtonCategoryOptions;
    public TableView<Movie>tvMovies;
    private MainSceneModel mainSceneModel = new MainSceneModel();

    @FXML
    private TextField movieSearch;

    public MainSceneController() throws Exception {
        mainSceneModel = new MainSceneModel();
    }


    public void initialize() {

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
    }
}

