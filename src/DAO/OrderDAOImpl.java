package DAO;

import Model.CartItem;
import Model.Order;
import Model.OrderDetail;
import Util.MyDatabase;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class OrderDAOImpl implements OrderDAO {

    @Override
    public boolean createOrder(int userId, List<CartItem> cart) {

        if (cart == null || cart.isEmpty()) {
            throw new RuntimeException("Cart is empty");
        }

        String insertOrder =
                "INSERT INTO orders(user_id, status, created_at) VALUES (?, 'PENDING', NOW())";

        String insertDetail =
                "INSERT INTO order_details(order_id, product_id, quantity, price) VALUES (?, ?, ?, ?)";

        String updateStock =
                "UPDATE product SET stock = stock - ? WHERE id = ? AND stock >= ?";

        Connection conn = null;

        try {
            conn = MyDatabase.getConnection();
            conn.setAutoCommit(false);

            // 1. CREATE ORDER
            int orderId;

            try (PreparedStatement psOrder = conn.prepareStatement(insertOrder, Statement.RETURN_GENERATED_KEYS)) {

                psOrder.setInt(1, userId);
                psOrder.executeUpdate();

                ResultSet rs = psOrder.getGeneratedKeys();
                if (!rs.next()) throw new RuntimeException("Cannot get order ID");

                orderId = rs.getInt(1);
            }

            // 2. PROCESS CART
            for (CartItem item : cart) {

                // 2.1 UPDATE STOCK (atomic check)
                try (PreparedStatement psStock = conn.prepareStatement(updateStock)) {

                    psStock.setInt(1, item.getQuantity());
                    psStock.setInt(2, item.getProductId());
                    psStock.setInt(3, item.getQuantity());

                    int affected = psStock.executeUpdate();

                    if (affected == 0) {
                        throw new RuntimeException("Not enough stock for productId: " + item.getProductId());
                    }
                }

                // 2.2 GET PRICE FROM DB (SAFE INSIDE SAME CONNECTION)
                double price;

                try (PreparedStatement ps = conn.prepareStatement(
                        "SELECT price FROM product WHERE id = ?")) {

                    ps.setInt(1, item.getProductId());

                    ResultSet rs = ps.executeQuery();

                    if (!rs.next()) {
                        throw new RuntimeException("Product not found: " + item.getProductId());
                    }

                    price = rs.getDouble("price");
                }

                // 2.3 INSERT ORDER DETAIL
                try (PreparedStatement psDetail = conn.prepareStatement(insertDetail)) {

                    psDetail.setInt(1, orderId);
                    psDetail.setInt(2, item.getProductId());
                    psDetail.setInt(3, item.getQuantity());
                    psDetail.setDouble(4, price);

                    psDetail.executeUpdate();
                }
            }

            conn.commit();
            return true;

        } catch (Exception e) {

            try {
                if (conn != null) conn.rollback();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }

            throw new RuntimeException("Checkout failed: " + e.getMessage(), e);

        } finally {

            try {
                if (conn != null) conn.setAutoCommit(true);
                if (conn != null) conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    // =========================
    // FIND ALL ORDERS
    // =========================
    @Override
    public List<Order> findAll() {

        List<Order> list = new ArrayList<>();
        String sql = "SELECT * FROM orders ORDER BY created_at DESC";

        try (Connection conn = MyDatabase.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Order o = new Order();
                o.setId(rs.getInt("id"));
                o.setUserId(rs.getInt("user_id"));
                o.setStatus(rs.getString("status"));
                o.setCreatedAt(rs.getTimestamp("created_at"));
                list.add(o);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }

    // =========================
    // FIND ORDER DETAIL
    // =========================
    @Override
    public List<OrderDetail> findDetailsByOrderId(int orderId) {

        List<OrderDetail> list = new ArrayList<>();

        String sql =
                "SELECT od.*, p.name " +
                        "FROM order_details od " +
                        "JOIN product p ON od.product_id = p.id " +
                        "WHERE od.order_id = ?";

        try (Connection conn = MyDatabase.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, orderId);

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {

                OrderDetail od = new OrderDetail();
                od.setOrderId(rs.getInt("order_id"));
                od.setProductId(rs.getInt("product_id"));
                od.setQuantity(rs.getInt("quantity"));
                od.setPrice(rs.getDouble("price"));

                list.add(od);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }

    // =========================
    // UPDATE STATUS
    // =========================
    @Override
    public void updateStatus(int orderId, String status) {

        String sql = "UPDATE orders SET status=? WHERE id=?";

        try (Connection conn = MyDatabase.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, status);
            ps.setInt(2, orderId);

            ps.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // =========================
    // FIND BY ID
    // =========================
    @Override
    public Order findById(int id) {

        String sql = "SELECT * FROM orders WHERE id = ?";

        try (Connection conn = MyDatabase.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                Order order = new Order();
                order.setId(rs.getInt("id"));
                order.setUserId(rs.getInt("user_id"));
                order.setStatus(rs.getString("status"));
                order.setCreatedAt(rs.getTimestamp("created_at"));
                return order;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }
}