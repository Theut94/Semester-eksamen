package dal;

import be.Movie;

import java.io.IOException;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class MovieDAO
{
    DatabaseConnector DC = new DatabaseConnector();

    public MovieDAO() throws IOException
    {
    }

    public Movie createMovie(String movieName, float ratingPersonal, float ratingIMDB, String filelink, LocalDate lastview) throws SQLException {
        Connection connection = DC.getConnection();
        String sql = "INSERT INTO Movie(movieName, ratingPersonal, ratingIMDB, filelink, lastview) VALUES (?,?,?,?,?);";
        PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
        ps.setString(1, movieName);
        ps.setFloat(2, ratingPersonal);
        ps.setFloat(3, ratingIMDB);
        ps.setString(4, filelink);
        ps.setString(5, lastview.toString());

        int affectedRows = ps.executeUpdate();
        if(affectedRows == 1)
        {
            ResultSet rs = ps.getGeneratedKeys();
            if(rs.next())
            {
                int id = rs.getInt(1);
                Movie movie = new Movie(id, movieName, ratingIMDB, ratingPersonal, filelink, lastview);
                return movie;
            }

        }
        return null;
    }

    public List<Movie> getAllMovies() {
        ArrayList<Movie> allMovies = new ArrayList<>();
        try(Connection c = DC.getConnection()){
            String sql = "SELECT * FROM Movie";
            Statement s = c.createStatement();
            ResultSet rs = s.executeQuery(sql);
            while(rs.next()) {
                int movieId = rs.getInt("movieId");
                String movieName = rs.getString("movieName");
                float ratingIMDB = rs.getFloat("ratingIMDB");
                float ratingPersonal = rs.getFloat("ratingPersonal");
                String filelink = rs.getString("filelink");
                LocalDate lastview = LocalDate.parse(rs.getString("lastview"));
                Movie movie = new Movie(movieId, movieName, ratingIMDB, ratingPersonal, filelink, lastview);
                allMovies.add(movie);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return allMovies;
    }
}
