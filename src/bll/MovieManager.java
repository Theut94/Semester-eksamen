package bll;

import be.Category;
import be.Movie;
import bll.util.SearchMovie;
import dal.MovieDAO;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MovieManager
{
    private MovieDAO movieDAO;
    SearchMovie filter;

    public MovieManager() throws IOException {
        filter = new SearchMovie();
        movieDAO = new MovieDAO();
    }

    /**
     * Connects to MovieDAO to get a list of all movies in the database.
     * The movies are then added to an observable list
     * @return observable list of all movies
     */
    public ObservableList<Movie> getAllMoviesToObservable() {
        ObservableList<Movie> observableMovies = FXCollections.observableArrayList();
        observableMovies.addAll(movieDAO.getAllMovies());
        return observableMovies;
    }

    /**
     * Connects to the SearchMovie class to search in the given movies
     * @param movieList - the list of movies to be searched in
     * @param keyChar - the query to use in the search
     * @return the list of movies that fits the query
     */
    public List<Movie> getSearchedMovies(List<Movie> movieList, String keyChar) {
        return filter.search(movieList, keyChar);
    }

    /**
     * Connects to MovieDAO to create a new movie
     * @param movie - the movie to be created
     */
    public void createMovie(Movie movie) {
        movieDAO.createMovie(movie);
    }

    /**
     * Connects to MovieDAO to update the information of a specific movie
     * @param movie - the movie to be updated
     */
    public void updateMovie(Movie movie) {
        movieDAO.updateMovie(movie);
    }

    /**
     * Connects to MovieDAO to delete a specific movie
     * @param movie - the movie to be deleted
     */
    public void deleteMovie(Movie movie) {
        movieDAO.deleteMovie(movie);
    }

    /**
     * Connects to MovieDAO to get the movies with the IDs of the list provided
     * @param movieId - a list of movie IDs
     * @return a list of movies
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


    /**
     * Connects to CategoryDAO and CatMovieDAO to get a list of the movie's categories
     * @param movie - the movie of which to find the categories
     * @return arraylist of categories of the movie or an empty list if it has none
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
