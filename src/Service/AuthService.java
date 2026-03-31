package Service;

import DAO.UserDAO;
import DAO.UserDAOImpl;
import Model.User;
import Util.PasswordUtil;

import java.util.Scanner;

public class AuthService {
    private UserDAO dao = new UserDAOImpl();

    public void register(String name, String email, String password, String phone, String address) {

        if (name.isEmpty()) {
            throw new RuntimeException("Ten khong duoc de trong");
        }

        if (!email.contains("@")) {
            throw new RuntimeException("Email phai co @");
        }

        if (password.length() < 6) {
            throw new RuntimeException("Mat khau >= 6 ky tu");
        }

        if (!phone.matches("\\d{10}")) {
            throw new RuntimeException("SĐT phai 10 so");
        }

        if (address.isEmpty()) {
            throw new RuntimeException("Dia chi khong duoc de trong");
        }

        String hash = PasswordUtil.hash(password);
        dao.save(new User(0, name, email, hash,"customer", phone, address ));
    }

    public User login(String email, String password) {
        User user = dao.findByEmail(email);

        if (user == null) {
            throw new RuntimeException("Khong tim thay");
        }
        if (!PasswordUtil.check(password, user.getPassword())) {
            throw new RuntimeException("Sai mat khau");
        }

        return user;
    }
}