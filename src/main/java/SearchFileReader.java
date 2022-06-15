import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class SearchFileReader {

    private String readInputFile(String fileName) {
        StringBuilder textJson = new StringBuilder();
        try {
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(new FileInputStream(fileName),
                    "UTF8"));
            while (bufferedReader.ready()) {
                textJson.append((char) bufferedReader.read());
            }
            bufferedReader.close();
        } catch (IOException e) {
            try {
                ErrorMy errorMy = new ErrorMy();
                errorMy.printError(ErrorsEnum.ERROR_FILE_READ.getTitle());
            } catch (IOException exception) {
                System.out.println(ErrorsEnum.ERROR_FILE_WRITE.getTitle());
            }

            System.out.println(ErrorsEnum.ERROR_FILE_READ.getTitle());
            System.out.println(e.getMessage());
            System.exit(1);
        }

        String strJson = textJson.toString();
        return strJson;
    }

    public Map<String, ArrayList<Map<String, Object>>> getMapFromJson(String fileName) {
        String json = readInputFile(fileName);
        Gson gson = new Gson();
        Type collectionType = new TypeToken<Map<String, ArrayList<Map<Object, Object>>>>() {
        }.getType();
        Map<String, ArrayList<Map<String, Object>>> mapFromJson = new HashMap<>();
        try {
            mapFromJson = gson.fromJson(json, collectionType);
        } catch (JsonSyntaxException e) {
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

        return mapFromJson;
    }
}
