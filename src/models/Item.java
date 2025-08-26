package models;

public class Item {
    private String name;
    private int quantity;
    private double price;

    // NAME
    public String getName() {
        return name;
    }

    public void setName(String name) {
        if (name == null || name.trim().isEmpty())
            throw new IllegalArgumentException("El nombre del item no puede ser nulo o vacío.");

        this.name = name;
    }

    // QUANTITY
    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        if (quantity < 0)
            throw new IllegalArgumentException("La cantidad no puede ser negativa.");

        this.quantity = quantity;
    }

    // PRICE
    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        if (price < 0)
            throw new IllegalArgumentException("El precio no puede ser negativo.");

        this.price = price;
    }
}