package DAO;

import Model.CartItem;
import Model.Order;
import Model.OrderDetail;

import java.util.List;

/**
 * Interface defining the data access operations for Orders and their details.
 */
public interface OrderDAO {

    /**
     * Handles the complex transaction of creating an order,
     * reducing product stock, and saving order details.
     * * @param userId The ID of the user placing the order.
     * @param cart   The list of items currently in the user's cart.
     * @return true if the transaction completes successfully.
     */
    boolean createOrder(int userId, List<CartItem> cart);

    /**
     * Retrieves all orders from the database, typically for administrative use.
     * * @return A list of all Order objects.
     */
    List<Order> findAll();

    /**
     * Retrieves the specific line items associated with a single order.
     * * @param orderId The ID of the order to look up.
     * @return A list of OrderDetail objects.
     */
    List<OrderDetail> findDetailsByOrderId(int orderId);

    /**
     * Updates the fulfillment status (e.g., 'PENDING', 'SHIPPED', 'CANCELLED').
     * * @param orderId The ID of the order to update.
     * @param status  The new status string.
     */
    void updateStatus(int orderId, String status);

    /**
     * Finds a single order by its primary key.
     * * @param id The order ID.
     * @return The Order object if found, otherwise null.
     */
    Order findById(int id);
}