package newbank.server;

import java.util.ArrayList;

public class Customer {
	
	private ArrayList<Account> accounts;
	
	public Customer() {
		accounts = new ArrayList<>();
	}
	
	public void printAccountBalance() {
		MenuPrinter.printShowAccounts(accounts);
	}

	public void addAccount(Account account) {
		accounts.add(account);		
	}
}
