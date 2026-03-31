package DAO;

import Model.Product;
import java.util.List;

public interface ProductDAO {

    List<Product> findAll();

    void insert(Product p);

    void update(Product p);

    void delete(int id);

    Product findById(int id);

    List<Product> searchByName(String name);

    List<Product> sortPriceASC();

    List<Product> sortPriceDESC();
}