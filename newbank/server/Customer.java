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

	public int numAccounts(){
		return this.accounts.size();
	}

	public double getAcctBalance(int acctIdx){
		return this.accounts.get(acctIdx).getBalance();
	}

	public void addAcctTransaction(int acctIdx, double amount, String memo){
		this.accounts.get(acctIdx).addTransaction(amount, memo);
	}

	public void addAccount(Account account) {
		accounts.add(account);		
	}
}
