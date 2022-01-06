package dal;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class DatabaseTableCreation {

    private DatabaseConnector dbc = new DatabaseConnector();

    public DatabaseTableCreation() throws IOException {
    }

    private void movieTableCreation() {
        try (Connection c = dbc.getConnection()) {

            String sql = "CREATE TABLE Movie (" +
                    "movieId int IDENTITY (1,1) NOT NULL PRIMARY KEY," +
                    "movieName varchar (255) NOT NULL," +
                    "ratingIMDB float NOT NULL," +  
                    "ratingPersonal float NOT NULL," +
                    "filelink varchar (255) NOT NULL," +
                    "lastview varchar (255) NOT NULL)";

            PreparedStatement ps = c.prepareStatement(sql);
            ps.execute();
        }
        catch (Exception e) {
            e.printStackTrace();
    }}

    private void categoryTableCreation() {
        try (Connection c = dbc.getConnection()) {

            String sql = "CREATE TABLE Category (" +
                    "categoryId int IDENTITY (1,1) NOT NULL PRIMARY KEY," +
                    "categoryName varchar (255) NOT NULL)";

            PreparedStatement ps = c.prepareStatement(sql);
            ps.execute();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void catMovieTableCreation() {
        try (Connection c = dbc.getConnection()) {

            String sql = "CREATE TABLE CatMovie (" +
                    "Id int IDENTITY (1,1) NOT NULL PRIMARY KEY," +
                    "movieId int FOREIGN KEY REFERENCES Movie(movieId)," +
                    "categoryId int FOREIGN KEY REFERENCES Category(categoryId))";

            PreparedStatement ps = c.prepareStatement(sql);
            ps.execute();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws IOException {
        DatabaseTableCreation dtc = new DatabaseTableCreation();
        dtc.catMovieTableCreation();
    }

}
