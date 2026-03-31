package Presentation;

import java.util.Scanner;

public class AdminUI {

    private final Scanner sc = new Scanner(System.in);

    public void menu() {
        while (true) {
            System.out.println("\n=== ADMIN MENU ===");
            System.out.println("1. Quan ly Category");
            System.out.println("2. Quan ly Product");
            System.out.println("3. Quan ly Order");
            System.out.println("4. Quan ly Flash Sale");
            System.out.println("0. Logout");
            System.out.print("Choice: ");

            int choice = Integer.parseInt(sc.nextLine());

            switch (choice) {
                case 1 -> new CategoryUI().menu();
                case 2 -> new ProductUI().menu();
                case 3 -> new OrderUI().menu();
                case 4 -> new FlashSaleUI().menu();
                case 0 -> { return; }
                default -> System.out.println("Lua chon khong hop le!");
            }
        }
    }
}