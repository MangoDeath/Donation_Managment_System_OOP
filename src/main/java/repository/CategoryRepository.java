package repository;

import config.DB;
import model.Category;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class CategoryRepository {

    public void createCategory(String category_name, String description) throws SQLException {
        String sql = "INSERT INTO category(category_name, description) VALUES (?, ?)";

        try (Connection con = DB.getInstance().getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, category_name);
            ps.setString(2, description);
            ps.executeUpdate();
        }
    }

    public List<Category> findAllCategories() throws SQLException {
        String sql = "SELECT category_id, category_name, description FROM category ORDER BY category_name";
        List<Category> categories = new ArrayList<>();

        try (Connection con = DB.getInstance().getConnection();
             Statement st = con.createStatement();
             ResultSet rs = st.executeQuery(sql)) {

            while (rs.next()) {
                categories.add(new Category(
                        rs.getInt("category_id"),
                        rs.getString("category_name"),
                        rs.getString("description")
                ));
            }
        }
        return categories;
    }
}
