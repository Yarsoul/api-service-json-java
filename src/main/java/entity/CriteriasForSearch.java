package entity;

import service.ResultsGetterForSearch;
import service.SetterForSearch;
import enums.EnumForSearch;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class CriteriasForSearch {
    private ArrayList<HashMap<String, Object>> arrCustomersLastName = new ArrayList<>();
    private ArrayList<HashMap<String, Object>> arrProductsAndTimes = new ArrayList<>();
    private ArrayList<HashMap<String, Object>> arrExpenses = new ArrayList<>();
    private ArrayList<HashMap<String, Object>> arrBadCustomers = new ArrayList<>();

    public ArrayList<HashMap<String, Object>> getArrCustomersLastName() {
        return arrCustomersLastName;
    }

    public ArrayList<HashMap<String, Object>> getArrProductsAndTimes() {
        return arrProductsAndTimes;
    }

    public ArrayList<HashMap<String, Object>> getArrExpenses() {
        return arrExpenses;
    }

    public ArrayList<HashMap<String, Object>> getArrBadCustomers() {
        return arrBadCustomers;
    }

    public String getLAST_NAME() {
        return EnumForSearch.LAST_NAME.getTitle();
    }

    public String getPRODUCT_NAME() {
        return EnumForSearch.PRODUCT_NAME.getTitle();
    }

    public String getMIN_TIMES() { return EnumForSearch.MIN_TIMES.getTitle(); }

    public String getMIN_EXPENSES() {
        return EnumForSearch.MIN_EXPENSES.getTitle();
    }

    public String getMAX_EXPENSES() {
        return EnumForSearch.MAX_EXPENSES.getTitle();
    }

    public String getBAD_CUSTOMERS() {
        return EnumForSearch.BAD_CUSTOMERS.getTitle();
    }

    private void setParametersForCriterias(ArrayList<Map<String, Object>> arrCriterias) {
        SetterForSearch setterForSearch = new SetterForSearch();
        setterForSearch.setParameters(arrCriterias);
        arrCustomersLastName = setterForSearch.getArrCustomersLastName();
        arrProductsAndTimes = setterForSearch.getArrProductsAndTimes();
        arrExpenses = setterForSearch.getArrExpenses();
        arrBadCustomers = setterForSearch.getArrBadCustomers();
    }

    public void setAllCriterias(String file) {
        //controller.SearchFileReader searchFileReader = new controller.SearchFileReader();
        ResultsGetterForSearch resultsGetterForSearch = new ResultsGetterForSearch();
        ArrayList<Map<String, Object>> arrCriterias = resultsGetterForSearch.getArrCriteriasFromJson(file);
        setParametersForCriterias(arrCriterias);
    }

    public void showAllCriterias() {
        System.out.println("arrCustomersLastName = " + arrCustomersLastName);
        System.out.println("arrProductsAndTimes = " + arrProductsAndTimes);
        System.out.println("arrExpenses = " + arrExpenses);
        System.out.println("arrBadCustomers = " + arrBadCustomers);
    }
}
