package newbank.server;

import java.util.HashMap;
import java.util.Objects;

public class NewBank {

	private static final NewBank bank = new NewBank();
	private HashMap<String, Integer> ID;
	private static HashMap<String,Customer> customers;
	private HashMap<String,CustomerPassword> LoginCred;
	private NewBank() {
		ID = new HashMap<>();
		customers = new HashMap<>();
		LoginCred = new HashMap<>();

		addTestData();
	}

	private void addTestData() {
		Customer bhagy = new Customer(01);
		bhagy.addAccount(new Account("Main", 1000.0));
		customers.put("Bhagy", bhagy);
		LoginCred.put("Bhagy",new CustomerPassword("Bhagy123"));

		Customer christina = new Customer(02);
		christina.addAccount(new Account("Savings", 1500.0));
		customers.put("Christina", christina);
		LoginCred.put("Christina",new CustomerPassword("Christina123"));

		Customer john = new Customer(03);
		john.addAccount(new Account("Checking", 250.0));
		customers.put("John", john);
		LoginCred.put("John",new CustomerPassword("John123"));
	}

	public static NewBank getBank() {
		return bank;
	}


	//Method which handles authentication
	public synchronized CustomerID checkLogInDetails(String userName, String password) {
		if(customers.containsKey(userName)) {
			if(LoginCred.get(userName).verifyPassword(password))
				return new CustomerID(userName);
		}
		return null;
	}





	public static void showMyAccounts(CustomerID customer) {
		customers.get(customer.getKey()).printAccountBalance();
	}
}

