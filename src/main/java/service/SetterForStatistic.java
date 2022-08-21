package service;

import errors.ErrorMy;
import enums.EnumForErrors;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.HashMap;

public class SetterForStatistic {
    private final String START_DATE = "startDate";
    private final String END_DATE = "endDate";
    LocalDate startDate;
    LocalDate endDate;

    public void setAllCriterias(String fileName) {
        FileReaderForStatistic fileReaderForStatistic = new FileReaderForStatistic();
        HashMap<String, String> mapFromJson = fileReaderForStatistic.getMapFromJson(fileName);

        if (mapFromJson.containsKey(START_DATE) && mapFromJson.containsKey(END_DATE)) {
            String strStartDate = mapFromJson.get(START_DATE);
            String strEndDate = mapFromJson.get(END_DATE);
            try {
                startDate = LocalDate.parse(strStartDate);
                endDate = LocalDate.parse(strEndDate);
            } catch (DateTimeParseException e) {
                try {
                    ErrorMy errorMy = new ErrorMy();
                    errorMy.printError(EnumForErrors.ERROR_DATE.getTitle());
                } catch (IOException exception) {
                    System.out.println(EnumForErrors.ERROR_FILE_WRITE.getTitle());
                    System.out.println(exception.getMessage());
                }

                System.out.println(EnumForErrors.ERROR.getTitle() + EnumForErrors.ERROR_DATE.getTitle());
                System.out.println(e.getMessage());
                System.exit(1);
            }
        } else {
            try {
                ErrorMy errorMy = new ErrorMy();
                errorMy.printError(EnumForErrors.ERROR_DATE_NAMES.getTitle());
            } catch (IOException exception) {
                System.out.println(EnumForErrors.ERROR_FILE_WRITE.getTitle());
                System.out.println(exception.getMessage());
            }

            System.out.println(EnumForErrors.ERROR.getTitle() + EnumForErrors.ERROR_DATE_NAMES.getTitle());
            System.exit(1);
        }
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }
}
