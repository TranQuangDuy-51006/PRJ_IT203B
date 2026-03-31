package Presentation;

import Service.CategoryService;
import Model.Category;

import java.util.List;
import java.util.Scanner;

public class CategoryUI {

    private static CategoryService service = new CategoryService();
    private static Scanner sc = new Scanner(System.in);

    public static void menu() {
        while (true) {
            System.out.println("\n=== CATEGORY MANAGEMENT ===");
            System.out.println("1. Hien thi");
            System.out.println("2. Them");
            System.out.println("3. Sua");
            System.out.println("4. Xoa");
            System.out.println("0. Thoat");
            System.out.print("Lua chon: ");

            int choice = Integer.parseInt(sc.nextLine());

            switch (choice) {
                case 1 -> show();
                case 2 -> add();
                case 3 -> edit();
                case 4 -> delete();
                case 0 -> {
                    return;
                }
                default -> System.out.println("Sai lua chon!");
            }
        }
    }

    private static void show() {
        List<Category> list = service.getAll();

        if (list.isEmpty()) {
            System.out.println("Danh muc rong!");
            return;
        }

        System.out.println("+----+----------------------+------------+");
        System.out.println("| ID | Ten                  | Trang thai |");
        System.out.println("+----+----------------------+------------+");

        for (Category c : list) {
            System.out.printf("| %-2d | %-20s | %-10s |\n",
                    c.getId(),
                    c.getName(),
                    c.isDeleted() ? "Da xoa" : "Active");
        }

        System.out.println("+----+----------------------+------------+");
    }

    private static void add() {
        System.out.print("Nhap ten: ");
        String name = sc.nextLine();

        service.add(name);
        System.out.println("Them thanh cong!");
    }

    private static void edit() {
        System.out.print("Nhap id: ");
        int id = Integer.parseInt(sc.nextLine());

        if (!service.existsById(id)) {
            System.out.println("ID khong ton tai!");
            return;
        }

        System.out.print("Nhap ten moi: ");
        String name = sc.nextLine();

        service.edit(id, name);
        System.out.println("Cap nhat thanh cong!");
    }

    private static void delete() {
        System.out.print("Nhap id: ");
        int id = Integer.parseInt(sc.nextLine());

        if (!service.existsById(id)) {
            System.out.println("ID khong ton tai!");
            return;
        }

        System.out.print("Ban co chac muon xoa? (y/n): ");
        String confirm = sc.nextLine();

        if (!confirm.equalsIgnoreCase("y")) {
            System.out.println("Da huy xoa!");
            return;
        }

        service.remove(id);
        System.out.println("Da xoa (soft delete)!");
    }
}