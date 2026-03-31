package Presentation;

import Model.FlashSale;
import Service.FlashSaleService;

import java.sql.Timestamp;
import java.util.List;
import java.util.Scanner;

public class FlashSaleUI {

    private final Scanner sc = new Scanner(System.in);
    private final FlashSaleService flashSaleService = new FlashSaleService();

    public void menu() {
        while (true) {
            System.out.println("\n=== FLASH SALE MANAGEMENT ===");
            System.out.println("1. Them Flash Sale");
            System.out.println("2. Danh sach Flash Sale");
            System.out.println("3. Cap nhat Flash Sale");
            System.out.println("4. Xoa Flash Sale");
            System.out.println("0. Quay lai");
            System.out.print("Choice: ");

            int c = Integer.parseInt(sc.nextLine());

            switch (c) {
                case 1 -> add();
                case 2 -> list();
                case 3 -> update();
                case 4 -> delete();
                case 0 -> { return; }
                default -> System.out.println("Sai lua chon!");
            }
        }
    }

    // ================= ADD =================
    private void add() {
        try {
            System.out.print("Product ID: ");
            int productId = Integer.parseInt(sc.nextLine());

            System.out.print("Discount %: ");
            double discount = Double.parseDouble(sc.nextLine());

            System.out.print("Start (yyyy-mm-dd hh:mm:ss): ");
            Timestamp start = Timestamp.valueOf(sc.nextLine());

            System.out.print("End (yyyy-mm-dd hh:mm:ss): ");
            Timestamp end = Timestamp.valueOf(sc.nextLine());

            if (start.after(end)) {
                System.out.println("Start < End!");
                return;
            }

            if (discount <= 0 || discount > 100) {
                System.out.println("Discount 1-100!");
                return;
            }

            flashSaleService.create(productId, discount, start, end);

            System.out.println("OK!");

        } catch (Exception e) {
            System.out.println("Sai format!");
        }
    }

    // ================= LIST =================
    private void list() {
        List<FlashSale> list = flashSaleService.getAll();

        if (list.isEmpty()) {
            System.out.println("Empty!");
            return;
        }

        for (FlashSale f : list) {
            System.out.printf("ID:%d | Product:%d | %.0f%% | %s -> %s\n",
                    f.getId(),
                    f.getProductId(),
                    f.getDiscountPercent(),
                    f.getStartTime(),
                    f.getEndTime());
        }
    }

    // ================= UPDATE =================
    private void update() {

        System.out.print("ID: ");
        int id = Integer.parseInt(sc.nextLine());

        FlashSale fs = flashSaleService.findById(id);

        if (fs == null) {
            System.out.println("Not found!");
            return;
        }

        System.out.print("New discount: ");
        double discount = Double.parseDouble(sc.nextLine());

        System.out.print("Start: ");
        Timestamp start = Timestamp.valueOf(sc.nextLine());

        System.out.print("End: ");
        Timestamp end = Timestamp.valueOf(sc.nextLine());

        flashSaleService.update(id, discount, start, end);

        System.out.println("Updated!");
    }

    // ================= DELETE (CONFIRM) =================
    private void delete() {

        System.out.print("ID: ");
        int id = Integer.parseInt(sc.nextLine());

        FlashSale fs = flashSaleService.findById(id);

        if (fs == null) {
            System.out.println("Not found!");
            return;
        }

        System.out.print("Confirm delete (YES/NO): ");
        String confirm = sc.nextLine();

        if (!confirm.equalsIgnoreCase("YES")) {
            System.out.println("Cancelled!");
            return;
        }

        flashSaleService.delete(id);

        System.out.println("Deleted!");
    }
}