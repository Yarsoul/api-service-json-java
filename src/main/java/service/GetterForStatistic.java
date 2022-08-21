package service;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import entity.CriteriasForStatistic;
import entity.ResultsFinishForStatistic;

import java.io.File;

public class GetterForStatistic {
    public void startStatistic(String fileInput, File fileOutput) {
        CriteriasForStatistic criteriasForStatistic = new CriteriasForStatistic();
        criteriasForStatistic.setAllCriterias(fileInput);

        ResultsGetterForStatistic resultsGetterForStatistic = new ResultsGetterForStatistic();
        ResultsFinishForStatistic result = resultsGetterForStatistic.getResults(criteriasForStatistic);

        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        String json = gson.toJson(result);

        FileWriterMy fileWriterMy = new FileWriterMy();
        fileWriterMy.writeFile(fileOutput, json);
    }
}
