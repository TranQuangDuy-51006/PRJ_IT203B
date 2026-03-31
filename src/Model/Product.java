package Model;

public class Product {

    private int id;
    private String name;
    private String brand;
    private String storage;
    private String color;
    private double price;
    private int stock;
    private String description;
    private int categoryId;

    // ===== CONSTRUCTOR =====
    public Product() {}

    public Product(int id, String name, String brand, String storage, String color,
                   double price, int stock, String description, int categoryId) {
        this.id = id;
        this.name = name;
        this.brand = brand;
        this.storage = storage;
        this.color = color;
        this.price = price;
        this.stock = stock;
        this.description = description;
        this.categoryId = categoryId;
    }

    // ===== GETTER =====
    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getBrand() {
        return brand;
    }

    public String getStorage() {
        return storage;
    }

    public String getColor() {
        return color;
    }

    public double getPrice() {
        return price;
    }

    public int getStock() {
        return stock;
    }

    public String getDescription() {
        return description;
    }

    public int getCategoryId() {
        return categoryId;
    }

    // ===== SETTER =====
    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public void setStorage(String storage) {
        this.storage = storage;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }
}