package dal;

import com.microsoft.sqlserver.jdbc.SQLServerDataSource;
import com.microsoft.sqlserver.jdbc.SQLServerException;

import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

public class DatabaseConnector
{
    private SQLServerDataSource dataSource;

    //Here we access the Database we've been using - we have been so kind to send the txt.document with the program so you can access it aswell :-)
    public DatabaseConnector() throws IOException
    {
        Properties props = new Properties();
        props.load(new FileReader("src/dal/DataAccess.txt"));
        dataSource = new SQLServerDataSource();
        dataSource.setDatabaseName(props.getProperty("database"));
        dataSource.setUser(props.getProperty("username"));
        dataSource.setPassword(props.getProperty("pw"));
        dataSource.setServerName(props.getProperty("server"));

    }

    public Connection getConnection() throws SQLException {

        return dataSource.getConnection();
    }


    public static void main(String[] args) throws IOException, SQLException {
        DatabaseConnector DC = new DatabaseConnector();
        Connection connection = DC.getConnection();
        System.out.println(!connection.isClosed());

    }
}
