package newbank.server;

import java.util.ArrayList;

public class Customer {

	String userName = null;
	
	private ArrayList<Account> accounts;
	
	public Customer() {
		accounts = new ArrayList<>();
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
