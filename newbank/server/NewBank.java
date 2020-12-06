package newbank.server;

import java.util.HashMap;

public class NewBank {
	
	private static final NewBank bank = new NewBank();
	private static HashMap<String,Customer> customers;
	private HashMap<String, Integer> ID;
	private static HashMap<String,CustomerPassword> LoginCred;
	private static HashMap<String,Employee> employees;
	private HashMap<String, Integer> EID;
	private HashMap<String,EmployeePassword> EmployeeLoginCred;
	
	private NewBank() {
		ID = new HashMap<>();
		customers = new HashMap<>();
		LoginCred = new HashMap<>();
		EID = new HashMap<>();
		employees = new HashMap<>();
		EmployeeLoginCred = new HashMap<>();
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

		Employee richard = new Employee(01);
		employees.put("Richard", richard);
		EmployeeLoginCred.put("Richard", new EmployeePassword("Richard123"));
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

	public synchronized EmployeeID checkEmployeeLogInDetails(String userName, String password) {
		if(employees.containsKey(userName)) {
			if(EmployeeLoginCred.get(userName).verifyPassword(password))
				return new EmployeeID(userName);
		}
		return null;
	}

	public static void createCustomer(String userName) {
		//checks customer of same name does not exist
		if(customers.containsKey(userName)) {
			System.out.println("User already exists.");
		}
		else{
			Customer newCustomer = new Customer(customers.size()+1);
			customers.put(userName, newCustomer);
			String password = userName + "123";
			LoginCred.put(userName,new CustomerPassword(password));
		}
	}

	public static void createAccount(String customerName, Account account){
		customers.get(customerName).addAccount(account);
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

	public static void showDepositOptions(CustomerID customer) {
		customers.get(customer.getKey()).printDepositToAccounts();
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

	public static Integer getAccountID(String recipient){
		if (customers.containsKey(recipient)){
			return customers.get(recipient).getCheckings();
		}
		return -1;
	}

	public static void executeSendMoney(CustomerID customer, String recipient, Integer toAccountID, Integer fromAccountIndex, double requestAmount){
		customers.get(customer.getKey()).executeWithdraw(fromAccountIndex, requestAmount);
		customers.get(recipient).executeDeposit(toAccountID, requestAmount);
	}

}
