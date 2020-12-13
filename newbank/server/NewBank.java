package newbank.server;

import java.util.HashMap;

public class NewBank {

	public static final String [] customerCommands = new String[]{
		"SENDMONEY",
		"SHOWMYACCOUNTS",
		"MOVEMYMONEY",
		"WITHDRAW",
		"DEPOSIT",
		"CREATEMAIN",
		"CREATESAVING",
		"CREATECHECKING",
		"CLOSEACCOUNT",
		"LOGOUT"
	};

	public static final String [] employeeCommands = new String[]{
		"CREATEACCOUNT",
		"CREATECUSTOMER",
		"LOGOUT"
	};

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
		AccountType mainAccountType = new AccountType("Main");
		AccountType savingAccountType = new AccountType("Saving");
		AccountType checkingAccountType = new AccountType("Checking");

		Customer bhagy = new Customer(01);
		bhagy.addAccount(new Account(mainAccountType, 1000.0));
		customers.put("Bhagy", bhagy);
		LoginCred.put("Bhagy",new CustomerPassword("Bhagy1234"));

		Customer christina = new Customer(02);
		christina.addAccount(new Account(savingAccountType, 1500.0));
		customers.put("Christina", christina);
		LoginCred.put("Christina",new CustomerPassword("Christina1234"));

		Customer john = new Customer(03);
		john.addAccount(new Account(checkingAccountType, 250.0));
		john.addAccount(new Account(savingAccountType, 500));
		john.addAccount(new Account(mainAccountType, 2000));
		customers.put("John", john);
		LoginCred.put("John",new CustomerPassword("John1234"));

		Employee richard = new Employee(01);
		employees.put("Richard", richard);
		EmployeeLoginCred.put("Richard", new EmployeePassword("Richard1234"));
	}
	
	public static NewBank getBank() {
		return bank;
	}

	//Method which handles authentication
	public synchronized BankUserID checkLogInDetails(String userName, String password) {
		if(customers.containsKey(userName)) {
			if(LoginCred.get(userName).verifyPassword(password))
				return new CustomerID(userName);
		}
		if(employees.containsKey(userName)) {
			if(EmployeeLoginCred.get(userName).verifyPassword(password))
				return new EmployeeID(userName);
		}
		return null;
	}

	public synchronized CustomerID checkCustomerLogInDetails(String userName, String password) {
		if(customers.containsKey(userName)) {
			if(LoginCred.get(userName).verifyPassword(password))
				return new CustomerID(userName);
		}
		return null;
	}

	public synchronized CustomerID ChangePassword(String userName,String temp, String currentPassword) {
		if (customers.containsKey(userName)) {
			if (LoginCred.get(userName).verifyPassword(currentPassword)) {
				LoginCred.get(userName).changePassword(temp);
			}
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

	public synchronized String validator(CustomerID customer) {
		if(customers.containsKey(customer.getKey())) {
			return "valid";
		}
		return "invalid";
	}

	public static void showMyAccounts(CustomerID customer) {
		customers.get(customer.getKey()).printAccountBalance();
	}

	public static void printsAccountsToText(CustomerID customer) {
		customers.get(customer.getKey()).printAccountsToText();
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

	public static void createAccount(String customerName){
		AccountType mainAccountType = new AccountType("Main");
		customers.get(customerName).addAccount(new Account(mainAccountType, 0));
	}

	public static void createMain(CustomerID customer, double requestAmount){
		AccountType mainAccountType = new AccountType("Main");
		customers.get(customer.getKey()).addAccount(new Account(mainAccountType, requestAmount));
	}

	public static void removeAccount(CustomerID customer, AccountType accountType){
		customers.get(customer.getKey()).removeAccount(accountType);
	}

	public static void createSaving(CustomerID customer, double requestAmount){
		AccountType savingAccountType = new AccountType("Saving");
		customers.get(customer.getKey()).addAccount(new Account(savingAccountType, requestAmount));
	}

	public static void createChecking(CustomerID customer, double requestAmount){
		AccountType checkingAccountType = new AccountType("Checking");
		customers.get(customer.getKey()).addAccount(new Account(checkingAccountType, requestAmount));
	}
}
 
