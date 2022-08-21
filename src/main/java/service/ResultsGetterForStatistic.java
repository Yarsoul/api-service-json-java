package service;

import entity.CriteriasForStatistic;
import entity.Purchase;
import entity.ResultsFinishForStatistic;
import entity.ResultsForStatistic;
import repository.DBHelper;
import errors.ErrorMy;
import enums.EnumForErrors;

import java.io.IOException;
import java.sql.SQLException;
import java.time.Period;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;

public class ResultsGetterForStatistic {
    private final String NAME = "name";
    private final String EXPENSES = "expenses";
    ArrayList<Integer> indexesDelete = new ArrayList<>();


    // Получаем готовый класс с результатами:
    public ResultsFinishForStatistic getResults(CriteriasForStatistic criteriasForStatistic) {
        ResultsFinishForStatistic result = new ResultsFinishForStatistic();

        // Получаем кол-во дней между датами:
        int totalDays = getTotalDays(criteriasForStatistic);
        result.setTotalDays(totalDays);

        // Получаем массив покупок по каждому покупателю:
        ArrayList<ResultsForStatistic> statResPurchases = getArrStatResultFinish(criteriasForStatistic);
        result.setCustomers(statResPurchases);

        // Получаем общую сумму покупок за период из массива покупок:
        int totalExpenses = getTotalExpenses(statResPurchases);
        result.setTotalExpenses(totalExpenses);

        // Получаем среднюю сумму затрат за период из массива покупок:
        double avgExpenses = getAvgExpenses(statResPurchases);
        result.setAvgExpenses(avgExpenses);

        return result;
    }


    // Получает кол-во дней между входящими датами:
    private int getTotalDays(CriteriasForStatistic criteriasForStatistic) {
        if (criteriasForStatistic.getStartDate() != null && criteriasForStatistic.getEndDate() != null) {
            Period period = Period.between(criteriasForStatistic.getStartDate(), criteriasForStatistic.getEndDate());
            int totalDays = period.getDays();
            return totalDays;
            //System.out.println("days = " + totalDays);
        } else {
            System.out.println("Параметры дат не указаны");
            return 0;
        }
    }

    // Получает общий массив с покупками за определенный период по каждому покупателю (товар, сумма покупок товара):
    private ArrayList<Purchase> getArrPurchases(CriteriasForStatistic criteriasForStatistic) {
        ArrayList<Purchase> purchases = new ArrayList<>();
        if (criteriasForStatistic.getStartDate() != null && criteriasForStatistic.getEndDate() != null) {
            try {
                purchases = DBHelper.getStatFromDb(criteriasForStatistic.getStartDate(), criteriasForStatistic.getEndDate());
            } catch (SQLException e) {
                try {
                    ErrorMy errorMy = new ErrorMy();
                    errorMy.printError(EnumForErrors.ERROR_DATABASE.getTitle());
                } catch (IOException exception) {
                    System.out.println(EnumForErrors.ERROR.getTitle() + EnumForErrors.ERROR_FILE_WRITE.getTitle());
                }

                System.out.println(EnumForErrors.ERROR.getTitle() + EnumForErrors.ERROR_DATABASE.getTitle());
                System.exit(1);
            }
        }
        if (purchases.isEmpty()) {
            try {
                ErrorMy errorMy = new ErrorMy();
                errorMy.printError(EnumForErrors.ERROR_DATE_NONE.getTitle());
            } catch (IOException exception) {
                System.out.println(EnumForErrors.ERROR_FILE_WRITE.getTitle());
            }

            System.out.println(EnumForErrors.ERROR.getTitle() + EnumForErrors.ERROR_DATE_NONE.getTitle());
            System.exit(1);
        }
        return purchases;
    }

    // Получает массив покупок с распределенными результатами статистики:
    private ArrayList<ResultsForStatistic> getArrStatisticResults(ArrayList<Purchase> arrPurchases) {
        ArrayList<ResultsForStatistic> arrResults = new ArrayList<>();
        for (Purchase purchase : arrPurchases) {
            ResultsForStatistic resultsForStatistic = new ResultsForStatistic();
            LinkedHashMap<String, Object> hashMapPurchases = new LinkedHashMap<>();
            hashMapPurchases.put(NAME, purchase.getProductName());
            hashMapPurchases.put(EXPENSES, purchase.getExpenses());
            resultsForStatistic.addPurchase(hashMapPurchases);
            resultsForStatistic.setName(purchase.getCustomerName());
            arrResults.add(resultsForStatistic);
        }
        return arrResults;
    }

    // Распределяет дублирующиеся записи о покупках по нужным покупателям внутри массива покупателей:
    private void distributeStatisticResults(ArrayList<ResultsForStatistic> arrStatisticResultsPurchases) {
        int count = 0;
        LinkedHashSet<String> linkedHashSet = new LinkedHashSet<>();
        for (int i = 0; i < arrStatisticResultsPurchases.size(); i++) {
            if (linkedHashSet.add(arrStatisticResultsPurchases.get(i).getName())) {
                count = i;
            } else {
                arrStatisticResultsPurchases.get(count).addPurchase(arrStatisticResultsPurchases.get(i).getPurchases().get(0));
                indexesDelete.add(i);
            }
        }
    }

    // Удаляет лишние повторяющиеся записи из массива покупателей:
    private void updateStatisticResults(ArrayList<ResultsForStatistic> arrStatisticResultsPurchases) {
        for (int i = 0; i < indexesDelete.size(); i++) {
            int index = indexesDelete.get(i) - i;
            arrStatisticResultsPurchases.remove(index);
        }
        indexesDelete.clear();
    }

    // Получает итоговый массив покупок, распределенный по покупателям:
    private ArrayList<ResultsForStatistic> getArrStatResultFinish(CriteriasForStatistic criteriasForStatistic) {
        // Получаем общий массив с покупками за определенный период по каждому покупателю (товар, сумма покупок товара):
        ArrayList<Purchase> arrPurchases = getArrPurchases(criteriasForStatistic);
        // Получаем массив покупок с распределенными результатами статистики:
        ArrayList<ResultsForStatistic> arrStatisticResultsPurchases = getArrStatisticResults(arrPurchases);
        // Распределяем дублирующиеся записи о покупках по нужным покупателям внутри массива:
        distributeStatisticResults(arrStatisticResultsPurchases);
        // Удаляем лишние повторяющиеся записи из массива:
        updateStatisticResults(arrStatisticResultsPurchases);
        // Считаем и добавляем общее кол-во покупок у каждого покупателя:
        setCustomerTotalExpenses(arrStatisticResultsPurchases);

        return arrStatisticResultsPurchases;
    }

    // Считает и инициализирует общее кол-во покупок у каждого покупателя:
    private void setCustomerTotalExpenses(ArrayList<ResultsForStatistic> arrStatisticResultsPurchases) {
        for (int i = 0; i < arrStatisticResultsPurchases.size(); i++) {
            int totalExpenses = 0;
            for (int j = 0; j < arrStatisticResultsPurchases.get(i).getPurchases().size(); j++) {
                totalExpenses += (int) arrStatisticResultsPurchases.get(i).getPurchases().get(j).get(EXPENSES);
            }
            arrStatisticResultsPurchases.get(i).setTotalExpenses(totalExpenses);
        }
    }

    // Получает общую сумму покупок за период:
    private int getTotalExpenses(ArrayList<ResultsForStatistic> arrStatisticResultsPurchases) {
        int totalExpenses = 0;
        for (ResultsForStatistic customer : arrStatisticResultsPurchases) {
            totalExpenses += customer.getTotalExpenses();
        }
        return totalExpenses;
    }

    // Получает среднюю сумму затрат за период:
    private double getAvgExpenses(ArrayList<ResultsForStatistic> arrStatisticResultsPurchases) {
        double totalExpenses = (double) getTotalExpenses(arrStatisticResultsPurchases);
        int count = arrStatisticResultsPurchases.size();
        double avgExpenses = totalExpenses / count;
        return avgExpenses;
    }
}
