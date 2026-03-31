package DAO;

import Model.User;

public interface UserDAO {
    User findByEmail(String email);
    void save(User user);
}