package Service;

import DAO.FlashSaleDAO;
import Model.FlashSale;
import java.sql.Timestamp;
import java.util.List;

public class FlashSaleService {

    private final FlashSaleDAO flashSaleDAO = new FlashSaleDAO();

    // ================= CREATE =================
    public void create(int productId, double discount, Timestamp start, Timestamp end) {
        FlashSale fs = new FlashSale(productId, discount, start, end);
        flashSaleDAO.insert(fs);
    }

    // ================= GET ALL =================
    public List<FlashSale> getAll() {
        return flashSaleDAO.findAll();
    }

    // ================= FIND BY ID =================
    public FlashSale findById(int id) {
        return flashSaleDAO.findById(id);
    }

    // ================= UPDATE =================
    public void update(int id, double discount, Timestamp start, Timestamp end) {
        FlashSale fs = new FlashSale();
        fs.setId(id);
        fs.setDiscountPercent(discount);
        fs.setStartTime(start);
        fs.setEndTime(end);

        flashSaleDAO.update(fs);
    }

    // ================= DELETE =================
    public void delete(int id) {
        flashSaleDAO.delete(id);
    }

    // ================= CHECK ACTIVE FLASH SALE =================
    public Double getActiveDiscount(int productId) {
        return flashSaleDAO.getActiveDiscount(productId);
    }
}