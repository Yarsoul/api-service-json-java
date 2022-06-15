import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.File;

public class StatisticGetter {
    public void startStatistic(String fileInput, File fileOutput) {
        StatisticCriterias statisticCriterias = new StatisticCriterias();
        statisticCriterias.setAllCriterias(fileInput);

        StatisticResultsGetter statisticResultsGetter = new StatisticResultsGetter();
        StatisticResultsFinish result = statisticResultsGetter.getResults(statisticCriterias);

        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        String json = gson.toJson(result);

        FileWriterMy fileWriterMy = new FileWriterMy();
        fileWriterMy.writeFile(fileOutput, json);
    }
}
