import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

public class SearchCriterias {
    private final String LAST_NAME = "lastName";
    private final String PRODUCT_NAME = "productName";
    private final String MIN_TIMES = "minTimes";
    private final String MIN_EXPENSES = "minExpenses";
    private final String MAX_EXPENSES = "maxExpenses";
    private final String BAD_CUSTOMERS = "badCustomers";
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
        return LAST_NAME;
    }

    public String getPRODUCT_NAME() {
        return PRODUCT_NAME;
    }

    public String getMIN_TIMES() {
        return MIN_TIMES;
    }

    public String getMIN_EXPENSES() {
        return MIN_EXPENSES;
    }

    public String getMAX_EXPENSES() {
        return MAX_EXPENSES;
    }

    public String getBAD_CUSTOMERS() {
        return BAD_CUSTOMERS;
    }

    private void setParametersForCriterias(ArrayList<Map<String, Object>> arrCriterias) {
        for (Map<String, Object> map : arrCriterias) {
            if (map.containsKey(LAST_NAME)) {
                LinkedHashMap<String, Object> mapCustomerName = new LinkedHashMap<>();
                mapCustomerName.put(LAST_NAME, map.get(LAST_NAME));
                arrCustomersLastName.add(mapCustomerName);
            }
            if (map.containsKey(PRODUCT_NAME) && map.containsKey(MIN_TIMES)) {
                LinkedHashMap<String, Object> mapProduct = new LinkedHashMap<>();
                Object productName = map.get(PRODUCT_NAME).toString();
                Object minTimes = 0;
                try {
                    minTimes = (int) Double.parseDouble(map.get(MIN_TIMES).toString());
                } catch (NumberFormatException e) {
                    try {
                        ErrorMy errorMy = new ErrorMy();
                        errorMy.printError(ErrorsEnum.ERROR_JSON.getTitle());
                    } catch (IOException exception) {
                        System.out.println(ErrorsEnum.ERROR_FILE_WRITE.getTitle());
                    }

                    System.out.println(ErrorsEnum.ERROR.getTitle() + ErrorsEnum.ERROR_JSON.getTitle());
                    System.out.println(e.getMessage());
                    System.exit(1);
                }

                mapProduct.put(PRODUCT_NAME, productName);
                mapProduct.put(MIN_TIMES, minTimes);
                arrProductsAndTimes.add(mapProduct);
            }
            if (map.containsKey(MIN_EXPENSES) && map.containsKey(MIN_EXPENSES)) {
                LinkedHashMap<String, Object> mapExpenses = new LinkedHashMap<>();
                int minExpenses = 0;
                int maxExpenses = 0;
                try {
                    minExpenses = (int) Double.parseDouble(map.get(MIN_EXPENSES).toString());
                    maxExpenses = (int) Double.parseDouble(map.get(MAX_EXPENSES).toString());
                } catch (NumberFormatException e) {
                    try {
                        ErrorMy errorMy = new ErrorMy();
                        errorMy.printError(ErrorsEnum.ERROR_JSON.getTitle());
                    } catch (IOException exception) {
                        System.out.println(ErrorsEnum.ERROR_FILE_WRITE.getTitle());
                    }

                    System.out.println(ErrorsEnum.ERROR.getTitle() + ErrorsEnum.ERROR_JSON.getTitle());
                    System.out.println(e.getMessage());
                    System.exit(1);
                }

                mapExpenses.put(MIN_EXPENSES, minExpenses);
                mapExpenses.put(MAX_EXPENSES, maxExpenses);
                arrExpenses.add(mapExpenses);
            }
            if (map.containsKey(BAD_CUSTOMERS)) {
                LinkedHashMap<String, Object> mapBadCustomers = new LinkedHashMap<>();
                int badCustomers = 0;
                try {
                    badCustomers = (int) Double.parseDouble(map.get(BAD_CUSTOMERS).toString());
                } catch (NumberFormatException e) {
                    try {
                        ErrorMy errorMy = new ErrorMy();
                        errorMy.printError(ErrorsEnum.ERROR_JSON.getTitle());
                    } catch (IOException exception) {
                        System.out.println(ErrorsEnum.ERROR_FILE_WRITE.getTitle());
                    }

                    System.out.println(ErrorsEnum.ERROR.getTitle() + ErrorsEnum.ERROR_JSON.getTitle());
                    System.out.println(e.getMessage());
                    System.exit(1);
                }

                mapBadCustomers.put(BAD_CUSTOMERS, badCustomers);
                arrBadCustomers.add(mapBadCustomers);
            }
        }
    }

    public void setAllCriterias(String file) {
        //SearchFileReader searchFileReader = new SearchFileReader();
        SearchResultsGetter searchResultsGetter = new SearchResultsGetter();
        ArrayList<Map<String, Object>> arrCriterias = searchResultsGetter.getArrCriteriasFromJson(file);
        setParametersForCriterias(arrCriterias);
    }

    public void showAllCriterias() {
        System.out.println("arrCustomersLastName = " + arrCustomersLastName);
        System.out.println("arrProductsAndTimes = " + arrProductsAndTimes);
        System.out.println("arrExpenses = " + arrExpenses);
        System.out.println("arrBadCustomers = " + arrBadCustomers);
    }
}
