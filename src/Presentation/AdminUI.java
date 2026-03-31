package Presentation;

import Service.OrderService;
import Model.Order;
import Model.OrderDetail;

import java.util.List;
import java.util.Scanner;

public class AdminUI {

    private final Scanner sc = new Scanner(System.in);
    private final OrderService orderService = new OrderService();

    // ================= MAIN MENU =================
    public void menu() {
        while (true) {
            System.out.println("\n=== ADMIN MENU ===");
            System.out.println("1. Quan ly Category");
            System.out.println("2. Quan ly Product");
            System.out.println("3. Quan ly Order");
            System.out.println("0. Logout");
            System.out.print("Choice: ");

            int choice = Integer.parseInt(sc.nextLine());

            switch (choice) {
                case 1 -> categoryMenu();
                case 2 -> productMenu();
                case 3 -> orderMenu();
                case 0 -> { return; }
                default -> System.out.println("Lua chon khong hop le!");
            }
        }
    }

    private void categoryMenu() {
        new CategoryUI().menu();
    }

    private void productMenu() {
        new ProductUI().menu();
    }

    // ================= ORDER MENU =================
    private void orderMenu() {
        while (true) {
            System.out.println("\n=== ORDER MANAGEMENT ===");
            System.out.println("1. Xem danh sach don hang");
            System.out.println("2. Xem chi tiet don hang");
            System.out.println("3. Cap nhat trang thai");
            System.out.println("0. Quay lai");
            System.out.print("Choice: ");

            int c = Integer.parseInt(sc.nextLine());

            switch (c) {
                case 1 -> showOrders();
                case 2 -> showOrderDetail();
                case 3 -> updateStatus();
                case 0 -> { return; }
                default -> System.out.println("Lua chon khong hop le!");
            }
        }
    }

    // ================= SHOW ORDERS =================
    private void showOrders() {

        List<Order> list = orderService.getAllOrders();

        if (list == null || list.isEmpty()) {
            System.out.println("Chua co don hang nao!");
            return;
        }

        System.out.println("+----+---------+---------------------+------------+");
        System.out.println("| ID | User ID | Created At          | Status     |");
        System.out.println("+----+---------+---------------------+------------+");

        for (Order o : list) {
            System.out.printf("| %-2d | %-7d | %-19s | %-10s |\n",
                    o.getId(),
                    o.getUserId(),
                    o.getCreatedAt(),
                    o.getStatus());
        }

        System.out.println("+----+---------+---------------------+------------+");
    }

    // ================= SHOW ORDER DETAIL =================
    private void showOrderDetail() {

        System.out.print("Nhap order id: ");
        int id = Integer.parseInt(sc.nextLine());

        Order order = orderService.findById(id);

        if (order == null) {
            System.out.println("Order khong ton tai!");
            return;
        }

        List<OrderDetail> list = orderService.getOrderDetails(id);

        if (list == null || list.isEmpty()) {
            System.out.println("Don hang khong co chi tiet!");
            return;
        }

        System.out.println("\nOrder ID: " + id);
        System.out.println("Status  : " + order.getStatus());
        System.out.println("----------------------------------------");

        System.out.println("+----------+----------+----------+----------+");
        System.out.println("| Product  | Qty      | Price    | SubTotal |");
        System.out.println("+----------+----------+----------+----------+");

        double total = 0;

        for (OrderDetail d : list) {

            double subTotal = d.getQuantity() * d.getPrice();
            total += subTotal;

            System.out.printf("| %-8d | %-8d | %-8.0f | %-8.0f |\n",
                    d.getProductId(),
                    d.getQuantity(),
                    d.getPrice(),
                    subTotal);
        }

        System.out.println("+----------+----------+----------+----------+");
        System.out.printf("TOTAL: %.0f\n", total);
    }

    // ================= UPDATE STATUS =================
    private void updateStatus() {

        System.out.print("Nhap order id: ");
        int id = Integer.parseInt(sc.nextLine());

        Order order = orderService.findById(id);

        if (order == null) {
            System.out.println("Order khong ton tai!");
            return;
        }

        System.out.println("Current status: " + order.getStatus());

        System.out.print("Nhap status (PENDING / SHIPPING / DELIVERED): ");
        String status = sc.nextLine().toUpperCase();

        // validate flow chuẩn
        if (!status.equals("PENDING") &&
                !status.equals("SHIPPING") &&
                !status.equals("DELIVERED")) {

            System.out.println("Status khong hop le!");
            return;
        }

        // optional: enforce flow rule
        if (!isValidTransition(order.getStatus(), status)) {
            System.out.println("Khong the chuyen trang thai nay!");
            return;
        }

        orderService.updateStatus(id, status);
        System.out.println("Cap nhat thanh cong!");
    }

    // ================= BUSINESS RULE =================
    private boolean isValidTransition(String current, String next) {

        if (current.equals("PENDING") && next.equals("SHIPPING")) return true;
        if (current.equals("SHIPPING") && next.equals("DELIVERED")) return true;

        return current.equals(next);
    }
}