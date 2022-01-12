package bll.util;

import be.Movie;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class SearchMovie {

    /**
     * compares movieList with a String from query
     * @param movieList
     * @param query
     * @return a list of movies containing the String
     */
    public List<Movie> search(List<Movie> movieList, String query)
    {
        List<Movie> result = new ArrayList<>();

        for (Movie movie : movieList)
        {
            if (compareToTitle(movie, query) || compareToCategory(movie, query) || compareToCategory(movie, query))
            {
                result.add(movie);
            }
        }
        return result;
    }

    /**
     * checks to see if the movie contains the String
     * @param movie
     * @param query
     * @return true if the movie contains all the letters
     */
    /*private boolean compareToRating(Movie movie, String query)
    {
        return movie.getMovieIMDBRating().
    }*/

    /**
     * checks to see if the movie contains the String
     * @param movie
     * @param query
     * @return true if the movie contains all the letters
     */
    private boolean compareToTitle(Movie movie, String query)
    {
        return movie.getMovieName().toLowerCase().contains(query.toLowerCase());
    }

    private boolean compareToCategory(Movie movie, String query)
    {
        return movie.getCategoriesToString().toLowerCase(Locale.ROOT).contains(query.toLowerCase(Locale.ROOT));
    }
}

// title, genre, (imdb)