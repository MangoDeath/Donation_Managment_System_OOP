package repository;

import config.DB;
import java.sql.*;

public class PaymentRepository {

    public void createPayment(int donationId, String method, String status) throws SQLException {
        String sql = "INSERT INTO payment(donation_id, method, status) VALUES (?, ?, ?)";

        try (Connection con = DB.getInstance().getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, donationId);
            ps.setString(2, method);
            ps.setString(3, status);
            ps.executeUpdate();
        }
    }

    public void showAllPayments() throws SQLException {
        String sql = "SELECT payment_id, donation_id, method, status, payment_date FROM payment";

        try (Connection con = DB.getInstance().getConnection();
             Statement st = con.createStatement();
             ResultSet rs = st.executeQuery(sql)) {

            while (rs.next()) {
                System.out.println(
                        rs.getInt("payment_id") + " | donation: " +
                                rs.getInt("donation_id") + " | method: " +
                                rs.getString("method") + " | status: " +
                                rs.getString("status") + " | date: " +
                                rs.getDate("payment_date")
                );
            }
        }
    }
}
