import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class SearchResultsGetter {
    private final String CRITERIAS = "criterias";

    private ArrayList<Map<String, Object>> getArrCriteriasFromMap(Map<String, ArrayList<Map<String, Object>>> mapFromJson) {
        if (mapFromJson.containsKey(CRITERIAS)) {
            ArrayList<Map<String, Object>> arrCriterias = mapFromJson.get(CRITERIAS);
            return arrCriterias;
        } else {
            try {
                ErrorMy errorMy = new ErrorMy();
                errorMy.printError(ErrorsEnum.ERROR_CRITERIA.getTitle());
            } catch (IOException exception) {
                System.out.println(ErrorsEnum.ERROR_FILE_WRITE.getTitle());
            }

            System.out.println(ErrorsEnum.ERROR.getTitle() + ErrorsEnum.ERROR_CRITERIA.getTitle());
            System.exit(1);
            return null;
        }
    }

    public ArrayList<Map<String, Object>> getArrCriteriasFromJson(String file) {
        SearchFileReader searchFileReader = new SearchFileReader();
        Map<String, ArrayList<Map<String, Object>>> mapFromJson = searchFileReader.getMapFromJson(file);
        ArrayList<Map<String, Object>> arrCriterias = getArrCriteriasFromMap(mapFromJson);
        return arrCriterias;
    }

    // Получает готовый класс с результатами:
    public SearchResultsFinish getFinishResults(SearchCriterias searchCriterias) throws SQLException, IOException {
        ArrayList<SearchResults> arrResults = getArrResults(searchCriterias);

        SearchResultsFinish resultsFinish = new SearchResultsFinish();
        resultsFinish.setResults(arrResults);
        return resultsFinish;
    }

    // Получает массив с покупателями:
    private ArrayList<SearchResults> getArrResults(SearchCriterias searchCriterias) throws SQLException, IOException {
        ArrayList<SearchResults> arrResults = new ArrayList<>();

        // Добавляем в результирующий массив покупателей, найденных по фамилии:
        addCustomersFindLastName(searchCriterias, arrResults);
        // Добавляем покупателей, купивших определенный товар не менее, чем указанное число раз:
        addCustomersProductsAndTimes(searchCriterias, arrResults);
        // Добавляем покупателей, у которых общая стоимость всех покупок за всё время попадает в заданный интервал:
        addCustomersSumExpenses(searchCriterias, arrResults);
        // Добавляет покупателей, купивших меньше всего товаров. Возвращается не более, чем указанное число покупателей:
        addBadCustomers(searchCriterias, arrResults);

        return arrResults;
    }

    // Добавляет в результирующий массив покупателей, найденных по фамилии:
    private void addCustomersFindLastName(SearchCriterias searchCriterias, ArrayList<SearchResults> arrResults) throws SQLException, IOException {
        ArrayList<HashMap<String, Object>> arrCustomersLastName = searchCriterias.getArrCustomersLastName();
        if (arrCustomersLastName.size() != 0) {
            for (int i = 0; i < arrCustomersLastName.size(); i++) {
                SearchResults searchResults = new SearchResults();
                // Добавляем критерии:
                searchResults.putCriteria(arrCustomersLastName.get(i));
                // Получаем фамилию для поиска:
                String lastName = arrCustomersLastName.get(i).get(searchCriterias.getLAST_NAME()).toString();
                // Получаем массив с покупателями по искомой Фамилии:
                ArrayList<Customer> customers = DBHelper.searchCustomersFromDb(lastName);
                // Добавляем результаты:
                searchResults.addResults(customers);
                arrResults.add(searchResults);
            }
        }
    }

    // Добавляет покупателей, купивших определенный товар не менее, чем указанное число раз:
    private void addCustomersProductsAndTimes(SearchCriterias searchCriterias, ArrayList<SearchResults> arrResults) throws SQLException {
        ArrayList<HashMap<String, Object>> arrProductsAndTimes = searchCriterias.getArrProductsAndTimes();
        if (arrProductsAndTimes.size() != 0) {
            for (int i = 0; i < arrProductsAndTimes.size(); i++) {
                SearchResults searchResults = new SearchResults();
                // Добавляем критерии:
                searchResults.putCriteria(arrProductsAndTimes.get(i));
                // Получаем критерии для поиска:
                String productName = arrProductsAndTimes.get(i).get(searchCriterias.getPRODUCT_NAME()).toString();
                int minTimes = (int) arrProductsAndTimes.get(i).get(searchCriterias.getMIN_TIMES());
                // Получаем массив с покупателями по критериям:
                ArrayList<Customer> customers = DBHelper.searchCustomersPurchaseTimesFromDb(productName, minTimes);
                // Добавляем результаты:
                searchResults.addResults(customers);
                arrResults.add(searchResults);
            }
        }
    }

    // Добавляет покупателей, у которых общая стоимость всех покупок за всё время попадает в заданный интервал:
    private void addCustomersSumExpenses(SearchCriterias searchCriterias, ArrayList<SearchResults> arrResults) throws SQLException {
        ArrayList<HashMap<String, Object>> arrExpenses = searchCriterias.getArrExpenses();
        if (arrExpenses.size() != 0) {
            for (int i = 0; i < arrExpenses.size(); i++) {
                SearchResults searchResults = new SearchResults();
                // Добавляем критерии:
                searchResults.putCriteria(arrExpenses.get(i));
                // Получаем критерии для поиска:
                int minExpenses = (int) arrExpenses.get(i).get(searchCriterias.getMIN_EXPENSES());
                int maxExpenses = (int) arrExpenses.get(i).get(searchCriterias.getMAX_EXPENSES());
                // Получаем массив с покупателями по критериям:
                ArrayList<Customer> customers = DBHelper.searchCustomersExpensesFromDb(minExpenses, maxExpenses);
                // Добавляем результаты:
                searchResults.addResults(customers);
                arrResults.add(searchResults);
            }
        }
    }

    // Добавляет покупателей, купивших меньше всего товаров. Возвращается не более, чем указанное число покупателей:
    private void addBadCustomers(SearchCriterias searchCriterias, ArrayList<SearchResults> arrResults) {
        ArrayList<HashMap<String, Object>> arrBadCustomers = searchCriterias.getArrBadCustomers();
        if (arrBadCustomers.size() != 0) {
            for (int i = 0; i < arrBadCustomers.size(); i++) {
                SearchResults searchResults = new SearchResults();
                // Добавляем критерии:
                searchResults.putCriteria(arrBadCustomers.get(i));
                // Получаем критерии для поиска:
                int badCustomers = (int) arrBadCustomers.get(i).get(searchCriterias.getBAD_CUSTOMERS());
                // Получаем массив с покупателями по критериям:
                ArrayList<Customer> customers = DBHelper.searchBadCustomersFromDb(badCustomers);
                // Добавляем результаты:
                searchResults.addResults(customers);
                arrResults.add(searchResults);
            }
        }
    }
}
