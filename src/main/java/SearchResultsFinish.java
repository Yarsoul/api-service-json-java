import java.util.ArrayList;

public class SearchResultsFinish {
    private String type = "search";
    private ArrayList<SearchResults> results;

    public String getType() {
        return type;
    }

    public ArrayList<SearchResults> getResults() {
        return results;
    }

    public void setResults(ArrayList<SearchResults> results) {
        this.results = results;
    }
}
