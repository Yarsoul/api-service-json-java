package entity;

import service.SetterForStatistic;

import java.time.LocalDate;

public class CriteriasForStatistic {
    private LocalDate startDate;
    private LocalDate endDate;

    public LocalDate getStartDate() {
        return startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setAllCriterias(String fileName) {
        SetterForStatistic setterForStatistic = new SetterForStatistic();
        setterForStatistic.setAllCriterias(fileName);
        startDate = setterForStatistic.getStartDate();
        endDate = setterForStatistic.getEndDate();
    }

    public void showAllCriterias() {
        if (startDate != null && endDate != null) {
            System.out.println("startDate = " + startDate);
            System.out.println("endDate = " + endDate);
        } else {
            System.out.println("Параметры даты не указаны");
        }
    }
}
