package gui.controllers;

import be.Category;
import be.Movie;
import bll.util.MoviePlayer;
import gui.AlertHandler;
import gui.MainSceneModel;
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
    private TextField txtUpdatedRating;
    @FXML
    private Label lblLastView;
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
                    AlertHandler.informationAlert("Select a category before you search, please.");
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    public void PlayMovie(ActionEvent actionEvent) throws IOException
    {
        Movie selectedMovie = tvMovies.getSelectionModel().getSelectedItem();
        MoviePlayer.playMovie(selectedMovie.getMovieFilelink());
        selectedMovie.setLastview(LocalDate.now());
        mainSceneModel.updateMovie(selectedMovie);
        lblLastView.setText(LocalDate.now().toString());
    }

    public void ChangeRating(ActionEvent actionEvent) throws IOException {
        Movie selectedMovie = tvMovies.getSelectionModel().getSelectedItem();
        selectedMovie.setMoviePersonalRating(Float.parseFloat(txtUpdatedRating.getText()));
        mainSceneModel.updateMovie(selectedMovie);
        lblRatingPersonal.setText("" + selectedMovie.getMoviePersonalRating());
        tvMovies.refresh();
    }


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
        Movie selectedMovie = tvMovies.getSelectionModel().getSelectedItem();
        if (selectedMovie != null)
            mainSceneModel.editMovie(selectedMovie);
        else
            AlertHandler.informationAlert("You haven't selected a movie");
    }

    public void deleteMovie(ActionEvent actionEvent) {
        if (tvMovies.getSelectionModel().getSelectedItem() != null) {
        if (AlertHandler.confirmationAlert("Do you really want to delete this movie?")) {
            mainSceneModel.deleteMovie(tvMovies.getSelectionModel().getSelectedItem());
            tvMovies.getItems().remove(tvMovies.getSelectionModel().getSelectedItem());
        }}
        else
            AlertHandler.informationAlert("You haven't selected a movie");
    }

    public void selectCategory(MouseEvent mouseEvent) {
        tcMovieTitle.setCellValueFactory(new PropertyValueFactory<Movie, String>("movieName"));
        tcRatingIMDB.setCellValueFactory(new PropertyValueFactory<Movie, Float>("movieIMDBRating"));
        tcRatingPersonal.setCellValueFactory(new PropertyValueFactory<Movie, Float>("moviePersonalRating"));
        try {
            Category selectedCategory = tvCategories.getSelectionModel().getSelectedItem();
            if(selectedCategory != null)
            {
                if(selectedCategory.getId() != 1) //all movie id = 1
                    tvMovies.setItems(mainSceneModel.getMoviesFromCategory(selectedCategory));
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
                lblLastView.setText(selectedItem.getLastview().toString());
                if (selectedItem.getPicturePath() !="") {
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

    public void newCategory(ActionEvent actionEvent) throws IOException {
        mainSceneModel.newCategory();
    }

    public void editCategory(ActionEvent actionEvent) throws IOException {
        Category selectedItem = tvCategories.getSelectionModel().getSelectedItem();
        if(selectedItem != null)
            if (selectedItem.getId() == 1)
                AlertHandler.informationAlert("You cannot edit this category!");
            else
                mainSceneModel.editCategory(selectedItem);
        else
            AlertHandler.informationAlert("You haven't selected a category!");
        tvCategories.refresh();
    }

    public void deleteCategory(ActionEvent actionEvent) {
        Category selectedItem = tvCategories.getSelectionModel().getSelectedItem();
        if (selectedItem != null) {
            if (selectedItem.getId() == 1)
                AlertHandler.informationAlert("You cannot delete this category!");
            else if (AlertHandler.confirmationAlert("Are you sure you want to delete the \"" + selectedItem.getName() + "\" category?"))
                mainSceneModel.deleteCategory(selectedItem);
        }
        else
            AlertHandler.informationAlert("You haven't selected a category!");
        }
}

