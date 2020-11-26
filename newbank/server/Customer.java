package newbank.server;

import java.util.ArrayList;

public class Customer {

	private ArrayList<Account> accounts;
	private Integer ID;
	private String CustomerPassword;

	public Customer(Integer ID) {
		accounts = new ArrayList<>();
		this.ID=ID;
	}
	public Integer ID(){
		return ID;
	}


	public String accountsToString() {
		String s = "";
		if (accounts.size() == 0) {
			return "No accounts found for this user";
		}
		for(Account a : accounts) {
			s += a.toString();
		}
		return s;
	}


	public void addAccount(Account account) {
		accounts.add(account);
	}
}