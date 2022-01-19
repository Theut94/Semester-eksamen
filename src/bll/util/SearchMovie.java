package bll.util;

import be.Movie;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class SearchMovie {

    /**
     * Compares movieList with a String from query
     * @param movieList - the list of movies to be searched in
     * @param query - the query to use in the search
     * @return a list of movies containing the query
     */
    public List<Movie> search(List<Movie> movieList, String query)
    {
        List<Movie> result = new ArrayList<>();

        for (Movie movie : movieList)
        {
            if (compareToTitle(movie, query) || compareToCategory(movie, query))
            {
                result.add(movie);
            }
        }
        return result;
    }

    /**
     * Checks to see if the movie's title contains the query
     * @param movie - the movie being compared
     * @param query
     * @return true if the movie title contains the query
     */
    private boolean compareToTitle(Movie movie, String query)
    {
        return movie.getMovieName().toLowerCase().contains(query.toLowerCase());
    }

    /**
     * Checks to see if the movie's categories contains the query
     * @param movie - the movie being compared
     * @param query
     * @return true if the movie categories contains the query
     */
    private boolean compareToCategory(Movie movie, String query)
    {
        return movie.getCategoriesToString().toLowerCase(Locale.ROOT).contains(query.toLowerCase(Locale.ROOT));
    }
}
