package Service;

import DAO.ProductDAO;
import DAO.ProductDAOImpl;
import Model.Product;

import java.util.List;

public class ProductService {

    private ProductDAO dao = new ProductDAOImpl();

    public List<Product> getAll() {
        return dao.findAll();
    }

    public void add(Product p) {
        dao.insert(p);
    }

    public void edit(Product p) {
        dao.update(p);
    }

    public void remove(int id) {
        dao.delete(id);
    }

    public Product findById(int id) {
        return dao.findById(id);
    }

    public List<Product> search(String name) {
        return dao.searchByName(name);
    }

    public List<Product> sortByPriceASC() {
        return dao.sortPriceASC();
    }

    public List<Product> sortByPriceDESC() {
        return dao.sortPriceDESC();
    }
}