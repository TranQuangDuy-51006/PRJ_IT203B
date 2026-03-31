package Model;

import java.sql.Timestamp;

public class FlashSale {

    private int id;
    private int productId;
    private double discountPercent;
    private Timestamp startTime;
    private Timestamp endTime;

    public FlashSale() {
    }

    public FlashSale(int id, int productId, double discountPercent, Timestamp startTime, Timestamp endTime) {
        this.id = id;
        this.productId = productId;
        this.discountPercent = discountPercent;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    public FlashSale(int productId, double discountPercent, Timestamp startTime, Timestamp endTime) {
        this.productId = productId;
        this.discountPercent = discountPercent;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    // ================= GETTER / SETTER =================

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public double getDiscountPercent() {
        return discountPercent;
    }

    public void setDiscountPercent(double discountPercent) {
        this.discountPercent = discountPercent;
    }

    public Timestamp getStartTime() {
        return startTime;
    }

    public void setStartTime(Timestamp startTime) {
        this.startTime = startTime;
    }

    public Timestamp getEndTime() {
        return endTime;
    }

    public void setEndTime(Timestamp endTime) {
        this.endTime = endTime;
    }
}
