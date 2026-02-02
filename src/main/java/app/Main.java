package app;

import factory.PaymentProcessorFactory;
import model.Category;
import model.DonationDetails;
import payment.PaymentProcessor;
import repository.CampaignRepository;
import repository.CategoryRepository;
import repository.DonationRepository;
import repository.DonorRepository;
import repository.PaymentRepository;

import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        CampaignRepository campaignRepo = new CampaignRepository();
        DonorRepository donorRepo = new DonorRepository();
        PaymentRepository paymentRepo = new PaymentRepository();
        DonationRepository donationRepo = new DonationRepository();
        CategoryRepository categoryRepo = new CategoryRepository();

        PaymentProcessorFactory paymentFactory = new PaymentProcessorFactory();


        String currentRole = login(sc);
        if (currentRole == null) {
            System.out.println("Bye!");
            sc.close();
            return;
        }

        // main loop
        while (true) {
            printMenu();
            int choice = readInt(sc, "Choose: ");

            if (choice == 0) {
                break;
            } else if (choice == 1) {

                if (!canViewCampaigns(currentRole)) {
                    System.out.println("Access denied.");
                    continue;
                }
                try {
                    campaignRepo.showAllCampaigns();
                } catch (SQLException e) {
                    System.out.println("Error showing campaigns: " + e.getMessage());
                }

            } else if (choice == 2) {

                if (!canMakeDonation(currentRole)) {
                    System.out.println("Access denied.");
                    continue;
                }
                makeDonation(sc, campaignRepo, donationRepo);

            } else if (choice == 3) {

                if (!canRegisterDonor(currentRole)) {
                    System.out.println("Access denied.");
                    continue;
                }
                registerDonor(sc, donorRepo);

            } else if (choice == 4) {

                if (!canViewDonors(currentRole)) {
                    System.out.println("Access denied.");
                    continue;
                }
                try {
                    donorRepo.showAllDonors();
                } catch (SQLException e) {
                    System.out.println("Error showing donors: " + e.getMessage());
                }

            } else if (choice == 5) {

                if (!canRecordPayment(currentRole)) {
                    System.out.println("Access denied.");
                    continue;
                }
                recordPayment(sc, donationRepo, paymentRepo, paymentFactory);

            } else if (choice == 6) {

                if (!canViewPayments(currentRole)) {
                    System.out.println("Access denied.");
                    continue;
                }
                try {
                    paymentRepo.showAllPayments();
                } catch (SQLException e) {
                    System.out.println("Error showing payments: " + e.getMessage());
                }

            } else if (choice == 7) {

                if (!canViewCampaigns(currentRole)) {
                    System.out.println("Access denied.");
                    continue;
                }
                showCategories(categoryRepo);

            } else if (choice == 8) {

                if (!canManageCategories(currentRole)) {
                    System.out.println("Access denied.");
                    continue;
                }
                addCategory(sc, categoryRepo);

            } else if (choice == 9) {

                if (!canManageCategories(currentRole)) {
                    System.out.println("Access denied.");
                    continue;
                }
                assignCategoryToCampaign(sc, campaignRepo);

            } else if (choice == 10) {

                if (!canViewDetails(currentRole)) {
                    System.out.println("Access denied.");
                    continue;
                }
                showDonationDetails(sc, donationRepo);

            } else {
                System.out.println("Unknown option. Try again.");
            }
        }

        sc.close();
    }

    private static String login(Scanner sc) {
        while (true) {
            System.out.println("\n== Login ==");
            System.out.print("Username (or 'exit'): ");
            String username = sc.nextLine().trim();

            if (username.equalsIgnoreCase("exit")) {
                return null;
            }

            System.out.print("Password: ");
            String password = sc.nextLine().trim();


            if (username.equals("admin") && password.equals("admin")) {
                System.out.println("Welcome, admin (ADMIN)");
                return "ADMIN";
            } else if (username.equals("manager") && password.equals("manager")) {
                System.out.println("Welcome, manager (MANAGER)");
                return "MANAGER";
            } else if (username.equals("editor") && password.equals("editor")) {
                System.out.println("Welcome, editor (EDITOR)");
                return "EDITOR";
            } else if (username.equals("viewer") && password.equals("viewer")) {
                System.out.println("Welcome, viewer (VIEWER)");
                return "VIEWER";
            } else {
                System.out.println("Invalid credentials, try again.");
            }
        }
    }

    private static void printMenu() {
        System.out.println("\n1) Show campaigns");
        System.out.println("2) Make donation");
        System.out.println("3) Register donor");
        System.out.println("4) Show donors");
        System.out.println("5) Record payment");
        System.out.println("6) Show payments");
        System.out.println("7) Show categories");
        System.out.println("8) Add category");
        System.out.println("9) Assign category to campaign");
        System.out.println("10) Show full donation description");
        System.out.println("0) Exit");
    }

    private static int readInt(Scanner sc, String prompt) {
        while (true) {
            System.out.print(prompt);
            String s = sc.nextLine().trim();
            try {
                return Integer.parseInt(s);
            } catch (NumberFormatException e) {
                System.out.println("Please enter a valid integer.");
            }
        }
    }

    private static double readDouble(Scanner sc, String prompt) {
        while (true) {
            System.out.print(prompt);
            String s = sc.nextLine().trim();
            try {
                return Double.parseDouble(s);
            } catch (NumberFormatException e) {
                System.out.println("Please enter a valid number.");
            }
        }
    }

    private static String readText(Scanner sc, String prompt) {
        System.out.print(prompt);
        return sc.nextLine();
    }

    private static void makeDonation(Scanner sc, CampaignRepository campaignRepo, DonationRepository donationRepo) {
        try {
            int donorId = readInt(sc, "Donor ID: ");
            int campaignId = readInt(sc, "Campaign ID: ");
            double amount = readDouble(sc, "Amount: ");

            if (donorId <= 0 || campaignId <= 0) {
                System.out.println("Invalid IDs.");
                return;
            }

            if (amount <= 0) {
                System.out.println("Invalid data. Amount must be positive.");
                return;
            }

            String status = campaignRepo.getCampaignStatus(campaignId);
            if (status == null) {
                System.out.println("Campaign not found.");
                return;
            }
            if ("CLOSED".equalsIgnoreCase(status)) {
                System.out.println("Campaign is CLOSED.");
                return;
            }

            donationRepo.createDonation(donorId, campaignId, amount);
            campaignRepo.updateCampaign(campaignId, amount);
            System.out.println("Donation successful.");

        } catch (SQLException e) {
            System.out.println("Error making donation: " + e.getMessage());
        }
    }

    private static void registerDonor(Scanner sc, DonorRepository donorRepo) {
        try {
            int donorId = readInt(sc, "Donor ID: ");
            String fullName = readText(sc, "Full Name: ").trim();
            String email = readText(sc, "Email: ").trim();
            String address = readText(sc, "Address: ").trim();
            String phone = readText(sc, "Phone: ").trim();

            if (donorId <= 0) {
                System.out.println("Invalid donor ID.");
                return;
            }
            if (fullName.isEmpty() || email.isEmpty() || phone.isEmpty()) {
                System.out.println("Invalid data. Full name, email and phone are required.");
                return;
            }

            donorRepo.createDonor(donorId, fullName, email, address, phone);
            System.out.println("Donor registered successfully!");

        } catch (SQLException e) {
            System.out.println("Error registering donor: " + e.getMessage());
        }
    }

    private static void recordPayment(
            Scanner sc,
            DonationRepository donationRepo,
            PaymentRepository paymentRepo,
            PaymentProcessorFactory paymentFactory
    ) {
        try {
            int donationId = readInt(sc, "Donation ID: ");
            String method = readText(sc, "Method (CARD/TRANSFER/CASH): ").trim();

            if (donationId <= 0 || method.isEmpty()) {
                System.out.println("Invalid data.");
                return;
            }

            DonationDetails details = donationRepo.getDonationDetails(donationId);
            if (details == null) {
                System.out.println("Donation not found.");
                return;
            }

            PaymentProcessor processor = paymentFactory.createProcessor(method);
            String status = processor.process(details.getAmount());

            paymentRepo.createPayment(donationId, method.toUpperCase(), status);
            System.out.println("Payment recorded with status: " + status);

        } catch (SQLException e) {
            System.out.println("Error recording payment: " + e.getMessage());
        }
    }

    private static void showCategories(CategoryRepository categoryRepo) {
        try {
            List<Category> categories = categoryRepo.findAllCategories();
            if (categories.isEmpty()) {
                System.out.println("No categories available.");
                return;
            }
            for (Category c : categories) {
                System.out.println(c.getCategoryId() + " | " + c.getName() + " | " + c.getDescription());
            }
        } catch (SQLException e) {
            System.out.println("Error showing categories: " + e.getMessage());
        }
    }

    private static void addCategory(Scanner sc, CategoryRepository categoryRepo) {
        try {
            String name = readText(sc, "Category Name: ").trim();
            String description = readText(sc, "Description: ").trim();

            if (name.isEmpty()) {
                System.out.println("Category name is required.");
                return;
            }

            categoryRepo.createCategory(name, description);
            System.out.println("Category created successfully!");

        } catch (SQLException e) {
            System.out.println("Error creating category: " + e.getMessage());
        }
    }

    private static void assignCategoryToCampaign(Scanner sc, CampaignRepository campaignRepo) {
        try {
            int campaignId = readInt(sc, "Campaign ID: ");
            int categoryId = readInt(sc, "Category ID: ");

            if (campaignId <= 0 || categoryId <= 0) {
                System.out.println("Invalid IDs.");
                return;
            }

            campaignRepo.updateCampaignCategory(campaignId, categoryId);
            System.out.println("Campaign category updated successfully!");

        } catch (SQLException e) {
            System.out.println("Error updating campaign category: " + e.getMessage());
        }
    }

    private static void showDonationDetails(Scanner sc, DonationRepository donationRepo) {
        try {
            int donationId = readInt(sc, "Donation ID: ");
            if (donationId <= 0) {
                System.out.println("Invalid donation ID.");
                return;
            }

            DonationDetails details = donationRepo.getDonationDetails(donationId);
            if (details == null) {
                System.out.println("Donation not found.");
            } else {
                System.out.println(details);
            }

        } catch (SQLException e) {
            System.out.println("Error fetching donation details: " + e.getMessage());
        }
    }

    // ====== Access checks ======

    private static boolean canViewCampaigns(String role) {
        return role != null;
    }

    private static boolean canMakeDonation(String role) {
        return "ADMIN".equals(role) || "MANAGER".equals(role) || "EDITOR".equals(role);
    }

    private static boolean canRegisterDonor(String role) {
        return "ADMIN".equals(role) || "EDITOR".equals(role);
    }

    private static boolean canViewDonors(String role) {
        return role != null;
    }

    private static boolean canRecordPayment(String role) {
        return "ADMIN".equals(role) || "EDITOR".equals(role);
    }

    private static boolean canViewPayments(String role) {
        return role != null;
    }

    private static boolean canViewDetails(String role) {
        return role != null;
    }

    private static boolean canManageCategories(String role) {
        return "ADMIN".equals(role) || "MANAGER".equals(role);
    }
}
