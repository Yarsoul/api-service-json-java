package service;

import errors.ErrorMy;
import enums.EnumForErrors;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import static enums.EnumForSearch.*;

public class SetterForSearch {
    private ArrayList<HashMap<String, Object>> arrCustomersLastName = new ArrayList<>();
    private ArrayList<HashMap<String, Object>> arrProductsAndTimes = new ArrayList<>();
    private ArrayList<HashMap<String, Object>> arrExpenses = new ArrayList<>();
    private ArrayList<HashMap<String, Object>> arrBadCustomers = new ArrayList<>();

    public void setParameters(ArrayList<Map<String, Object>> arrCriterias) {
        String lastNameStr = LAST_NAME.getTitle();
        String productNameStr = PRODUCT_NAME.getTitle();
        String minTimesStr = MIN_TIMES.getTitle();
        String minExpensesStr = MIN_EXPENSES.getTitle();
        String maxExpensesStr = MAX_EXPENSES.getTitle();
        String badCustomersStr = BAD_CUSTOMERS.getTitle();

        for (Map<String, Object> map : arrCriterias) {
            if (map.containsKey(lastNameStr)) {
                LinkedHashMap<String, Object> mapCustomerName = new LinkedHashMap<>();
                mapCustomerName.put(lastNameStr, map.get(lastNameStr));
                arrCustomersLastName.add(mapCustomerName);
            }
            if (map.containsKey(productNameStr) && map.containsKey(minTimesStr)) {
                LinkedHashMap<String, Object> mapProduct = new LinkedHashMap<>();
                Object productNameObj = map.get(productNameStr).toString();
                Object minTimesObj = 0;
                try {
                    minTimesObj = (int) Double.parseDouble(map.get(minTimesStr).toString());
                } catch (NumberFormatException e) {
                    try {
                        ErrorMy errorMy = new ErrorMy();
                        errorMy.printError(EnumForErrors.ERROR_JSON.getTitle());
                    } catch (IOException exception) {
                        System.out.println(EnumForErrors.ERROR_FILE_WRITE.getTitle());
                    }

                    System.out.println(EnumForErrors.ERROR.getTitle() + EnumForErrors.ERROR_JSON.getTitle());
                    System.out.println(e.getMessage());
                    System.exit(1);
                }

                mapProduct.put(productNameStr, productNameObj);
                mapProduct.put(minTimesStr, minTimesObj);
                arrProductsAndTimes.add(mapProduct);
            }
            if (map.containsKey(minExpensesStr) && map.containsKey(maxExpensesStr)) {
                LinkedHashMap<String, Object> mapExpenses = new LinkedHashMap<>();
                int minExpenses = 0;
                int maxExpenses = 0;
                try {
                    minExpenses = (int) Double.parseDouble(map.get(minExpensesStr).toString());
                    maxExpenses = (int) Double.parseDouble(map.get(maxExpensesStr).toString());
                } catch (NumberFormatException e) {
                    try {
                        ErrorMy errorMy = new ErrorMy();
                        errorMy.printError(EnumForErrors.ERROR_JSON.getTitle());
                    } catch (IOException exception) {
                        System.out.println(EnumForErrors.ERROR_FILE_WRITE.getTitle());
                    }

                    System.out.println(EnumForErrors.ERROR.getTitle() + EnumForErrors.ERROR_JSON.getTitle());
                    System.out.println(e.getMessage());
                    System.exit(1);
                }

                mapExpenses.put(String.valueOf(MIN_EXPENSES), minExpenses);
                mapExpenses.put(String.valueOf(MAX_EXPENSES), maxExpenses);
                arrExpenses.add(mapExpenses);

            }
            if (map.containsKey(badCustomersStr)) {
                LinkedHashMap<String, Object> mapBadCustomers = new LinkedHashMap<>();
                int badCustomers = 0;
                try {
                    badCustomers = (int) Double.parseDouble(map.get(badCustomersStr).toString());
                } catch (NumberFormatException e) {
                    try {
                        ErrorMy errorMy = new ErrorMy();
                        errorMy.printError(EnumForErrors.ERROR_JSON.getTitle());
                    } catch (IOException exception) {
                        System.out.println(EnumForErrors.ERROR_FILE_WRITE.getTitle());
                    }

                    System.out.println(EnumForErrors.ERROR.getTitle() + EnumForErrors.ERROR_JSON.getTitle());
                    System.out.println(e.getMessage());
                    System.exit(1);
                }

                mapBadCustomers.put(String.valueOf(BAD_CUSTOMERS), badCustomers);
                arrBadCustomers.add(mapBadCustomers);
            }
        }
    }

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
}
