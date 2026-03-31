package Service;

import DAO.OrderDAO;
import DAO.OrderDAOImpl;
import Model.CartItem;
import Model.Order;
import Model.OrderDetail;

import java.util.List;

public class OrderService {

    private final OrderDAO dao = new OrderDAOImpl();

    // =========================
    // CHECKOUT
    // =========================
    public boolean checkout(int userId, List<CartItem> cart) {

        if (userId <= 0) {
            throw new RuntimeException("Invalid userId");
        }

        if (cart == null || cart.isEmpty()) {
            throw new RuntimeException("Cart is empty");
        }

        try {
            return dao.createOrder(userId, cart); // ✅ FIX HERE
        } catch (Exception e) {
            System.out.println("Checkout error: " + e.getMessage());
            return false;
        }
    }

    // =========================
    // ADMIN - GET ALL ORDERS
    // =========================
    public List<Order> getAllOrders() {
        return dao.findAll();
    }

    // =========================
    // ORDER DETAILS
    // =========================
    public List<OrderDetail> getOrderDetails(int orderId) {

        if (orderId <= 0) {
            throw new RuntimeException("Invalid orderId");
        }

        return dao.findDetailsByOrderId(orderId);
    }

    // =========================
    // UPDATE STATUS
    // =========================
    public void updateStatus(int orderId, String status) {

        if (orderId <= 0) {
            throw new RuntimeException("Invalid orderId");
        }

        if (status == null || status.isBlank()) {
            throw new RuntimeException("Invalid status");
        }

        if (!status.equals("PENDING") &&
                !status.equals("SHIPPING") &&
                !status.equals("DELIVERED")) {
            throw new RuntimeException("Invalid status value");
        }

        dao.updateStatus(orderId, status);
    }

    // =========================
    // FIND BY ID
    // =========================
    public Order findById(int id) {

        if (id <= 0) {
            throw new RuntimeException("Invalid order id");
        }

        return dao.findById(id);
    }
}