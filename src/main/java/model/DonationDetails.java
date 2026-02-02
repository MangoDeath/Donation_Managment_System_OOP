package model;

import java.time.LocalDateTime;

public class DonationDetails {
    private final int donationId;
    private final String donorName;
    private final String donorEmail;
    private final String campaignTitle;
    private final String charityName;
    private final String categoryName;
    private final double amount;
    private final LocalDateTime donationDate;

    public DonationDetails(int donationId,
                           String donorName,
                           String donorEmail,
                           String campaignTitle,
                           String charityName,
                           String categoryName,
                           double amount,
                           LocalDateTime donationDate) {
        this.donationId = donationId;
        this.donorName = donorName;
        this.donorEmail = donorEmail;
        this.campaignTitle = campaignTitle;
        this.charityName = charityName;
        this.categoryName = categoryName;
        this.amount = amount;
        this.donationDate = donationDate;
    }

    public int getDonationId() {
        return donationId;
    }

    public String getDonorName() {
        return donorName;
    }

    public String getDonorEmail() {
        return donorEmail;
    }

    public String getCampaignTitle() {
        return campaignTitle;
    }

    public String getCharityName() {
        return charityName;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public double getAmount() {
        return amount;
    }

    public LocalDateTime getDonationDate() {
        return donationDate;
    }

    @Override
    public String toString() {
        return "Donation " + donationId + " | donor: " + donorName + " (" + donorEmail + ")" +
                " | campaign: " + campaignTitle + " | charity: " + charityName +
                " | category: " + categoryName + " | amount: " + amount + " | date: " + donationDate;
    }
}
