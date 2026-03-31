package DAO;

import Model.CartItem;

import java.util.ArrayList;
import java.util.List;

public class CartDAO {

    private List<CartItem> cart = new ArrayList<>();

    public void add(CartItem item) {
        cart.add(item);
    }

    public List<CartItem> findAll() {
        return cart;
    }

    public void clear() {
        cart.clear();
    }
}