package newbank.server;

public class CustomerID {
	private String key;

	Customer customerFunctions = new Customer();
	
	public CustomerID(String key) {
		this.key = key;
	}
	
	public String getKey() {
		return key;
	}
}
