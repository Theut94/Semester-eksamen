package dal;

import be.Category;
import be.Movie;

import java.io.IOException;
import java.sql.*;

public class CategoryDAO {
    private DatabaseConnector DC = new DatabaseConnector();

    public CategoryDAO() throws IOException {
    }

    public Category createCategory(String categoryName) {
        try (Connection connection = DC.getConnection()) {
            String sql = "INSERT INTO Category(categoryName) VALUES (?);";
            PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, categoryName);

            int affectedRows = ps.executeUpdate();
            if (affectedRows == 1) {
                ResultSet rs = ps.getGeneratedKeys();
                if (rs.next()) {
                    int id = rs.getInt(1);
                    Category category = new Category(categoryName, id);
                    return category;
                }
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return null;
    }



}
