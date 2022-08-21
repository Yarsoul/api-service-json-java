package entity;

import java.util.ArrayList;

public class ResultsFinishForStatistic {
    private String type = "stat";
    private int totalDays;
    private ArrayList<ResultsForStatistic> customers = new ArrayList<>();
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

    public ArrayList<ResultsForStatistic> getCustomers() {
        return customers;
    }

    public void setCustomers(ArrayList<ResultsForStatistic> customers) {
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
