package Util;

import java.sql.Connection;
import java.sql.DriverManager;

public class MyDatabase {

    private static final String URL = "jdbc:mysql://localhost:3306/phone_store";
    private static final String USER = "root";
    private static final String PASS = "khanhkhanh";

    public static Connection getConnection() {
        try {
            return DriverManager.getConnection(URL, USER, PASS);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}