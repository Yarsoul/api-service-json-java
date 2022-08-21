package service;

import entity.Customer;
import entity.CriteriasForSearch;
import entity.ResultsForSearch;
import entity.ResultsFinishForSearch;
import repository.DBHelper;
import errors.ErrorMy;
import enums.EnumForErrors;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ResultsGetterForSearch {
    private final String CRITERIAS = "criterias";

    private ArrayList<Map<String, Object>> getArrCriteriasFromMap(Map<String, ArrayList<Map<String, Object>>> mapFromJson) {
        if (mapFromJson.containsKey(CRITERIAS)) {
            ArrayList<Map<String, Object>> arrCriterias = mapFromJson.get(CRITERIAS);
            return arrCriterias;
        } else {
            try {
                ErrorMy errorMy = new ErrorMy();
                errorMy.printError(EnumForErrors.ERROR_CRITERIA.getTitle());
            } catch (IOException exception) {
                System.out.println(EnumForErrors.ERROR_FILE_WRITE.getTitle());
            }

            System.out.println(EnumForErrors.ERROR.getTitle() + EnumForErrors.ERROR_CRITERIA.getTitle());
            System.exit(1);
            return null;
        }
    }

    public ArrayList<Map<String, Object>> getArrCriteriasFromJson(String file) {
        FileReaderForSearch fileReaderForSearch = new FileReaderForSearch();
        Map<String, ArrayList<Map<String, Object>>> mapFromJson = fileReaderForSearch.getMapFromJson(file);
        ArrayList<Map<String, Object>> arrCriterias = getArrCriteriasFromMap(mapFromJson);
        return arrCriterias;
    }

    // Получает готовый класс с результатами:
    public ResultsFinishForSearch getFinishResults(CriteriasForSearch criteriasForSearch) throws SQLException, IOException {
        ArrayList<ResultsForSearch> arrResults = getArrResults(criteriasForSearch);

        ResultsFinishForSearch resultsFinish = new ResultsFinishForSearch();
        resultsFinish.setResults(arrResults);
        return resultsFinish;
    }

    // Получает массив с покупателями:
    private ArrayList<ResultsForSearch> getArrResults(CriteriasForSearch criteriasForSearch) throws SQLException, IOException {
        ArrayList<ResultsForSearch> arrResults = new ArrayList<>();

        // Добавляем в результирующий массив покупателей, найденных по фамилии:
        addCustomersFindLastName(criteriasForSearch, arrResults);
        // Добавляем покупателей, купивших определенный товар не менее, чем указанное число раз:
        addCustomersProductsAndTimes(criteriasForSearch, arrResults);
        // Добавляем покупателей, у которых общая стоимость всех покупок за всё время попадает в заданный интервал:
        addCustomersSumExpenses(criteriasForSearch, arrResults);
        // Добавляет покупателей, купивших меньше всего товаров. Возвращается не более, чем указанное число покупателей:
        addBadCustomers(criteriasForSearch, arrResults);

        return arrResults;
    }

    // Добавляет в результирующий массив покупателей, найденных по фамилии:
    private void addCustomersFindLastName(CriteriasForSearch criteriasForSearch, ArrayList<ResultsForSearch> arrResults) throws SQLException, IOException {
        ArrayList<HashMap<String, Object>> arrCustomersLastName = criteriasForSearch.getArrCustomersLastName();
        if (arrCustomersLastName.size() != 0) {
            for (int i = 0; i < arrCustomersLastName.size(); i++) {
                ResultsForSearch resultsForSearch = new ResultsForSearch();
                // Добавляем критерии:
                resultsForSearch.putCriteria(arrCustomersLastName.get(i));
                // Получаем фамилию для поиска:
                String lastName = arrCustomersLastName.get(i).get(criteriasForSearch.getLAST_NAME()).toString();
                // Получаем массив с покупателями по искомой Фамилии:
                ArrayList<Customer> customers = DBHelper.searchCustomersFromDb(lastName);
                // Добавляем результаты:
                resultsForSearch.addResults(customers);
                arrResults.add(resultsForSearch);
            }
        }
    }

    // Добавляет покупателей, купивших определенный товар не менее, чем указанное число раз:
    private void addCustomersProductsAndTimes(CriteriasForSearch criteriasForSearch, ArrayList<ResultsForSearch> arrResults) throws SQLException {
        ArrayList<HashMap<String, Object>> arrProductsAndTimes = criteriasForSearch.getArrProductsAndTimes();
        if (arrProductsAndTimes.size() != 0) {
            for (int i = 0; i < arrProductsAndTimes.size(); i++) {
                ResultsForSearch resultsForSearch = new ResultsForSearch();
                // Добавляем критерии:
                resultsForSearch.putCriteria(arrProductsAndTimes.get(i));
                // Получаем критерии для поиска:
                String productName = arrProductsAndTimes.get(i).get(criteriasForSearch.getPRODUCT_NAME()).toString();
                int minTimes = (int) arrProductsAndTimes.get(i).get(criteriasForSearch.getMIN_TIMES());
                // Получаем массив с покупателями по критериям:
                ArrayList<Customer> customers = DBHelper.searchCustomersPurchaseTimesFromDb(productName, minTimes);
                // Добавляем результаты:
                resultsForSearch.addResults(customers);
                arrResults.add(resultsForSearch);
            }
        }
    }

    // Добавляет покупателей, у которых общая стоимость всех покупок за всё время попадает в заданный интервал:
    private void addCustomersSumExpenses(CriteriasForSearch criteriasForSearch, ArrayList<ResultsForSearch> arrResults) throws SQLException {
        ArrayList<HashMap<String, Object>> arrExpenses = criteriasForSearch.getArrExpenses();
        if (arrExpenses.size() != 0) {
            for (int i = 0; i < arrExpenses.size(); i++) {
                ResultsForSearch resultsForSearch = new ResultsForSearch();
                // Добавляем критерии:
                resultsForSearch.putCriteria(arrExpenses.get(i));
                // Получаем критерии для поиска:
                int minExpenses = (int) arrExpenses.get(i).get(criteriasForSearch.getMIN_EXPENSES());
                int maxExpenses = (int) arrExpenses.get(i).get(criteriasForSearch.getMAX_EXPENSES());
                // Получаем массив с покупателями по критериям:
                ArrayList<Customer> customers = DBHelper.searchCustomersExpensesFromDb(minExpenses, maxExpenses);
                // Добавляем результаты:
                resultsForSearch.addResults(customers);
                arrResults.add(resultsForSearch);
            }
        }
    }

    // Добавляет покупателей, купивших меньше всего товаров. Возвращается не более, чем указанное число покупателей:
    private void addBadCustomers(CriteriasForSearch criteriasForSearch, ArrayList<ResultsForSearch> arrResults) {
        ArrayList<HashMap<String, Object>> arrBadCustomers = criteriasForSearch.getArrBadCustomers();
        if (arrBadCustomers.size() != 0) {
            for (int i = 0; i < arrBadCustomers.size(); i++) {
                ResultsForSearch resultsForSearch = new ResultsForSearch();
                // Добавляем критерии:
                resultsForSearch.putCriteria(arrBadCustomers.get(i));
                // Получаем критерии для поиска:
                int badCustomers = (int) arrBadCustomers.get(i).get(criteriasForSearch.getBAD_CUSTOMERS());
                // Получаем массив с покупателями по критериям:
                ArrayList<Customer> customers = DBHelper.searchBadCustomersFromDb(badCustomers);
                // Добавляем результаты:
                resultsForSearch.addResults(customers);
                arrResults.add(resultsForSearch);
            }
        }
    }
}
