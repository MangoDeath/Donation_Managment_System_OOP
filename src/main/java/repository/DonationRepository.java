package repository;

import config.DB;
import model.DonationDetails;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;

public class DonationRepository {

    public void createDonation(int donorId, int campaignId, double amount) throws SQLException {
        String sql = "INSERT INTO donation(donor_id, campaign_id, amount) VALUES (?, ?, ?)";

        try (Connection con = DB.getInstance().getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, donorId);
            ps.setInt(2, campaignId);
            ps.setDouble(3, amount);
            ps.executeUpdate();
        }
    }

    public DonationDetails getDonationDetails(int donationId) throws SQLException {
        String sql = "SELECT d.donation_id, d.amount, d.donation_date, " +
                "dr.full_name, dr.email, " +
                "c.title AS campaign_title, " +
                "ch.charity_name, " +
                "cat.category_name AS category_name " +
                "FROM donation d " +
                "JOIN donor dr ON d.donor_id = dr.donor_id " +
                "JOIN campaign c ON d.campaign_id = c.campaign_id " +
                "LEFT JOIN charity ch ON c.charity_id = ch.charity_id " +
                "LEFT JOIN category cat ON c.category_id = cat.category_id " +
                "WHERE d.donation_id = ?";

        try (Connection con = DB.getInstance().getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, donationId);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                Timestamp timestamp = rs.getTimestamp("donation_date");
                LocalDateTime donationDate = timestamp != null ? timestamp.toLocalDateTime() : null;
                return new DonationDetails(
                        rs.getInt("donation_id"),
                        rs.getString("full_name"),
                        rs.getString("email"),
                        rs.getString("campaign_title"),
                        rs.getString("charity_name"),
                        rs.getString("category_name"),
                        rs.getDouble("amount"),
                        donationDate
                );
            }
        }
        return null;
    }
}
