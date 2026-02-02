package controller;

import repository.CampaignRepository;
import repository.DonationRepository;

import java.sql.SQLException;

public class DonationController {

    private final CampaignRepository campaignRepo = new CampaignRepository();
    private final DonationRepository donationRepo = new DonationRepository();

    public String makeDonation(int donorId, int campaignId, double amount) throws SQLException {
        if (amount <= 0) {
            return "Amount must be greater than 0";
        }

        String status = campaignRepo.getCampaignStatus(campaignId);
        if (status == null) {
            return "Campaign not found";
        }

        if ("CLOSED".equalsIgnoreCase(status)) {
            return "Campaign is CLOSED";
        }

        donationRepo.createDonation(donorId, campaignId, amount);
        campaignRepo.updateCampaign(campaignId, amount);

        return "Donation successful";
    }
}
