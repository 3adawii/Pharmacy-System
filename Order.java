public class Order {
    private Drug drug;
    private int quantity;
    private double totalPrice;

    public Order(Drug drug, int quantity, double totalPrice) {
        this.drug = drug;
        this.quantity = quantity;
        this.totalPrice = totalPrice;
    }

    public double getTotalPrice() {
        return totalPrice;
    }
}