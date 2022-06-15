import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.HashMap;

public class StatisticCriterias {
    private final String START_DATE = "startDate";
    private final String END_DATE = "endDate";
    private LocalDate startDate;
    private LocalDate endDate;

    public LocalDate getStartDate() {
        return startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setAllCriterias(String fileName) {
        StatisticFileReader statisticFileReader = new StatisticFileReader();
        HashMap<String, String> mapFromJson = statisticFileReader.getMapFromJson(fileName);
        if (mapFromJson.containsKey(START_DATE) && mapFromJson.containsKey(END_DATE)) {
            String strStartDate = mapFromJson.get(START_DATE);
            String strEndDate = mapFromJson.get(END_DATE);
            try {
                startDate = LocalDate.parse(strStartDate);
                endDate = LocalDate.parse(strEndDate);
            } catch (DateTimeParseException e) {
                try {
                    ErrorMy errorMy = new ErrorMy();
                    errorMy.printError(ErrorsEnum.ERROR_DATE.getTitle());
                } catch (IOException exception) {
                    System.out.println(ErrorsEnum.ERROR_FILE_WRITE.getTitle());
                    System.out.println(exception.getMessage());
                }

                System.out.println(ErrorsEnum.ERROR.getTitle() + ErrorsEnum.ERROR_DATE.getTitle());
                System.out.println(e.getMessage());
                System.exit(1);
            }
        } else {
            try {
                ErrorMy errorMy = new ErrorMy();
                errorMy.printError(ErrorsEnum.ERROR_DATE_NAMES.getTitle());
            } catch (IOException exception) {
                System.out.println(ErrorsEnum.ERROR_FILE_WRITE.getTitle());
                System.out.println(exception.getMessage());
            }

            System.out.println(ErrorsEnum.ERROR.getTitle() + ErrorsEnum.ERROR_DATE_NAMES.getTitle());
            System.exit(1);
        }
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
