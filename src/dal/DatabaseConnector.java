package dal;

import com.microsoft.sqlserver.jdbc.SQLServerDataSource;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

public class DatabaseConnector
{
    private SQLServerDataSource dataSource;

    public DatabaseConnector() throws IOException {
        Properties props = new Properties();
        props.load(new FileReader("src/dal/DataAccess.txt"));
        dataSource = new SQLServerDataSource();
        dataSource.setDatabaseName(props.getProperty("database"));
        dataSource.setUser(props.getProperty("username"));
        dataSource.setPassword(props.getProperty("pw"));
        dataSource.setServerName(props.getProperty("server"));
    }

    /**
     * @return connection to the database
     */
    public Connection getConnection() throws SQLException {
        return dataSource.getConnection();
    }

    public static void main(String[] args) throws IOException, SQLException {
        DatabaseConnector DC = new DatabaseConnector();
        Connection connection = DC.getConnection();
        System.out.println(!connection.isClosed());

    }
}