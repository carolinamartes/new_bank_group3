package newbank.server;

import java.util.ArrayList;

public class Customer {


	private ArrayList<Account> accounts;
	private Integer ID;
	private String CustomerPassword;
	private String userName;


	public Customer(Integer ID) { accounts = new ArrayList<>(); this.ID=ID;}

	public Integer getID() {
		return ID;
	}

	Integer ID(){
		return ID;
	}
	
	public void printAccountBalance() {
		MenuPrinter.printShowAccounts(accounts);
	}
	//This method is part of the functionality to print a balance to text
	public void printaccountstotext() {
		MenuPrinter.printaccountstotext(accounts);}

	public void printTransferableFromAccounts(double requestAmount) {
		ArrayList<Account> transferableFromAccounts = new ArrayList<Account>();
		for(Account a : accounts) {
			if (a.getCurrentBalance() >= requestAmount){
				transferableFromAccounts.add(a);
			}
		}
		MenuPrinter.printTransferableFrom(transferableFromAccounts);
	}

	public void executeTransfer(int fromAccountID, int toAccountID, double requestAmount){
		Account fromAccount = accounts.get(fromAccountID);
		Account toAccount = accounts.get(toAccountID);
		fromAccount.withdraw(requestAmount);
		toAccount.deposit(requestAmount);
		MenuPrinter.printSuccess();
		MenuPrinter.printShowAccounts(accounts);
	}

	public void printTransferableToAccounts(int fromAccountID) {
		MenuPrinter.printTransferableTo(accounts, fromAccountID);
	}

	public void addAccount(Account account) {
		accounts.add(account);		
	}
}
