package repository;

import java.sql.*;

public class DonationRepository {

    public void createDonation(int donorId, int campaignId, double amount) throws SQLException {
        String sql = "INSERT INTO donation(donor_id, campaign_id, amount) VALUES (?, ?, ?)";

        try (Connection con = DB.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, donorId);
            ps.setInt(2, campaignId);
            ps.setDouble(3, amount);
            ps.executeUpdate();
        }
    }
}
