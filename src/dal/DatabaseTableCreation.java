package dal;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class DatabaseTableCreation {

    private DatabaseConnector dbc = new DatabaseConnector();

    public DatabaseTableCreation() throws IOException {
    }

    public static void main(String[] args) throws IOException {
        DatabaseTableCreation dtc = new DatabaseTableCreation();
        dtc.catMovieTableCreation();
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

    // DONT TOUCH THIS METHOD IT IS DANGEROUS. WILL WIPE ENTIRE DATABASE!
    public void deleteAllTables()
    {
        try (Connection c = dbc.getConnection()) {

            String sql1 = "DELETE FROM dbo.CatMovie";
            String sql2 = "DELETE FROM dbo.Category";
            String sql3 = "DELETE FROM dbo.Movie";

            PreparedStatement ps1 = c.prepareStatement(sql1);
            ps1.execute();
            PreparedStatement ps2 = c.prepareStatement(sql2);
            ps2.execute();
            PreparedStatement ps3 = c.prepareStatement(sql3);
            ps3.execute();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
}
