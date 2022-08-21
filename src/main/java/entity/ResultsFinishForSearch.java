package entity;

import java.util.ArrayList;

public class ResultsFinishForSearch {
    private String type = "search";
    private ArrayList<ResultsForSearch> results;

    public String getType() {
        return type;
    }

    public ArrayList<ResultsForSearch> getResults() {
        return results;
    }

    public void setResults(ArrayList<ResultsForSearch> results) {
        this.results = results;
    }
}
