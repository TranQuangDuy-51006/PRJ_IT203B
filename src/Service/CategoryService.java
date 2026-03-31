package Service;

import DAO.CategoryDAOImpl;
import Model.Category;

import java.util.List;

public class CategoryService {

    private CategoryDAOImpl dao = new CategoryDAOImpl();

    public List<Category> getAll() {
        return dao.findAll();
    }

    public void add(String name) {
        dao.insert(name);
    }

    public void edit(int id, String name) {
        dao.update(id, name);
    }

    public void remove(int id) {
        dao.delete(id);
    }

    public boolean existsById(int id) {
        return dao.findById(id) != null;
    }

    public boolean existsByName(String name) {
        return dao.findByName(name) != null;
    }
}