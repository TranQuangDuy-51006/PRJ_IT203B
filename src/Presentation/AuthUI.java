package Presentation;

import Model.User;
import Service.AuthService;

import java.util.Scanner;

public class AuthUI {

    private AuthService authService = new AuthService();
    private Scanner sc = new Scanner(System.in);

    public void menu() {
        while (true) {
            System.out.println("\n======== AUTH MENU ========");
            System.out.println("1. Register");
            System.out.println("2. Login");
            System.out.println("0. Exit");
            System.out.print("Choice: ");

            int choice = sc.nextInt();
            sc.nextLine();

            switch (choice) {
                case 1:
                    register();
                    break;
                case 2:
                    login();
                    break;
                case 0:
                    System.exit(0);
                default:
                    System.out.println("Invalid choice!");
            }
        }
    }

    // ================= REGISTER =================
    private void register() {

        while (true) {
            try {
                System.out.print("Nhap ten: ");
                String name = sc.nextLine().trim();
                if (name.isEmpty()) throw new RuntimeException("Ten khong duoc de trong");

                // ===== EMAIL =====
                String email;
                while (true) {
                    System.out.print("Nhap email: ");
                    email = sc.nextLine().trim();

                    if (email.isEmpty()) {
                        System.out.println("Email khong duoc de trong!");
                    } else if (!email.contains("@")) {
                        System.out.println("Email phai co @!");
                    } else {
                        break;
                    }
                }

                // ===== PASSWORD =====
                String password;
                while (true) {
                    System.out.print("Nhap mat khau: ");
                    password = sc.nextLine();

                    if (password.isEmpty()) {
                        System.out.println("Khong duoc de trong!");
                    } else if (password.length() < 6) {
                        System.out.println("Mat khau >= 6 ky tu!");
                    } else {
                        break;
                    }
                }

                // ===== PHONE =====
                String phone;
                while (true) {
                    System.out.print("Nhap so dien thoai: ");
                    phone = sc.nextLine().trim();

                    if (phone.isEmpty()) {
                        System.out.println("SĐT khong duoc de trong!");
                    } else if (!phone.matches("\\d{10}")) {
                        System.out.println("SĐT phai la 10 chu so!");
                    } else {
                        break;
                    }
                }

                // ===== ADDRESS =====
                String address;
                while (true) {
                    System.out.print("Nhap dia chi: ");
                    address = sc.nextLine().trim();

                    if (address.isEmpty()) {
                        System.out.println("Dia chi khong duoc de trong!");
                    } else {
                        break;
                    }
                }

                // gọi service
                authService.register(name, email, password, phone, address);

                System.out.println("Dang ky thanh cong!");
                break;

            } catch (Exception e) {
                System.out.println("Loi: " + e.getMessage());
                System.out.println("Nhap lai!\n");
            }
        }
    }

    // ================= LOGIN =================
    private void login() {
        System.out.print("Nhap email: ");
        String email = sc.nextLine().trim();

        System.out.print("Nhap mat khau: ");
        String password = sc.nextLine();

        try {
            User user = authService.login(email, password);
            System.out.println("Dang nhap thanh cong!");

            // phân quyền
            if (user.getRole().equalsIgnoreCase("admin")) {
                new AdminUI().menu();

            } else {
                new CustomerUI().menu(user.getId());

            }

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
