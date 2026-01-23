package repository;

import config.DB;
import java.sql.*;

public class DonorRepository {

    public void createDonor(int donorId, String fullName, String email, String address, String phone) throws SQLException {
        String sql = "INSERT INTO donor(donor_id, full_name, email, address, phone) VALUES (?, ?, ?, ?, ?)";

        try (Connection con = DB.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, donorId);
            ps.setString(2, fullName);
            ps.setString(3, email);
            ps.setString(4, address);
            ps.setString(5, phone);
            ps.executeUpdate();
        }
    }

    public void showAllDonors() throws SQLException {
        String sql = "SELECT donor_id, full_name, email, address FROM donor";

        try (Connection con = DB.getConnection();
             Statement st = con.createStatement();
             ResultSet rs = st.executeQuery(sql)) {

            while (rs.next()) {
                System.out.println(
                        rs.getInt("donor_id") + " | " +
                                rs.getString("full_name") + " | " +
                                rs.getString("email")+ " | " + rs.getString("address")
                );
            }
        }
    }
}
