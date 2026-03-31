package Presentation;

import Service.OrderService;
import Model.Order;
import Model.OrderDetail;

import java.util.List;
import java.util.Scanner;

public class OrderUI {

    private final Scanner sc = new Scanner(System.in);
    private final OrderService orderService = new OrderService();

    public void menu() {
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

    // ================= LIST ORDERS =================
    private void showOrders() {
        List<Order> list = orderService.getAllOrders();

        if (list == null || list.isEmpty()) {
            System.out.println("Chua co don hang!");
            return;
        }

        for (Order o : list) {
            System.out.printf("ID:%d | User:%d | Status:%s | Time:%s\n",
                    o.getId(),
                    o.getUserId(),
                    o.getStatus(),
                    o.getCreatedAt());
        }
    }

    // ================= DETAIL =================
    private void showOrderDetail() {

        System.out.print("Order ID: ");
        int id = Integer.parseInt(sc.nextLine());

        Order order = orderService.findById(id);

        if (order == null) {
            System.out.println("Khong ton tai!");
            return;
        }

        List<OrderDetail> list = orderService.getOrderDetails(id);

        double total = 0;

        for (OrderDetail d : list) {
            double sub = d.getPrice() * d.getQuantity();
            total += sub;

            System.out.printf("Product:%d Qty:%d Price:%.0f Sub:%.0f\n",
                    d.getProductId(),
                    d.getQuantity(),
                    d.getPrice(),
                    sub);
        }

        System.out.println("TOTAL = " + total);
    }

    // ================= UPDATE STATUS =================
    private void updateStatus() {

        System.out.print("Order ID: ");
        int id = Integer.parseInt(sc.nextLine());

        Order order = orderService.findById(id);

        if (order == null) {
            System.out.println("Khong ton tai!");
            return;
        }

        System.out.print("Status (PENDING/SHIPPING/DELIVERED): ");
        String status = sc.nextLine().toUpperCase();

        orderService.updateStatus(id, status);

        System.out.println("Cap nhat thanh cong!");
    }
}