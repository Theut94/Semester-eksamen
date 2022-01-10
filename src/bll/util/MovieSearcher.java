package bll.util;

import be.Movie;

import java.util.ArrayList;
import java.util.List;

public class MovieSearcher {

    /**
     * This class handles searching for songs through a list of songs - mainly for All Songs.
     */


    public List<Movie> searchMovies(List<Movie> searchBase, String searchQuery) throws Exception {
        ArrayList<Movie> searchedMovies = new ArrayList<>();
        for (Movie movie : searchBase) {
            if (compareToMovieName(searchQuery, movie)
                    || compareToSongName(searchQuery, movie)){
                searchedMovies.add(movie);
            }
        }
        return searchedMovies;
    }

    /**
     * Compares input to artist names
     * @return true if a match is found
     */
    private boolean compareToMovieName(String query, Movie movie)
    {
        return movie.getMovieName().toLowerCase().contains(query.toLowerCase());
    }

    /**
     * Compares input to song titles
     * @return true if a match is found
     */
    private boolean compareToSongName(String query, Movie movie)
    {
        return query.contains(String.valueOf(movie.getMovieIMDBRating()));
    }


}
