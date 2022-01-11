package gui;

import bll.CatMovieManager;
import bll.CategoryManager;
import bll.MovieManager;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;

import java.io.IOException;

public class MainSceneController {

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

    public void OptionsMovie(ActionEvent actionEvent) {
    }

    public void OptionsCategory(ActionEvent actionEvent) {
    }

    public void RunCleanup(ActionEvent actionEvent) {
    }
}

