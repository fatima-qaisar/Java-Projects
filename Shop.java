package sp25_bcs_037;
public class Shop {
    private final String id;
    private final double price;
    private boolean available;

    public Shop(String id, double price) {
        this.id = id;
        this.price = price;
        this.available = true;
    }

    public String getId() { return id; }
    public double getPrice() { return price; }
    public boolean isAvailable() { return available; }

    public boolean book() {
        if (!available) return false;
        available = false;
        return true;
    }

    public boolean cancel() {
        if (available) return false;
        available = true;
        return true;
    }

    public String summary() {
        return String.format("%s | price: %,.0f PKR | %s", id, price, (available ? "Available" : "Booked"));
    }
}
