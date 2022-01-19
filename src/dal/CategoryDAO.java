package dal;

import be.Category;

import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;

public class CategoryDAO {
    private DatabaseConnector DC = new DatabaseConnector();

    public CategoryDAO() throws IOException {
    }

    /**
     * Creates a new category with the provided information in the Category table in the database,
     * then sets the ID of the category to match the ID given by the database
     * @param category - the category to be created
     */
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

    /**
     * Fetches a list of all categories from the database.
     * @return ArrayList of all categories.
     */
    public ArrayList<Category> getAllCategories() {
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

    /**
     * Updates the information of a specific category on the Category table, based on the category ID
     * @param category - category to be updated
     */
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

    /**
     * Deletes a specific category from the Category table based on the category ID
     * @param category - the category to be deleted
     */
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
