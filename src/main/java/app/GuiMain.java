package app;

import javax.swing.SwingUtilities;
import app.ui.DonationUI;

public class GuiMain {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            DonationUI ui = new DonationUI();
            ui.setVisible(true);
        });
    }
}