package Model;

public class OrderDetail {

    private int orderId;
    private int productId;
    private String productName; // ⭐ thêm cho UI
    private int quantity;
    private double price;

    // =========================
    // CONSTRUCTOR RỖNG (QUAN TRỌNG)
    // =========================
    public OrderDetail() {
    }

    // =========================
    // FULL CONSTRUCTOR
    // =========================
    public OrderDetail(int orderId, int productId, int quantity, double price) {
        this.orderId = orderId;
        this.productId = productId;
        this.quantity = quantity;
        this.price = price;
    }

    public OrderDetail(int orderId, int productId, String productName, int quantity, double price) {
        this.orderId = orderId;
        this.productId = productId;
        this.productName = productName;
        this.quantity = quantity;
        this.price = price;
    }

    // =========================
    // GETTER SETTER
    // =========================
    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    // =========================
    // HELPER METHOD (RẤT HỮU ÍCH)
    // =========================
    public double getSubTotal() {
        return this.quantity * this.price;
    }
}