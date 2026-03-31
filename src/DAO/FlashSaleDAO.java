package DAO;

import Model.FlashSale;
import Util.MyDatabase;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class FlashSaleDAO {

    // ================= INSERT =================
    public void insert(FlashSale fs) {

        String sql = """
            INSERT INTO flash_sales(product_id, discount_percent, start_time, end_time)
            VALUES (?, ?, ?, ?)
        """;

        try (Connection conn = MyDatabase.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, fs.getProductId());
            ps.setDouble(2, fs.getDiscountPercent());
            ps.setTimestamp(3, fs.getStartTime());
            ps.setTimestamp(4, fs.getEndTime());

            ps.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // ================= FIND ALL =================
    public List<FlashSale> findAll() {

        List<FlashSale> list = new ArrayList<>();

        String sql = "SELECT * FROM flash_sales";

        try (Connection conn = MyDatabase.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                list.add(new FlashSale(
                        rs.getInt("id"),
                        rs.getInt("product_id"),
                        rs.getDouble("discount_percent"),
                        rs.getTimestamp("start_time"),
                        rs.getTimestamp("end_time")
                ));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }

    // ================= FIND BY ID =================
    public FlashSale findById(int id) {

        String sql = "SELECT * FROM flash_sales WHERE id = ?";

        try (Connection conn = MyDatabase.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return new FlashSale(
                        rs.getInt("id"),
                        rs.getInt("product_id"),
                        rs.getDouble("discount_percent"),
                        rs.getTimestamp("start_time"),
                        rs.getTimestamp("end_time")
                );
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    // ================= UPDATE =================
    public void update(FlashSale fs) {

        String sql = """
            UPDATE flash_sales
            SET discount_percent = ?, start_time = ?, end_time = ?
            WHERE id = ?
        """;

        try (Connection conn = MyDatabase.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setDouble(1, fs.getDiscountPercent());
            ps.setTimestamp(2, fs.getStartTime());
            ps.setTimestamp(3, fs.getEndTime());
            ps.setInt(4, fs.getId());

            ps.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // ================= DELETE =================
    public void delete(int id) {

        String sql = "DELETE FROM flash_sales WHERE id = ?";

        try (Connection conn = MyDatabase.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);
            ps.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // ================= ACTIVE DISCOUNT =================
    public Double getActiveDiscount(int productId) {

        String sql = """
            SELECT discount_percent
            FROM flash_sales
            WHERE product_id = ?
              AND NOW() BETWEEN start_time AND end_time
            LIMIT 1
        """;

        try (Connection conn = MyDatabase.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, productId);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return rs.getDouble("discount_percent");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }
}
