import java.util.ArrayList;

public class StatisticResultsFinish {
    private String type = "stat";
    private int totalDays;
    private ArrayList<StatisticResultsPurchases> customers = new ArrayList<>();
    private int totalExpenses;
    private double avgExpenses;

    public String getType() {
        return type;
    }

    public int getTotalDays() {
        return totalDays;
    }

    public void setTotalDays(int totalDays) {
        this.totalDays = totalDays;
    }

    public ArrayList<StatisticResultsPurchases> getCustomers() {
        return customers;
    }

    public void setCustomers(ArrayList<StatisticResultsPurchases> customers) {
        this.customers = customers;
    }

    public int getTotalExpenses() {
        return totalExpenses;
    }

    public void setTotalExpenses(int totalExpenses) {
        this.totalExpenses = totalExpenses;
    }

    public double getAvgExpenses() {
        return avgExpenses;
    }

    public void setAvgExpenses(double avgExpenses) {
        this.avgExpenses = avgExpenses;
    }
}
