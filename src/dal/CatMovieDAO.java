package dal;

import be.Category;
import be.Movie;
import bll.util.URLConverter;

import java.io.IOException;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class CatMovieDAO
{
    private DatabaseConnector DC = new DatabaseConnector();

    public CatMovieDAO() throws IOException
    {
    }
    public void createCatMovie(Movie movie, Category category)
    {

        try(Connection connection = DC.getConnection()) {
            String sql = "INSERT INTO catMovie(movieId, categoryId) VALUES (?,?);";
            PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setInt(1, movie.getId());
            ps.setInt(2, category.getId());

            } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deleteOneCatMovie(Movie movie, Category category)
    {
        try(Connection connection = DC.getConnection()) {
            String sql = "DELETE FROM catMovie WHERE movieId =(?) AND categoryId= (?);";
            PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setInt(1, movie.getId());
            ps.setInt(2, category.getId());
            ps.executeUpdate();
            } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deleteMovieFromCatMovie(Movie movie)
    {
        try(Connection connection = DC.getConnection()) {
            String sql = "DELETE FROM catMovie WHERE movieId =(?);";
            PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setInt(1, movie.getId());
            ps.executeUpdate();
            } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deleteCategoryFromCatMovie( Category category)
    {
        try(Connection connection = DC.getConnection()) {
            String sql = "DELETE FROM catMovie WHERE categoryId= (?);";
            PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setInt(1, category.getId());
            ps.executeUpdate();
            } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Movie> getMoviesFromCategory(Category category)
    {
        ArrayList<Movie> allMoviesFromCategory = new ArrayList<>();
        try(Connection c = DC.getConnection()){
            String sql = "SELECT * FROM Movie WHERE categoryId = (?)";
            PreparedStatement ps = c.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setInt(1, category.getId());
            ResultSet rs = ps.getGeneratedKeys();
            while(rs.next()) {
                int movieId = rs.getInt("movieId");
                String movieName = rs.getString("movieName");
                float ratingIMDB = rs.getFloat("ratingIMDB");
                float ratingPersonal = rs.getFloat("ratingPersonal");
                String filelink = rs.getString("filelink");
                LocalDate lastview = LocalDate.parse(rs.getString("lastview"));
                Movie movie = new Movie(movieId, movieName, ratingIMDB, ratingPersonal, filelink, lastview);
                allMoviesFromCategory.add(movie);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return allMoviesFromCategory;
    }


}
