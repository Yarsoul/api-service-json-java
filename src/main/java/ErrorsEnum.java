public enum ErrorsEnum {
    ERROR_TYPE("Ошибка: Тип поиска указан неверно. Программа не выполнена."),
    ERROR_DATABASE("Ошибка запроса к базе данных"),
    ERROR_DATABASE_CONNECT("Ошибка соединения с базой данных"),
    ERROR_FILE_WRITE("Ошибка записи файла"),
    ERROR_FILE_READ("Ошибка чтения файла"),
    ERROR("Ошибка: "),
    ERROR_JSON("Неверный формат файла"),
    ERROR_CRITERIA("Не найдено имя критерия: criteria"),
    ERROR_СRITERIA_LASTNAME("Не найдено имя критерия: lastName"),
    ERROR_СRITERIA_PRODUCT_AND_TIMES("Не найдено имя одного из критериев: productName или minTimes"),
    ERROR_СRITERIA_EXPENSES("Не найдено имя одного из критериев: minExpenses или maxExpenses"),
    ERROR_СRITERIA_BAD_CUSTOMERS("Не найдено имя критерия: badCustomers"),
    ERROR_DATE("Неверный формат даты"),
    ERROR_DATE_NONE("За данный период покупки отсутствуют"),
    ERROR_DATE_NAMES("Не найден какой-либо из критериев: startDate или endDate");


    private String title;

    ErrorsEnum(String s) {
        this.title = s;
    }

    public String getTitle() {
        return title;
    }

}
