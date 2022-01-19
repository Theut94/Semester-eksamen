package dal;

import be.Category;
import be.Movie;

import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CatMovieDAO
{
    private DatabaseConnector DC = new DatabaseConnector();

    public CatMovieDAO() throws IOException {
    }

    /**
     * Creates an item in the CatMovie table to connect the given movie and category
     * @param movie
     * @param category
     */
    public void createCatMovie(Movie movie, Category category)
    {
        try(Connection connection = DC.getConnection()) {
            String sql = "INSERT INTO CatMovie(movieId, categoryId) VALUES (?,?);";
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setInt(1, movie.getId());
            ps.setInt(2, category.getId());
            ps.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Deletes a specific item from CatMovie table based on a movie ID and category ID
     * @param movie
     * @param category
     */
    public void deleteOneCatMovie(Movie movie, Category category)
    {
        try(Connection connection = DC.getConnection()) {
            String sql = "DELETE FROM CatMovie WHERE movieId =(?) AND categoryId= (?);";
            PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setInt(1, movie.getId());
            ps.setInt(2, category.getId());
            ps.executeUpdate();
            } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Deletes all items referencing a specific movie in the CatMovie table
     * @param movie the movie to be removed
     */
    public void deleteMovieFromCatMovie(Movie movie)
    {
        try(Connection connection = DC.getConnection()) {
            String sql = "DELETE FROM CatMovie WHERE movieId =(?);";
            PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setInt(1, movie.getId());
            ps.executeUpdate();
            } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Deletes all items referencing a specific category in the CatMovie table
     * @param category to be removed
     */
    public void deleteCategoryFromCatMovie( Category category)
    {
        try(Connection connection = DC.getConnection()) {
            String sql = "DELETE FROM CatMovie WHERE categoryId= (?);";
            PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setInt(1, category.getId());
            ps.executeUpdate();
            } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Fetches all items in CatMovie table that belongs to a specific category and adds the movie IDs to a list
     * @param category - the category to search for
     * @return a list of IDs of the movies belonging to the category
     */
    public List<Integer> getMoviesFromCategory(Category category)
    {
        ArrayList<Integer> allMoviesFromCategory = new ArrayList<>();
        try(Connection c = DC.getConnection()){
            String sql = "SELECT * FROM CatMovie WHERE categoryId = (?)";
            PreparedStatement ps = c.prepareStatement(sql);
            ps.setInt(1, category.getId());
            ps.execute();
            ResultSet rs = ps.getResultSet();
            while(rs.next()) {
               int i = rs.getInt("movieId");
               allMoviesFromCategory.add(i);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return allMoviesFromCategory;
    }

    /**
     * Fetches all items in CatMovie table that belongs to a specific category and adds the category IDs to a list
     * @param movie - the movie to search for
     * @return a list of IDs of the categories the movie is on
     */
    public List<Integer> getCategoryIdsOfMovie(Movie movie)
    {
        ArrayList<Integer> allCategoryIdsFromMovie = new ArrayList<>();
        try(Connection c = DC.getConnection()){
            String sql = "SELECT * FROM CatMovie WHERE movieId = (?)";
            PreparedStatement ps = c.prepareStatement(sql);
            ps.setInt(1, movie.getId());
            ps.execute();
            ResultSet rs = ps.getResultSet();
            while(rs.next()) {
                int i = rs.getInt("categoryId");
                allCategoryIdsFromMovie.add(i);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return allCategoryIdsFromMovie;
    }

}
