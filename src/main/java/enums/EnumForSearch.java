package enums;

public enum EnumForSearch {
    LAST_NAME("lastName"),
    PRODUCT_NAME("productName"),
    MIN_TIMES("minTimes"),
    MIN_EXPENSES("minExpenses"),
    MAX_EXPENSES("maxExpenses"),
    BAD_CUSTOMERS("badCustomers");

    private String title;

    EnumForSearch(String s) {
        this.title = s;
    }

    public String getTitle() {
        return title;
    }
}
