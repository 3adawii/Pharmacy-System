import javax.swing.*;
import java.awt.*;

public class Main {
    public static void main(String[] args) {
        javax.swing.SwingUtilities.invokeLater(() -> createAndShowGUI());
    }

    private static void createAndShowGUI() {
        JFrame frame = new JFrame("Pharmacy System");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        frame.setLayout(new BorderLayout());


        JTextArea outputTextArea = new JTextArea();
        outputTextArea.setEditable(false);
        frame.add(new JScrollPane(outputTextArea), BorderLayout.CENTER);

        PharmacyGUI pharmacyGUI = new PharmacyGUI(outputTextArea);

        // Create a JPanel for displaying drug information
        JPanel drugsInfoPanel = new JPanel();
        drugsInfoPanel.setPreferredSize(new Dimension(400, Integer.MAX_VALUE));
        pharmacyGUI.setDrugsInfoPanel(drugsInfoPanel);

        // Create buttons and add action listeners
        JButton addDrugButton = new JButton("Add Drug");
        JButton removeDrugButton = new JButton("Remove Drug");
        JButton placeOrderButton = new JButton("Place Order");
        JButton calculateTotalSalesButton = new JButton("Calculate Total Sales");
        JButton exitButton = new JButton("Exit");

        addDrugButton.addActionListener(e -> pharmacyGUI.addDrug());
        removeDrugButton.addActionListener(e -> pharmacyGUI.removeDrug());
        placeOrderButton.addActionListener(e -> pharmacyGUI.placeOrder());
        calculateTotalSalesButton.addActionListener(e -> pharmacyGUI.calculateTotalSales());
        exitButton.addActionListener(e -> System.exit(0)); // Exit the application

        // Create a panel for buttons
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(5, 1)); // Increase the row count to accommodate the new button
        buttonPanel.add(addDrugButton);
        buttonPanel.add(removeDrugButton);
        buttonPanel.add(placeOrderButton);
        buttonPanel.add(calculateTotalSalesButton);
        buttonPanel.add(exitButton);

        frame.add(buttonPanel, BorderLayout.WEST); // Adjust the position as needed
        frame.add(drugsInfoPanel, BorderLayout.EAST); // Adjust the position as needed

        frame.setVisible(true);
    }

}
