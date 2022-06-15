import java.util.ArrayList;
import java.util.HashMap;

public class SearchResults {
    private HashMap<String, Object> criteria = new HashMap<>();
    private ArrayList<Customer> results = new ArrayList<>();

    public void putCriteria(HashMap<String, Object> criteria) {
        this.criteria.putAll(criteria);
    }

    public void addResults(ArrayList<Customer> arrCustomers) {
        this.results.addAll(arrCustomers);
    }
}
