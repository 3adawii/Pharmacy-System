import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class PharmacyGUI {
    private List<Order> orderList;
    private List<Drug> drugList;
    private JTextArea outputTextArea;
    private JPanel drugsInfoPanel; // Panel to display available drugs

    public PharmacyGUI(JTextArea outputTextArea) {
        this.outputTextArea = outputTextArea;
        orderList = new ArrayList<>();
        drugList = new ArrayList<>();
        drugsInfoPanel = new JPanel(); // Initialize the drugsInfoPanel
        drugsInfoPanel.setLayout(new BoxLayout(drugsInfoPanel, BoxLayout.Y_AXIS)); // Use BoxLayout for vertical alignment
    }
    public void addDrug() {
        if (drugList.size() >= 10) {
            JOptionPane.showMessageDialog(null, "Maximum number of drugs (10) reached. Please remove a drug before adding more.");
            return; // Exit the method to prevent further execution
        }

        int id;
        do {
            id = Integer.parseInt(JOptionPane.showInputDialog("Enter drug Id:"));
            if (isIdAlreadyUsed(id)) {
                JOptionPane.showMessageDialog(null, "ID already entered! Please enter a different ID.");
            }
        } while (isIdAlreadyUsed(id));

        int price = Integer.parseInt(JOptionPane.showInputDialog("Enter drug price:"));
        String category = (String) JOptionPane.showInputDialog(
                null, "Select drug category:",
                "Category", JOptionPane.QUESTION_MESSAGE, null,
                Drug.getCategoryOptions(), Drug.getCategoryOptions()[0]
        );
        int availableQuantity = Integer.parseInt(JOptionPane.showInputDialog("Enter drug quantity:"));

        Drug newDrug = new Drug(id, price, category, availableQuantity);
        drugList.add(newDrug);

        updateDrugsInfoPanel(); // Update the display of available drugs

        outputTextArea.append("Drug added successfully!\n");
    }

    public void removeDrug() {
        int removedId = Integer.parseInt(JOptionPane.showInputDialog("Enter the drug Id to remove:"));
        drugList.removeIf(drug -> drug.getId() == removedId);

        updateDrugsInfoPanel(); // Update the display of available drugs

        outputTextArea.append("Drug removed successfully!\n");
    }

    public void placeOrder() {
        int drugId = Integer.parseInt(JOptionPane.showInputDialog("Enter drug Id for the order:"));

        Drug selectedDrug = findDrugById(drugId);

        if (selectedDrug != null) {
            // Display the price for a single drug
            JOptionPane.showMessageDialog(null, "Price for a single drug (ID " + drugId + "): " + selectedDrug.getPrice());

            int availableQuantity = selectedDrug.getAvailableQuantity();
            int quantity;

            do {
                quantity = Integer.parseInt(JOptionPane.showInputDialog("Enter quantity for the order:"));

                if (quantity > availableQuantity) {
                    // Display a message about the maximum available quantity
                    JOptionPane.showMessageDialog(null,
                            "The available amount of drugs (ID " + drugId + ") is: " + availableQuantity,
                            "Insufficient Quantity", JOptionPane.WARNING_MESSAGE);
                }

            } while (quantity > availableQuantity);

            // Update the available quantity after placing the order
            selectedDrug.setAvailableQuantity(availableQuantity - quantity);

            // Check if the updated quantity is zero or less, remove the drug
            if (selectedDrug.getAvailableQuantity() <= 0) {
                drugList.remove(selectedDrug);
                JOptionPane.showMessageDialog(null, "Drug (ID " + drugId + ") removed as quantity is now zero.");
            }

            // Refresh the drugs information panel
            updateDrugsInfoPanel();

            double unitPrice = selectedDrug.getPrice();
            double totalPrice = quantity * selectedDrug.calculateFinalPrice();

            Order order = new Order(selectedDrug, quantity, totalPrice);
            orderList.add(order);

            outputTextArea.append("Order placed successfully!\n");
        } else {
            outputTextArea.append("Drug not found!\n");
        }
    }

    public void calculateTotalSales() {
        double totalSales = calculateTotalSalesValue();
        JOptionPane.showMessageDialog(null, "Total sales for the day: " + totalSales);
    }

    private double calculateTotalSalesValue() {
        double totalSales = 0.0;
        for (Order order : orderList) {
            totalSales += order.getTotalPrice();
        }
        return totalSales;
    }


    private Drug findDrugById(int id) {
        for (Drug drug : drugList) {
            if (drug.getId() == id) {
                return drug;
            }
        }
        return null;
    }

    private boolean isIdAlreadyUsed(int id) {
        for (Drug drug : drugList) {
            if (drug.getId() == id) {
                return true;
            }
        }
        return false;
    }
    private void updateDrugsInfoPanel() {
        drugsInfoPanel.removeAll(); // Clear existing components

        // Display header for available drugs information
        JLabel drugsInfoLabel = new JLabel("Available Drugs:");
        drugsInfoPanel.add(drugsInfoLabel);

        // Display information for each drug
        for (Drug drug : drugList) {
            JLabel drugLabel = new JLabel("ID: " + drug.getId() +
                    " | Type: " + drug.getCategory() +
                    " | Price: " + drug.getPrice() +
                    " | Quantity: " + drug.getAvailableQuantity());
            drugsInfoPanel.add(drugLabel);
        }

        // Update the GUI
        drugsInfoPanel.revalidate();
        drugsInfoPanel.repaint();
    }

    public void setDrugsInfoPanel(JPanel drugsInfoPanel) {
        this.drugsInfoPanel = drugsInfoPanel;
        updateDrugsInfoPanel(); // Update the display of available drugs
    }
}
