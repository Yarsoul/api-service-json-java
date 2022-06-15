import java.io.*;

public class FileWriterMy {
    public void writeFile(File fileOutput, String json) {
        try {
            BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(fileOutput),
                    "UTF8"));
            bufferedWriter.write(json);
            bufferedWriter.close();
        } catch (IOException e) {
            System.out.println(ErrorsEnum.ERROR_FILE_WRITE.getTitle());
            System.out.println(e.getMessage());
            System.exit(1);
        }

        System.out.println("Программа выполнена");
    }
}
