package controller;

import enums.EnumForErrors;
import service.GetterForSearch;
import service.GetterForStatistic;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.SQLException;

public class MyController {
    public void startProgram() {
        try {
            System.out.println("Введите тип поиска ('search' или 'stat'):");
            BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
            String type = br.readLine();

            if (type.equals("search")) {
                System.out.println("Введите путь к файлу input.json:");
                BufferedReader brFileInput = new BufferedReader(new InputStreamReader(System.in));
                String fileInput = brFileInput.readLine();

                System.out.println("Введите путь к файлу output.json:");
                BufferedReader brFileOutput = new BufferedReader(new InputStreamReader(System.in));
                String output = brFileOutput.readLine();

                // Для отладки:
                //String fileInput = "src/main/resources/input.json";
                //File fileOutput = new File("src/main/resources/output.json");

                File fileOutput = new File(output);
                GetterForSearch getterForSearch = new GetterForSearch();
                getterForSearch.startSearch(fileInput, fileOutput);

                br.close();
                brFileInput.close();
                brFileOutput.close();
            } else if (type.equals("stat")) {
                System.out.println("Введите путь к файлу input.json:");
                BufferedReader brFileInput = new BufferedReader(new InputStreamReader(System.in));
                String fileInput = brFileInput.readLine();

                System.out.println("Введите путь к файлу output.json:");
                BufferedReader brFileOutput = new BufferedReader(new InputStreamReader(System.in));
                String output = brFileOutput.readLine();

                // Для отладки:
                //String fileInput = "src/main/resources/input2.json";
                //File fileOutput = new File("src/main/resources/output.json");

                File fileOutput = new File(output);
                GetterForStatistic getterForStatistic = new GetterForStatistic();
                getterForStatistic.startStatistic(fileInput, fileOutput);

                br.close();
                brFileInput.close();
                brFileOutput.close();
            } else {
                br.close();
                System.out.println(EnumForErrors.ERROR_TYPE.getTitle());
            }
        } catch (IOException exception) {
            System.out.println("Ошибка чтения вводных данных или чтения/записи файла: " + exception.getMessage());
        } catch (SQLException exception) {
            System.out.println("Ошибка чтения базы данных: " + exception.getMessage());
        }
    }
}
