package app;

import controller.DonationController;
import repository.CampaignRepository;
import repository.DonorRepository;
import repository.PaymentRepository;
import java.sql.SQLException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        DonationController donationController = new DonationController();
        CampaignRepository campaignRepo = new CampaignRepository();
        DonorRepository donorRepo = new DonorRepository();
        PaymentRepository paymentRepo = new PaymentRepository();

        while (true) {
            System.out.println("\n1) Show campaigns");
            System.out.println("2) Make donation");

            System.out.println("3) Register donor");
            System.out.println("4) Show donors");
            System.out.println("5) Record payment");
            System.out.println("6) Show payments");
            System.out.println("0) Exit");

            System.out.print("Choose: ");

            int choice;

            choice = Integer.parseInt(sc.nextLine());


            if (choice == 0) break;

            if (choice == 1) {
                try {
                    campaignRepo.showAllCampaigns();
                } catch (SQLException e) {
                    System.out.println("Error showing campaigns: " + e.getMessage());
                }
            }

            if (choice == 2) {
                try {
                    System.out.print("Donor ID: ");
                    int donorId = Integer.parseInt(sc.nextLine());

                    System.out.print("Campaign ID: ");
                    int campaignId = Integer.parseInt(sc.nextLine());

                    System.out.print("Amount: ");
                    double amount = Double.parseDouble(sc.nextLine());

                    String result = donationController.makeDonation(donorId, campaignId, amount);
                    System.out.println(result);
                } catch (NumberFormatException e) {
                    System.out.println("Invalid input. Please enter numbers only.");
                } catch (SQLException e) {
                    System.out.println("Error making donation: " + e.getMessage());
                }
            }
            if (choice == 3) {
                try {
                    System.out.print("Donor ID: ");
                    int donorId = sc.nextInt();
                    sc.nextLine();
                    System.out.print("Full Name: ");
                    String fullName = sc.nextLine();
                    System.out.print("Email: ");
                    String email = sc.nextLine();
                    System.out.print("Address: ");
                    String address = sc.nextLine();
                    System.out.print("Phone: ");
                    String phone = sc.nextLine();
                    donorRepo.createDonor(donorId, fullName, email, address, phone);
                    System.out.println("Donor registered successfully!");
                } catch (SQLException e) {
                    System.out.println("Error registering donor: " + e.getMessage());
                }
            }

            if (choice == 4) {
                try {
                    donorRepo.showAllDonors();
                } catch (SQLException e) {
                    System.out.println("Error showing donors: " + e.getMessage());
                }
            }

            if (choice == 5) {
                try {
                    System.out.print("Donation ID: ");
                    int donationId = Integer.parseInt(sc.nextLine());
                    System.out.print("Method: ");
                    String method = sc.nextLine();
                    System.out.print("Status: ");
                    String status = sc.nextLine();
                    paymentRepo.createPayment(donationId, method, status);
                    System.out.println("Payment record successful!");
                } catch (SQLException e) {
                    System.out.println("Error recording payment: " + e.getMessage());
                }
            }

            if (choice == 6) {
                try {
                    paymentRepo.showAllPayments();
                } catch (SQLException e) {
                    System.out.println("Error showing payments: " + e.getMessage());
                }
            }

        }
        sc.close();

    }
<<<<<<< HEAD
}
=======
}
>>>>>>> 73adf4649f836c3048195d492fad4eb68249563f
