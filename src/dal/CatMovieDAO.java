package dal;

import be.Category;
import be.Movie;
import bll.util.URLConverter;
import gui.MainSceneModel;

import java.io.IOException;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class CatMovieDAO
{
    private DatabaseConnector DC = new DatabaseConnector();
    private MovieDAO movieDAO = new MovieDAO();

    public CatMovieDAO() throws IOException
    {
    }
    public void createCatMovie(Movie movie, Category category)
    {

        try(Connection connection = DC.getConnection()) {
            String sql = "INSERT INTO CatMovie(movieId, categoryId) VALUES (?,?);";
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
            String sql = "DELETE FROM CatMovie WHERE movieId =(?) AND categoryId= (?);";
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
            String sql = "DELETE FROM CatMovie WHERE movieId =(?);";
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
            String sql = "DELETE FROM CatMovie WHERE categoryId= (?);";
            PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setInt(1, category.getId());
            ps.executeUpdate();
            } catch (SQLException e) {
            e.printStackTrace();
        }
    }

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


}
