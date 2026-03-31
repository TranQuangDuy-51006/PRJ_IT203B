package Service;

import DAO.CartDAO;
import DAO.ProductDAO;
import DAO.ProductDAOImpl;
import Model.CartItem;
import Model.Product;

import java.util.List;

public class CartService {

    private final CartDAO cartDAO = new CartDAO();
    private final ProductDAO productDAO = new ProductDAOImpl();

    public void addToCart(CartItem item) {
        cartDAO.add(item);
    }

    public List<CartItem> getCart() {
        return cartDAO.findAll();
    }

    public void clear() {
        cartDAO.clear();
    }

    public double getTotal() {

        List<CartItem> cart = cartDAO.findAll();
        if (cart == null) return 0;

        double total = 0;

        for (CartItem c : cart) {

            if (c == null) continue;

            Product p = productDAO.findById(c.getProductId());

            if (p == null) continue;

            total += p.getPrice() * c.getQuantity();
        }

        return total;
    }
}