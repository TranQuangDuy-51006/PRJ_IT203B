package DAO;

import Model.Product;
import Util.MyDatabase;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Implementation of ProductDAO using JDBC.
 * Handles CRUD operations and filtering for the Product entity.
 */
public class ProductDAOImpl implements ProductDAO {

    // =========================================================================
    // READ OPERATIONS
    // =========================================================================

    @Override
    public List<Product> findAll() {
        List<Product> list = new ArrayList<>();
        String sql = "SELECT * FROM product";

        try (Connection conn = MyDatabase.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                list.add(map(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    @Override
    public Product findById(int id) {
        String sql = "SELECT * FROM product WHERE id = ?";

        try (Connection conn = MyDatabase.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return map(rs);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<Product> searchByName(String name) {
        List<Product> list = new ArrayList<>();
        String sql = "SELECT * FROM product WHERE name LIKE ?";

        try (Connection conn = MyDatabase.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, "%" + name + "%");
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    list.add(map(rs));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    // =========================================================================
    // WRITE OPERATIONS (CUD)
    // =========================================================================

    @Override
    public void insert(Product p) {
        String sql = """
                INSERT INTO product(name, brand, storage, color, price, stock, description, category_id) 
                VALUES (?, ?, ?, ?, ?, ?, ?, ?)
                """;

        try (Connection conn = MyDatabase.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            setProductParams(ps, p);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void update(Product p) {
        String sql = """
                UPDATE product 
                SET name=?, brand=?, storage=?, color=?, price=?, stock=?, description=?, category_id=? 
                WHERE id=?
                """;

        try (Connection conn = MyDatabase.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            setProductParams(ps, p);
            ps.setInt(9, p.getId()); // ID for WHERE clause
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void delete(int id) {
        String sql = "DELETE FROM product WHERE id = ?";

        try (Connection conn = MyDatabase.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // =========================================================================
    // SORTING OPERATIONS
    // =========================================================================

    @Override
    public List<Product> sortPriceASC() {
        return sort("ORDER BY price ASC");
    }

    @Override
    public List<Product> sortPriceDESC() {
        return sort("ORDER BY price DESC");
    }

    private List<Product> sort(String orderClause) {
        List<Product> list = new ArrayList<>();
        String sql = "SELECT * FROM product " + orderClause;

        try (Connection conn = MyDatabase.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                list.add(map(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    // =========================================================================
    // PRIVATE HELPER METHODS
    // =========================================================================

    /**
     * Maps a single row from ResultSet to a Product object.
     */
    private Product map(ResultSet rs) throws SQLException {
        return new Product(
                rs.getInt("id"),
                rs.getString("name"),
                rs.getString("brand"),
                rs.getString("storage"),
                rs.getString("color"),
                rs.getDouble("price"),
                rs.getInt("stock"),
                rs.getString("description"),
                rs.getInt("category_id")
        );
    }

    /**
     * Shared logic for setting PreparedStatement parameters for Insert/Update.
     */
    private void setProductParams(PreparedStatement ps, Product p) throws SQLException {
        ps.setString(1, p.getName());
        ps.setString(2, p.getBrand());
        ps.setString(3, p.getStorage());
        ps.setString(4, p.getColor());
        ps.setDouble(5, p.getPrice());
        ps.setInt(6, p.getStock());
        ps.setString(7, p.getDescription());
        ps.setInt(8, p.getCategoryId());
    }
}