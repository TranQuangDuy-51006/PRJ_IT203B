package Presentation;

import Model.Product;
import Service.ProductService;

import java.util.List;
import java.util.Scanner;

public class ProductUI {

    private ProductService service = new ProductService();
    private Scanner sc = new Scanner(System.in);

    private final int PAGE_SIZE = 5;

    public void menu() {
        while (true) {
            System.out.println("\n=== QUAN LY SAN PHAM ===");
            System.out.println("1. Hien thi");
            System.out.println("2. Them");
            System.out.println("3. Sua");
            System.out.println("4. Xoa");
            System.out.println("5. Tim kiem");
            System.out.println("6. Sap xep gia tang");
            System.out.println("7. Sap xep gia giam");
            System.out.println("0. Quay lai");
            System.out.print("Lua chon: ");

            int c = inputInt();

            switch (c) {
                case 1: show(); break;
                case 2: add(); break;
                case 3: edit(); break;
                case 4: delete(); break;
                case 5: search(); break;
                case 6: sort(true); break;
                case 7: sort(false); break;
                case 0: return;
                default: System.out.println("Lua chon khong hop le!");
            }
        }
    }

    // ===== VALIDATE =====
    private int inputInt() {
        while (true) {
            try {
                return Integer.parseInt(sc.nextLine());
            } catch (Exception e) {
                System.out.print("Nhap so: ");
            }
        }
    }

    private double inputPrice() {
        while (true) {
            try {
                double p = Double.parseDouble(sc.nextLine());
                if (p <= 0) throw new Exception();
                return p;
            } catch (Exception e) {
                System.out.print("Gia > 0, nhap lai: ");
            }
        }
    }

    private int inputStock() {
        while (true) {
            try {
                int s = Integer.parseInt(sc.nextLine());
                if (s < 0) throw new Exception();
                return s;
            } catch (Exception e) {
                System.out.print("Stock >= 0, nhap lai: ");
            }
        }
    }

    private String inputText(String msg) {
        while (true) {
            System.out.print(msg);
            String s = sc.nextLine().trim();
            if (!s.isEmpty()) return s;
            System.out.println("Khong duoc de trong!");
        }
    }

    // ===== SHOW + PAGINATION =====
    private void show() {
        List<Product> list = service.getAll();

        if (list.isEmpty()) {
            System.out.println("Khong co san pham");
            return;
        }

        int totalPage = (int) Math.ceil(list.size() * 1.0 / PAGE_SIZE);
        int page = 1;

        while (true) {
            int start = (page - 1) * PAGE_SIZE;
            int end = Math.min(start + PAGE_SIZE, list.size());

            System.out.println("\nTrang " + page + "/" + totalPage);
            System.out.println("+----+----------------------+----------+--------+--------+---------+");
            System.out.println("| ID | Ten                  | Brand    | Dunglu | Mau    | Gia     |");
            System.out.println("+----+----------------------+----------+--------+--------+---------+");

            for (int i = start; i < end; i++) {
                Product p = list.get(i);
                System.out.printf("| %-2d | %-20s | %-8s | %-6s | %-6s | %-7.0f |\n",
                        p.getId(), p.getName(), p.getBrand(),
                        p.getStorage(), p.getColor(), p.getPrice());
            }

            System.out.println("+----+----------------------+----------+--------+--------+---------+");
            System.out.println("n-next | p-prev | 0-exit");
            String cmd = sc.nextLine();

            if (cmd.equals("n") && page < totalPage) page++;
            else if (cmd.equals("p") && page > 1) page--;
            else if (cmd.equals("0")) break;
        }
    }

    // ===== ADD =====
    private void add() {
        String name = inputText("Ten: ");
        String brand = inputText("Hang: ");
        String storage = inputText("Dung luong: ");
        String color = inputText("Mau: ");

        System.out.print("Gia: ");
        double price = inputPrice();

        System.out.print("So luong: ");
        int stock = inputStock();

        String desc = inputText("Mo ta: ");

        System.out.print("Category ID: ");
        int cateId = inputInt();

        Product p = new Product(0, name, brand, storage, color, price, stock, desc, cateId);

        service.add(p);
        System.out.println("Them thanh cong!");
    }

    // ===== EDIT =====
    private void edit() {
        System.out.print("Nhap id: ");
        int id = inputInt();

        Product old = service.findById(id);
        if (old == null) {
            System.out.println("Khong ton tai!");
            return;
        }

        // hien thi thong tin cu
        System.out.println("Thong tin cu:");
        System.out.println(old.getName() + " | " + old.getPrice() + " | " + old.getStock());

        String name = inputText("Ten moi: ");

        System.out.print("Gia moi: ");
        double price = inputPrice();

        System.out.print("Stock moi: ");
        int stock = inputStock();

        Product p = new Product(id, name, old.getBrand(), old.getStorage(),
                old.getColor(), price, stock, old.getDescription(), old.getCategoryId());

        service.edit(p);
        System.out.println("Cap nhat thanh cong!");
    }

    // ===== DELETE =====
    private void delete() {
        System.out.print("Nhap id: ");
        int id = inputInt();

        System.out.print("Ban co chac chan muon xoa? (y/n): ");
        String confirm = sc.nextLine();

        if (!confirm.equalsIgnoreCase("y")) {
            System.out.println("Da huy!");
            return;
        }

        service.remove(id);
        System.out.println("Xoa thanh cong!");
    }

    // ===== SEARCH =====
    private void search() {
        String name = inputText("Nhap ten: ");
        List<Product> list = service.search(name);

        if (list.isEmpty()) {
            System.out.println("Khong tim thay");
            return;
        }

        for (Product p : list) {
            System.out.println(p.getId() + " | " + p.getName() + " | " + p.getPrice());
        }
    }

    // ===== SORT =====
    private void sort(boolean asc) {
        List<Product> list = asc ? service.sortByPriceASC() : service.sortByPriceDESC();

        for (Product p : list) {
            System.out.println(p.getId() + " | " + p.getName() + " | " + p.getPrice());
        }
    }
}