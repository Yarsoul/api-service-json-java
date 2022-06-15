import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class ErrorMy {
    private final String type = "error";
    private String message;

    private void setMessage(String message) {
        this.message = message;
    }

    public void printError(String message) throws IOException {
        ErrorMy errorMy = new ErrorMy();
        errorMy.setMessage(message);

        File fileOutput = new File("src/main/resources/output.json");
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        String json = gson.toJson(errorMy);

        FileOutputStream fileOutputStream = new FileOutputStream(fileOutput);
        fileOutputStream.write(json.getBytes());
        fileOutputStream.close();
    }
}
