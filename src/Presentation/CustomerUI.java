package Presentation;

import Service.*;
import DAO.*;
import Model.*;

import java.util.*;

public class CustomerUI {

    private Scanner sc = new Scanner(System.in);

    private ProductDAO productDAO = new ProductDAOImpl();
    private CartService cartService = new CartService();
    private OrderService orderService = new OrderService();

    public void menu(int userId) {

        while (true) {
            System.out.println("\n=== CUSTOMER MENU ===");
            System.out.println("1. Xem san pham");
            System.out.println("2. Them vao gio");
            System.out.println("3. Xem gio hang");
            System.out.println("4. Thanh toan");
            System.out.println("0. Thoat");

            System.out.print("Chon: ");
            int c = Integer.parseInt(sc.nextLine());

            switch (c) {
                case 1 -> show(productDAO.findAll());
                case 2 -> addCart();
                case 3 -> viewCart();
                case 4 -> checkout(userId);
                case 0 -> {
                    return;
                }
                default -> System.out.println("Sai lua chon");
            }
        }
    }

    private void show(List<Product> list) {
        if (list == null || list.isEmpty()) {
            System.out.println("Khong co san pham");
            return;
        }

        System.out.println("\nID | TEN | GIA | STOCK");

        for (Product p : list) {
            if (p != null && p.getStock() > 0) {
                System.out.println(
                        p.getId() + " | " +
                                p.getName() + " | " +
                                p.getPrice() + " | " +
                                p.getStock()
                );
            }
        }
    }

    private void addCart() {

        System.out.print("Nhap productId: ");
        int id = Integer.parseInt(sc.nextLine());

        Product p = productDAO.findById(id);

        if (p == null) {
            System.out.println("Khong ton tai san pham");
            return;
        }

        System.out.print("So luong: ");
        int qty = Integer.parseInt(sc.nextLine());

        if (qty <= 0) {
            System.out.println("So luong > 0");
            return;
        }

        if (qty > p.getStock()) {
            System.out.println("Khong du hang");
            return;
        }

        cartService.addToCart(new CartItem(p.getId(), qty));

        System.out.println("Da them vao gio");
    }

    private void viewCart() {

        List<CartItem> cart = cartService.getCart();

        if (cart == null || cart.isEmpty()) {
            System.out.println("Gio hang trong");
            return;
        }

        System.out.println("\n=== GIO HANG ===");
        System.out.println("ID | TEN | GIA | QTY | TOTAL");

        double totalAll = 0;

        for (CartItem c : cart) {

            if (c == null) continue;

            Product p = productDAO.findById(c.getProductId());

            if (p == null) continue;

            double total = p.getPrice() * c.getQuantity();
            totalAll += total;

            System.out.println(
                    p.getId() + " | " +
                            p.getName() + " | " +
                            p.getPrice() + " | " +
                            c.getQuantity() + " | " +
                            total
            );
        }

        System.out.println("------------------------");
        System.out.println("TOTAL: " + totalAll);
    }

    private void checkout(int userId) {

        List<CartItem> cart = cartService.getCart();

        if (cart == null || cart.isEmpty()) {
            System.out.println("Gio hang trong");
            return;
        }

        try {
            boolean success = orderService.checkout(userId, cart);
            if (success) {
                cartService.clear();
                System.out.println("THANH TOAN THANH CONG");
            }
        } catch (Exception e) {
            System.out.println("THANH TOAN THAT BAI: " + e.getMessage());
        }
    }
}