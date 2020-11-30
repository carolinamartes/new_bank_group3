package newbank.server;

import java.util.HashMap;

public class NewBank {
	
	private static final NewBank bank = new NewBank();
	private static HashMap<String,Customer> customers;
	
	private NewBank() {
		customers = new HashMap<>();
		addTestData();
	}
	
	private void addTestData() {
		Customer bhagy = new Customer();
		bhagy.addAccount(new Account("Main", 1000.0));
		bhagy.addAccount(new Account("Savings", 20.0));
		customers.put("Bhagy", bhagy);

		// Tests a customer with no accounts
		Customer christina = new Customer();
		customers.put("Christina", christina);
		
		Customer john = new Customer();
		john.addAccount(new Account("Checking", 250.0));
		customers.put("John", john);
	}
	
	public static NewBank getBank() {
		return bank;
	}
	
	public synchronized CustomerID checkLogInDetails(String userName, String password) {
		if(customers.containsKey(userName)) {
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

	public static void showMyAccounts(CustomerID customer) {
		customers.get(customer.getKey()).printAccountBalance();
	}

	public static void showTransferFromOptions(CustomerID customer, double requestAmount) {
		customers.get(customer.getKey()).printTransferableFromAccounts(requestAmount);
	}

	public static void showTransferToOptions(CustomerID customer, int fromAccount) {
		customers.get(customer.getKey()).printTransferableToAccounts(fromAccount);
	}

	public static void showDepositOptions(CustomerID customer, double requestAmount) {
		customers.get(customer.getKey()).printDepositToAccounts(requestAmount);
	}

	public static void executeTransfer(CustomerID customer, int fromAccount, int toAccount, double requestAmount){
		customers.get(customer.getKey()).executeTransfer(fromAccount, toAccount, requestAmount);
	}

	public static void executeWithdraw(CustomerID customer, int fromAccount, double requestAmount){
		customers.get(customer.getKey()).executeWithdraw(fromAccount, requestAmount);
	}

	public static void executeDeposit(CustomerID customer, int toAccount, double requestAmount){
		customers.get(customer.getKey()).executeDeposit(toAccount, requestAmount);
	}

}
