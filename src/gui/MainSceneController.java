package gui;

import be.Category;
import be.Movie;
import bll.util.AlertHandler;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;

import java.io.IOException;
import java.time.LocalDate;

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
                if(tvCategories.getSelectionModel().getSelectedItem()!=null)
                {
                    mainSceneModel.search(newValue, tvCategories.getSelectionModel().getSelectedItem());
                    tvMovies.setItems(mainSceneModel.getSearchedMovies());
                }
                else
                    AlertHandler.informationAlert("SELECT A CATEGORYDUMMY");
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    public void PlayMovie(ActionEvent actionEvent) throws IOException {
        mainSceneModel.playMovie();
    }

    public void ChangeRating(ActionEvent actionEvent) {}

    public void OptionsCategory(ActionEvent actionEvent) {}

    public void RunCleanup(ActionEvent actionEvent)
    {
        ObservableList<Movie> cleanupList = FXCollections.observableArrayList();
        for(Movie m : mainSceneModel.getAllMovies())
        {
            if(m.getLastview().isBefore(LocalDate.now().minusYears(2)) && m.getMoviePersonalRating()!=-1 && m.getMoviePersonalRating()<=6 && !m.getLastview().equals(LocalDate.of(1969,04,20)))
                cleanupList.add(m);
        }
        if(!cleanupList.isEmpty())
        {
            tcMovieTitle.setCellValueFactory(new PropertyValueFactory<Movie, String>("movieName"));
            tcRatingIMDB.setCellValueFactory(new PropertyValueFactory<Movie, Float>("movieIMDBRating"));
            tcRatingPersonal.setCellValueFactory(new PropertyValueFactory<Movie, Float>("moviePersonalRating"));
            tvMovies.setItems(cleanupList);
            AlertHandler.informationAlert("You now see all movies you haven't watched in 2 years, and has a personal rating below 6");
        }
        else
        {
            AlertHandler.informationAlert("There are no movies, that needs to be cleaned up");
        }
    }

    public void newMovie(ActionEvent actionEvent) throws IOException {
        mainSceneModel.newMovie();
    }

    public void editMovie(ActionEvent actionEvent) throws IOException {
        mainSceneModel.editMovie(tvMovies.getSelectionModel().getSelectedItem());
    }

    public void deleteMovie(ActionEvent actionEvent) {
        if (AlertHandler.confirmationAlert("Do you really want to delete this movie?")) {
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

