package dal;

import be.Movie;
import bll.util.URLConverter;
import com.microsoft.sqlserver.jdbc.SQLServerException;

import java.io.IOException;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class MovieDAO
{
    DatabaseConnector DC = new DatabaseConnector();

    public MovieDAO() throws IOException {
    }

    public void createMovie(Movie movie) {
        try(Connection connection = DC.getConnection()) {
            String sql = "INSERT INTO Movie(movieName, ratingPersonal, ratingIMDB, filelink, lastview) VALUES (?,?,?,?,?);";
            PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, movie.getMovieName());
            ps.setFloat(2, movie.getMoviePersonalRating());
            ps.setFloat(3, movie.getMovieIMDBRating());
            ps.setString(4, URLConverter.fileLinkToURI(movie.getMovieFilelink()));
            ps.setString(5, movie.getLastview().toString());
            int affectedRows = ps.executeUpdate();
            if (affectedRows == 1) {
                ResultSet rs = ps.getGeneratedKeys();
                if (rs.next()) {
                    int id = rs.getInt(1);
                    movie.setId(id);
                }
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public List<Movie> getAllMovies() {
        ArrayList<Movie> allMovies = new ArrayList<>();
        try(Connection c = DC.getConnection()){
            String sql = "SELECT * FROM Movie";
            Statement s = c.createStatement();
            ResultSet rs = s.executeQuery(sql);
            while(rs.next()) {
                String movieName = rs.getString("movieName");
                float ratingIMDB = rs.getFloat("ratingIMDB");
                float ratingPersonal = rs.getFloat("ratingPersonal");
                String filelink = rs.getString("filelink");
                LocalDate lastview = LocalDate.parse(rs.getString("lastview"));
                Movie movie = new Movie(movieName, ratingIMDB, ratingPersonal, filelink, lastview);
                movie.setId(rs.getInt("movieId"));
                allMovies.add(movie);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return allMovies;
    }

    public void updateMovie(Movie movie)
    {
        String sql = "UPDATE Movie SET movieName= (?), ratingIMDB=(?), ratingPersonal=(?), filelink=(?), lastView = (?) WHERE movieId = (?);";
        try(Connection connection = DC.getConnection())
        {
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, movie.getMovieName());
            statement.setFloat(2, movie.getMovieIMDBRating());
            statement.setFloat(3,movie.getMoviePersonalRating());
            statement.setString(4, movie.getMovieFilelink());
            statement.setString(5,movie.getLastview().toString());
            statement.setInt(6, movie.getId());
            statement.executeUpdate();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public void deleteMovie(Movie movie)
    {
        String sql = "DELETE FROM Movie WHERE movieId = (?);";
        try(Connection connection = DC.getConnection())
        {
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setInt(1, movie.getId());
            ps.execute();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public Movie getMovieFromId(int i) {
        try(Connection c = DC.getConnection()){
        String sql = "SELECT * FROM Movie WHERE movieId = (?)";
        PreparedStatement ps = c.prepareStatement(sql);
        ps.setInt(1, i);
        ps.execute();
        ResultSet rs = ps.getResultSet();
        while(rs.next()) {
            String movieName = rs.getString("movieName");
            float ratingIMDB = rs.getFloat("ratingIMDB");
            float ratingPersonal = rs.getFloat("ratingPersonal");
            String filelink = rs.getString("filelink");
            LocalDate lastview = LocalDate.parse(rs.getString("lastview"));
            Movie movie = new Movie(movieName, ratingIMDB, ratingPersonal, filelink, lastview);
            movie.setId(rs.getInt("movieId"));
            return movie;
        }
    } catch (SQLException throwables) {
        throwables.printStackTrace();
    }
        return null;
    }
}
