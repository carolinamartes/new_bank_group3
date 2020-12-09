package newbank.server;

import java.util.HashMap;

public class NewBank {
	
	private static final NewBank bank = new NewBank();
	private static HashMap<String,Customer> customers;
	private HashMap<String, Integer> ID;
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

	public synchronized String validator(CustomerID customer) {
		if(customers.containsKey(customer.getKey())) {
			return "valid";
		}
		return "invalid";
	}

	public void showMyAccounts(CustomerID customer, MenuPrinter menuPrinter) {
		customers.get(customer.getKey()).printAccountBalance(menuPrinter);
	}

	public void showTransferFromOptions(CustomerID customer, double requestAmount, MenuPrinter menuPrinter) {
		customers.get(customer.getKey()).printTransferableFromAccounts(requestAmount, menuPrinter);
	}

	public void showTransferToOptions(CustomerID customer, int fromAccount, MenuPrinter menuPrinter) {
		customers.get(customer.getKey()).printTransferableToAccounts(fromAccount, menuPrinter);
	}

	public void executeTransfer(CustomerID customer, int fromAccount, int toAccount, double requestAmount, MenuPrinter menuPrinter){
		customers.get(customer.getKey()).executeTransfer(fromAccount, toAccount, requestAmount, menuPrinter);
	}

	public void freezeAccounts(CustomerID customer, MenuPrinter menuPrinter) {
		customers.get(customer.getKey()).freezeAccounts();
	}
}
