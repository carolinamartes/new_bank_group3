package newbank.server;

import java.util.HashMap;
import java.util.Objects;

public class NewBank {

	private static final NewBank bank = new NewBank();
	private HashMap<String, Integer> ID;
	private HashMap<String,Customer> customers;
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



	// commands from the NewBank customer are processed in this method
	public synchronized String processRequest(CustomerID customer, String request) {
		if(customers.containsKey(customer.getKey())) {
			switch(request) {
				case "SHOWMYACCOUNTS" : return showMyAccounts(customer);
				default : return "FAIL";
			}
		}
		return "FAIL";
	}

	private String showMyAccounts(CustomerID customer) {
		return (customers.get(customer.getKey())).accountsToString();
	}



	//This is a method I am working on to allow a customer to change their password
	/*public synchronized void ChangePassword(String password) {
		if (password.length() < 6) {
			System.out.println("Passwords must be at least six characters long");
		} else {
			logInData.put(userName, new CustomerPassword(password));
		}
	}

	 */


}