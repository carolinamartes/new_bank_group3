package newbank.server;
import java.util.List;
import java.util.Arrays;

public class AccountType {
    private String key;
    private final String [] acceptedTypes = new String[]{ "Checking", "Saving", "Main"};

    public AccountType(String key) {
        List<String> list = Arrays.asList(acceptedTypes);
        if (list.contains(key)){
            this.key = key;
        }
        else {
            System.out.println("Unaccepted account type: " + key);
        }

    }
    public String getKey() {
            return key;
        }

}
