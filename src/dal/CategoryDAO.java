package dal;

import be.Category;
import be.Movie;
import com.microsoft.sqlserver.jdbc.SQLServerException;

import java.io.IOException;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class CategoryDAO {
    private DatabaseConnector DC = new DatabaseConnector();

    public CategoryDAO() throws IOException {
    }

    public void createCategory(Category category) {
        try (Connection connection = DC.getConnection()) {
            String sql = "INSERT INTO Category(categoryName) VALUES (?);";
            PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, category.getCategoryName());
            int affectedRows = ps.executeUpdate();
            if (affectedRows == 1) {
                ResultSet rs = ps.getGeneratedKeys();
                if (rs.next()) {
                    int id = rs.getInt(1);
                    category.setId(id);

                }
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

    }

    public List<Category> getAllCategories() {
        ArrayList<Category> allCategories = new ArrayList<>();
        try(Connection c = DC.getConnection()){
            String sql = "SELECT * FROM Category";
            Statement s = c.createStatement();
            ResultSet rs = s.executeQuery(sql);
            while(rs.next()) {
                String categoryName = rs.getString("categoryName");
                Category cat = new Category(categoryName);
                cat.setId(rs.getInt("categoryId"));
                allCategories.add(cat);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return allCategories;
    }

    public void updateCategory(Category category)
    {
        String sql = "UPDATE Category SET categoryName= (?) WHERE categoryId = (?);";
        try(Connection connection = DC.getConnection())
        {
            PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, category.getCategoryName());
            ps.setInt(2, category.getId());
            ps.executeUpdate();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public void deleteCategory(Category category)
    {
        String sql = "DELETE FROM Category WHERE categoryId = (?);";
        try(Connection connection = DC.getConnection())
        {
            PreparedStatement ps = connection.prepareStatement(sql,Statement.RETURN_GENERATED_KEYS);
            ps.setInt(1, category.getId());
            ps.executeUpdate();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

}
