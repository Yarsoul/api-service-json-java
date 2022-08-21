package service;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import entity.CriteriasForSearch;
import entity.ResultsFinishForSearch;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;

public class GetterForSearch {
    public void startSearch(String fileInput, File fileOutput) throws IOException, SQLException {
        CriteriasForSearch criteriasForSearch = new CriteriasForSearch();
        criteriasForSearch.setAllCriterias(fileInput);

        ResultsGetterForSearch resultsGetterForSearch = new ResultsGetterForSearch();
        ResultsFinishForSearch result = resultsGetterForSearch.getFinishResults(criteriasForSearch);

        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        String json = gson.toJson(result);

        FileWriterMy fileWriterMy = new FileWriterMy();
        fileWriterMy.writeFile(fileOutput, json);
    }
}
