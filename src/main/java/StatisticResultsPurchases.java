import java.util.ArrayList;
import java.util.HashMap;

public class StatisticResultsPurchases {
    private String name;
    private ArrayList<HashMap<String, Object>> purchases = new ArrayList<>();
    private int totalExpenses;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ArrayList<HashMap<String, Object>> getPurchases() {
        return purchases;
    }

    public void setPurchases(ArrayList<HashMap<String, Object>> purchases) {
        this.purchases = purchases;
    }

    public void addPurchase(HashMap<String, Object> purchase) {
        this.purchases.add(purchase);
    }

    public int getTotalExpenses() {
        return totalExpenses;
    }

    public void setTotalExpenses(int totalExpenses) {
        this.totalExpenses = totalExpenses;
    }
}
