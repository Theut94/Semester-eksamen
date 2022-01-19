package dal;

import be.Movie;
import bll.MovieManager;

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

    /**
     * Creates a new movie with the provided information in the Movie table in the database,
     * then sets the ID of the movie to match the ID given by the database
     * @param movie - the movie to be created
     */
    public void createMovie(Movie movie) {
        try(Connection connection = DC.getConnection()) {
            String sql = "INSERT INTO Movie(movieName, ratingPersonal, ratingIMDB, filelink, lastview) VALUES (?,?,?,?,?);";
            PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, movie.getMovieName());
            ps.setFloat(2, movie.getMoviePersonalRating());
            ps.setFloat(3, movie.getMovieIMDBRating());
            ps.setString(4, movie.getMovieFilelink());
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

    /**
     * Fetches all movies from the Movie table and adds them to a list
     * @return a list of all movies in the database
     */
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
                Movie movie = new Movie(movieName, ratingIMDB, filelink, lastview);
                movie.setId(rs.getInt("movieId"));
                movie.setMoviePersonalRating(ratingPersonal);
                MovieManager movieManager = new MovieManager();
                movie.setMovieCategories(movieManager.getCategoriesOfMovie(movie));
                allMovies.add(movie);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return allMovies;
    }

    /**
     * Updates the information of a specific movie in the Movie table, based on the movie's ID
     * @param movie - the movie to be updated
     */
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

    /**
     * Deletes a specific movie from the Movie table, based on the movie's ID
     * @param movie - the movie to be deleted
     */
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



}
