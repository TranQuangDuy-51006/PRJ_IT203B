package DAO;

import Model.Category;
import java.util.List;

public interface CategoryDAO {

    List<Category> findAll();

    boolean existsById(int id);

    boolean existsByName(String name);

    void insert(String name);

    void update(int id, String name);

    void softDelete(int id);
}