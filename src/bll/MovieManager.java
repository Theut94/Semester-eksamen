package bll;

import be.Category;
import be.Movie;
import bll.util.MoviePlayer;
import bll.util.SearchMovie;
import dal.MovieDAO;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.awt.*;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;


public class MovieManager
{
    private MovieDAO movieDAO = new MovieDAO();
    SearchMovie filter;

    public MovieManager() throws IOException {
        filter = new SearchMovie();
    }

    /**
     * Here we get a list of all the movies
     */
    public ObservableList<Movie> getAllMoviesToObservable()
    {
        ObservableList<Movie> observableMovies = FXCollections.observableArrayList();
        observableMovies.addAll(movieDAO.getAllMovies());
        return observableMovies;
    }




    public List<Movie> getSearchedMovies(List<Movie> movieList, String keyChar)
    {
        return filter.search(movieList, keyChar);
    }

    public void createMovie(Movie movie)
    {
        movieDAO.createMovie(movie);
    }

    public void updateMovie(Movie movie)
    {
        movieDAO.updateMovie(movie);
    }

    public void deleteMovie(Movie movie)
    {
        movieDAO.deleteMovie(movie);
    }

    /**
     * Here we use a list of movie ID's from a category, to get a list of movies.
     * @param movieId
     * @return
     */
    public List<Movie> getMoviesFromId(List<Integer> movieId)
    {
        ArrayList<Movie> moviesFromCategory = new ArrayList<>();
        ArrayList<Movie> allMovies = new ArrayList<>();
        allMovies.addAll(movieDAO.getAllMovies());
        for(Movie m : allMovies)
        {
            for(int i : movieId) {
                if (m.getId() == i)
                {moviesFromCategory.add(m);}
            }
        }
        return moviesFromCategory;
    }



    public void playMovie() throws IOException {
        MoviePlayer.main();
    }

    /**
     * Here we get a list of a movie's Category.
     * @param movie
     * @return
     * @throws IOException
     */
    public ArrayList<Category> getCategoriesOfMovie(Movie movie) throws IOException {
        CategoryManager categoryManager = new CategoryManager();
        CatMovieManager catMovieManager = new CatMovieManager();

        List<Integer> categoryIds = catMovieManager.getCategoryIdsOfMovie(movie);
        if(categoryIds.size()!=0)
        return categoryManager.getCategoriesOfMovie(categoryIds);
        else
            return new ArrayList<Category>();
    }
    
}
