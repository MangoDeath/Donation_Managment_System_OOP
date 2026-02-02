package repository;

import config.DB;
import java.sql.*;

public class CampaignRepository {

    public void showAllCampaigns() throws SQLException {
        String sql = "SELECT c.campaign_id, c.title, c.goal_amount, c.current_amount, c.status, cat.category_name AS category_name " +
                "FROM campaign c " +
                "LEFT JOIN category cat ON c.category_id = cat.category_id";

        try (Connection con = DB.getInstance().getConnection();
             Statement st = con.createStatement();
             ResultSet rs = st.executeQuery(sql)) {

            while (rs.next()) {
                System.out.println(
                        rs.getInt("campaign_id") + " | " +
                                rs.getString("title") + " | goal: " +
                                rs.getDouble("goal_amount") + " | current: " +
                                rs.getDouble("current_amount") + " | status: " +
                                rs.getString("status") + " | category: " +
                                rs.getString("category_name")
                );
            }
        }
    }

    public String getCampaignStatus(int campaignId) throws SQLException {
        String sql = "SELECT status FROM campaign WHERE campaign_id = ?";

        try (Connection con = DB.getInstance().getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, campaignId);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return rs.getString("status");
            }
        }
        return null;
    }

    public void updateCampaign(int campaignId, double amount) throws SQLException {
        String sql = "UPDATE campaign " +
                "SET current_amount = current_amount + ?, " +
                "status = CASE WHEN current_amount + ? >= goal_amount THEN 'CLOSED' ELSE 'OPEN' END " +
                "WHERE campaign_id = ?";

        try (Connection con = DB.getInstance().getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setDouble(1, amount);
            ps.setDouble(2, amount);
            ps.setInt(3, campaignId);
            ps.executeUpdate();
        }
    }
    public void updateCampaignCategory(int campaignId, int categoryId) throws SQLException {
        String sql = "UPDATE campaign SET category_id = ? WHERE campaign_id = ?";

        try (Connection con = DB.getInstance().getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, categoryId);
            ps.setInt(2, campaignId);
            ps.executeUpdate();
        }
    }
}

