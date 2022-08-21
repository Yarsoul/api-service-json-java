package entity;

public class Purchase {
    private int customerId;
    private String customerName;
    private String productName;
    private int expenses;


    public int getCustomerId() {
        return customerId;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    public int getExpenses() {
        return expenses;
    }

    public void setExpenses(int expenses) {
        this.expenses = expenses;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public void showAll() {
        System.out.println("customerName = " + customerName);
        System.out.println("productName = " + productName);
        System.out.println("expenses = " + expenses);
    }
}
