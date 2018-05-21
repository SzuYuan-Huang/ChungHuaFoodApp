package project.newstylefood.Model;

/**
 * Created by PC on 2017/11/5.
 */

public class Order
{
    private String ProductName;
    private int Quantity;
    private int Price;
    private String StoreId;
    private String SortId;
    private String status;
    private String notification;

    public Order()
    {

    }

    public Order(String productName, int quantity, int price, String storeId,String sortId)
    {
        ProductName = productName;
        Quantity = quantity;
        Price = price;
        StoreId = storeId;
        SortId = sortId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getPrice() {
        return Price;
    }

    public void setPrice(int price) {
        Price = price;
    }

    public String getProductName() {
        return ProductName;
    }

    public void setProductName(String productName) {
        ProductName = productName;
    }

    public int getQuantity() {
        return Quantity;
    }

    public void setQuantity(int quantity) {
        Quantity = quantity;
    }

    public String getStoreId() {
        return StoreId;
    }

    public void setStoreId(String storeId) {
        StoreId = storeId;
    }

    public String getSortId() {
        return SortId;
    }

    public void setSortId(String sortId) {
        SortId = sortId;
    }

    public String getNotification() {
        return notification;
    }

    public void setNotification(String notification) {
        this.notification = notification;
    }
}
