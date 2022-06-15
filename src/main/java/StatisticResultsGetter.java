import java.io.IOException;
import java.sql.SQLException;
import java.time.Period;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;

public class StatisticResultsGetter {
    private final String NAME = "name";
    private final String EXPENSES = "expenses";
    ArrayList<Integer> indexesDelete = new ArrayList<>();


    // Получаем готовый класс с результатами:
    public StatisticResultsFinish getResults(StatisticCriterias statisticCriterias) {
        StatisticResultsFinish result = new StatisticResultsFinish();

        // Получаем кол-во дней между датами:
        int totalDays = getTotalDays(statisticCriterias);
        result.setTotalDays(totalDays);

        // Получаем массив покупок по каждому покупателю:
        ArrayList<StatisticResultsPurchases> statResPurchases = getArrStatResultFinish(statisticCriterias);
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
    private int getTotalDays(StatisticCriterias statisticCriterias) {
        if (statisticCriterias.getStartDate() != null && statisticCriterias.getEndDate() != null) {
            Period period = Period.between(statisticCriterias.getStartDate(), statisticCriterias.getEndDate());
            int totalDays = period.getDays();
            return totalDays;
            //System.out.println("days = " + totalDays);
        } else {
            System.out.println("Параметры дат не указаны");
            return 0;
        }
    }

    // Получает общий массив с покупками за определенный период по каждому покупателю (товар, сумма покупок товара):
    private ArrayList<Purchase> getArrPurchases(StatisticCriterias statisticCriterias) {
        ArrayList<Purchase> purchases = new ArrayList<>();
        if (statisticCriterias.getStartDate() != null && statisticCriterias.getEndDate() != null) {
            try {
                purchases = DBHelper.getStatFromDb(statisticCriterias.getStartDate(), statisticCriterias.getEndDate());
            } catch (SQLException e) {
                try {
                    ErrorMy errorMy = new ErrorMy();
                    errorMy.printError(ErrorsEnum.ERROR_DATABASE.getTitle());
                } catch (IOException exception) {
                    System.out.println(ErrorsEnum.ERROR.getTitle() + ErrorsEnum.ERROR_FILE_WRITE.getTitle());
                }

                System.out.println(ErrorsEnum.ERROR.getTitle() + ErrorsEnum.ERROR_DATABASE.getTitle());
                System.exit(1);
            }
        }
        if (purchases.isEmpty()) {
            try {
                ErrorMy errorMy = new ErrorMy();
                errorMy.printError(ErrorsEnum.ERROR_DATE_NONE.getTitle());
            } catch (IOException exception) {
                System.out.println(ErrorsEnum.ERROR_FILE_WRITE.getTitle());
            }

            System.out.println(ErrorsEnum.ERROR.getTitle() + ErrorsEnum.ERROR_DATE_NONE.getTitle());
            System.exit(1);
        }
        return purchases;
    }

    // Получает массив покупок с распределенными результатами статистики:
    private ArrayList<StatisticResultsPurchases> getArrStatisticResults(ArrayList<Purchase> arrPurchases) {
        ArrayList<StatisticResultsPurchases> arrResults = new ArrayList<>();
        for (Purchase purchase : arrPurchases) {
            StatisticResultsPurchases statisticResultsPurchases = new StatisticResultsPurchases();
            LinkedHashMap<String, Object> hashMapPurchases = new LinkedHashMap<>();
            hashMapPurchases.put(NAME, purchase.getProductName());
            hashMapPurchases.put(EXPENSES, purchase.getExpenses());
            statisticResultsPurchases.addPurchase(hashMapPurchases);
            statisticResultsPurchases.setName(purchase.getCustomerName());
            arrResults.add(statisticResultsPurchases);
        }
        return arrResults;
    }

    // Распределяет дублирующиеся записи о покупках по нужным покупателям внутри массива покупателей:
    private void distributeStatisticResults(ArrayList<StatisticResultsPurchases> arrStatisticResultsPurchases) {
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
    private void updateStatisticResults(ArrayList<StatisticResultsPurchases> arrStatisticResultsPurchases) {
        for (int i = 0; i < indexesDelete.size(); i++) {
            int index = indexesDelete.get(i) - i;
            arrStatisticResultsPurchases.remove(index);
        }
        indexesDelete.clear();
    }

    // Получает итоговый массив покупок, распределенный по покупателям:
    private ArrayList<StatisticResultsPurchases> getArrStatResultFinish(StatisticCriterias statisticCriterias) {
        // Получаем общий массив с покупками за определенный период по каждому покупателю (товар, сумма покупок товара):
        ArrayList<Purchase> arrPurchases = getArrPurchases(statisticCriterias);
        // Получаем массив покупок с распределенными результатами статистики:
        ArrayList<StatisticResultsPurchases> arrStatisticResultsPurchases = getArrStatisticResults(arrPurchases);
        // Распределяем дублирующиеся записи о покупках по нужным покупателям внутри массива:
        distributeStatisticResults(arrStatisticResultsPurchases);
        // Удаляем лишние повторяющиеся записи из массива:
        updateStatisticResults(arrStatisticResultsPurchases);
        // Считаем и добавляем общее кол-во покупок у каждого покупателя:
        setCustomerTotalExpenses(arrStatisticResultsPurchases);

        return arrStatisticResultsPurchases;
    }

    // Считает и инициализирует общее кол-во покупок у каждого покупателя:
    private void setCustomerTotalExpenses(ArrayList<StatisticResultsPurchases> arrStatisticResultsPurchases) {
        for (int i = 0; i < arrStatisticResultsPurchases.size(); i++) {
            int totalExpenses = 0;
            for (int j = 0; j < arrStatisticResultsPurchases.get(i).getPurchases().size(); j++) {
                totalExpenses += (int) arrStatisticResultsPurchases.get(i).getPurchases().get(j).get(EXPENSES);
            }
            arrStatisticResultsPurchases.get(i).setTotalExpenses(totalExpenses);
        }
    }

    // Получает общую сумму покупок за период:
    private int getTotalExpenses(ArrayList<StatisticResultsPurchases> arrStatisticResultsPurchases) {
        int totalExpenses = 0;
        for (StatisticResultsPurchases customer : arrStatisticResultsPurchases) {
            totalExpenses += customer.getTotalExpenses();
        }
        return totalExpenses;
    }

    // Получает среднюю сумму затрат за период:
    private double getAvgExpenses(ArrayList<StatisticResultsPurchases> arrStatisticResultsPurchases) {
        double totalExpenses = (double) getTotalExpenses(arrStatisticResultsPurchases);
        int count = arrStatisticResultsPurchases.size();
        double avgExpenses = totalExpenses / count;
        return avgExpenses;
    }
}
