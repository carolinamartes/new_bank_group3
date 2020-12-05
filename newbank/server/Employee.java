package newbank.server;

import java.util.ArrayList;

public class Employee {

	private Integer ID;
	private String EmployeePassword;
	private ArrayList<Account> accounts;
	
	public Employee(Integer ID) { this.ID=ID;}

	public Integer ID(){
		return ID;
	}

	public void addAccount(Account account) {
		accounts.add(account);
	}

	public void createCustomer(){

	}

	public void createAccount(){

	}

	public void closeCustomer(){

	}

	public void closeAccount(){

	}
}
