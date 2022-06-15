import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;

public class SearchGetter {
    public void startSearch(String fileInput, File fileOutput) throws IOException, SQLException {
        SearchCriterias searchCriterias = new SearchCriterias();
        searchCriterias.setAllCriterias(fileInput);

        SearchResultsGetter searchResultsGetter = new SearchResultsGetter();
        SearchResultsFinish result = searchResultsGetter.getFinishResults(searchCriterias);

        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        String json = gson.toJson(result);

        FileWriterMy fileWriterMy = new FileWriterMy();
        fileWriterMy.writeFile(fileOutput, json);
    }
}
