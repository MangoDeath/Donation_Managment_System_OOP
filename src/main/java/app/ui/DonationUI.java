package app.ui;

import controller.DonationController;
import repository.CampaignRepository;
import repository.DonorRepository;
import repository.PaymentRepository;

import javax.swing.*;
import java.awt.*;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.sql.SQLException;

public class DonationUI extends JFrame {

    private final DonationController donationController = new DonationController();
    private final CampaignRepository campaignRepo = new CampaignRepository();
    private final DonorRepository donorRepo = new DonorRepository();
    private final PaymentRepository paymentRepo = new PaymentRepository();

    private final JTextArea outputArea = new JTextArea();

    public DonationUI() {
        setTitle("Donation Management - Simple UI");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 500);
        setLocationRelativeTo(null);

        initLayout();
    }

    private void initLayout() {
        JPanel left = new JPanel();
        left.setLayout(new GridLayout(0, 1, 6, 6));
        left.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JButton btnShowCampaigns = new JButton("Show campaigns");
        JButton btnMakeDonation = new JButton("Make donation");
        JButton btnRegisterDonor = new JButton("Register donor");
        JButton btnShowDonors = new JButton("Show donors");
        JButton btnRecordPayment = new JButton("Record payment");
        JButton btnShowPayments = new JButton("Show payments");
        JButton btnExit = new JButton("Exit");

        btnShowCampaigns.addActionListener(e -> showCampaigns());
        btnMakeDonation.addActionListener(e -> makeDonation());
        btnRegisterDonor.addActionListener(e -> registerDonor());
        btnShowDonors.addActionListener(e -> showDonors());
        btnRecordPayment.addActionListener(e -> recordPayment());
        btnShowPayments.addActionListener(e -> showPayments());
        btnExit.addActionListener(e -> System.exit(0));

        left.add(btnShowCampaigns);
        left.add(btnMakeDonation);
        left.add(btnRegisterDonor);
        left.add(btnShowDonors);
        left.add(btnRecordPayment);
        left.add(btnShowPayments);
        left.add(btnExit);

        outputArea.setEditable(false);
        outputArea.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 12));
        JScrollPane scroll = new JScrollPane(outputArea);

        getContentPane().setLayout(new BorderLayout());
        getContentPane().add(left, BorderLayout.WEST);
        getContentPane().add(scroll, BorderLayout.CENTER);
    }

    private void showCampaigns() {
        String out = captureOutput(() -> {
            try {
                campaignRepo.showAllCampaigns();
            } catch (SQLException e) {
                System.out.println("Error showing campaigns: " + e.getMessage());
            }
        });
        outputArea.setText(out);
    }

    private void showDonors() {
        String out = captureOutput(() -> {
            try {
                donorRepo.showAllDonors();
            } catch (SQLException e) {
                System.out.println("Error showing donors: " + e.getMessage());
            }
        });
        outputArea.setText(out);
    }

    private void showPayments() {
        String out = captureOutput(() -> {
            try {
                paymentRepo.showAllPayments();
            } catch (SQLException e) {
                System.out.println("Error showing payments: " + e.getMessage());
            }
        });
        outputArea.setText(out);
    }

    private void makeDonation() {
        try {
            String donorIdStr = JOptionPane.showInputDialog(this, "Donor ID:");
            if (donorIdStr == null) return;
            int donorId = Integer.parseInt(donorIdStr.trim());

            String campaignIdStr = JOptionPane.showInputDialog(this, "Campaign ID:");
            if (campaignIdStr == null) return;
            int campaignId = Integer.parseInt(campaignIdStr.trim());

            String amountStr = JOptionPane.showInputDialog(this, "Amount:");
            if (amountStr == null) return;
            double amount = Double.parseDouble(amountStr.trim());

            String result = donationController.makeDonation(donorId, campaignId, amount);
            JOptionPane.showMessageDialog(this, result);
            appendOutput("Make donation: " + result);
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Invalid number format", "Error", JOptionPane.ERROR_MESSAGE);
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Database error: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void registerDonor() {
        try {
            String donorIdStr = JOptionPane.showInputDialog(this, "Donor ID:");
            if (donorIdStr == null) return;
            int donorId = Integer.parseInt(donorIdStr.trim());

            String fullName = JOptionPane.showInputDialog(this, "Full name:");
            if (fullName == null) return;

            String email = JOptionPane.showInputDialog(this, "Email:");
            if (email == null) return;

            String address = JOptionPane.showInputDialog(this, "Address:");
            if (address == null) return;

            String phone = JOptionPane.showInputDialog(this, "Phone:");
            if (phone == null) return;

            donorRepo.createDonor(donorId, fullName, email, address, phone);
            JOptionPane.showMessageDialog(this, "Donor registered successfully!");
            appendOutput("Registered donor: " + donorId + " | " + fullName);
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Invalid donor ID", "Error", JOptionPane.ERROR_MESSAGE);
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Database error: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void recordPayment() {
        try {
            String donationIdStr = JOptionPane.showInputDialog(this, "Donation ID:");
            if (donationIdStr == null) return;
            int donationId = Integer.parseInt(donationIdStr.trim());

            String method = JOptionPane.showInputDialog(this, "Method:");
            if (method == null) return;

            String status = JOptionPane.showInputDialog(this, "Status:");
            if (status == null) return;

            paymentRepo.createPayment(donationId, method, status);
            JOptionPane.showMessageDialog(this, "Payment recorded successfully!");
            appendOutput("Recorded payment for donation: " + donationId);
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Invalid donation ID", "Error", JOptionPane.ERROR_MESSAGE);
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Database error: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private String captureOutput(Runnable runnable) {
        PrintStream originalOut = System.out;
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        try (PrintStream ps = new PrintStream(baos)) {
            System.setOut(ps);
            runnable.run();
            ps.flush();
        } finally {
            System.setOut(originalOut);
        }
        return baos.toString();
    }

    private void appendOutput(String text) {
        outputArea.append(text + System.lineSeparator());
    }
}