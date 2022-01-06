package dal;

import be.Movie;

import java.awt.*;
import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;

public class MovieDAO
{
    DatabaseConnector DC = new DatabaseConnector();

    public MovieDAO() throws IOException
    {
    }

    public Movie createMovie(String movieName, float pRating, float IMDBRating, ArrayList categories) throws SQLException {
        Connection connection = DC.getConnection();
        String sql = "INSERT INTO Movie(movieName, pRating, IMDBRating) VALUES (?,?,?);";
        PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
        ps.setString(1, movieName);
        ps.setFloat(2, pRating);
        ps.setFloat(3, IMDBRating);

        int affectedRows = ps.executeUpdate();
        if(affectedRows == 1)
        {
            ResultSet rs = ps.getGeneratedKeys();
            if(rs.next())
            {
                int id = rs.getInt(1);
                Movie movie = new Movie(id, movieName, IMDBRating, pRating, categories);
                return movie;
            }

        }
        return null;
    }
}
