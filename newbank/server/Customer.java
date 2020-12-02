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

	public void printTransferableFromAccounts(double requestAmount) {
		ArrayList<Account> transferableFromAccounts = new ArrayList<Account>();
		for(Account a : accounts) {
			if (a.getCurrentBalance() >= requestAmount){
				transferableFromAccounts.add(a);
			}
		}
		MenuPrinter.printTransferableFrom(transferableFromAccounts);
	}

	public void printDepositToAccounts() {
		MenuPrinter.printDepositTo(accounts);
	}

	public void executeTransfer(int fromAccountID, int toAccountID, double requestAmount){
		Account fromAccount = accounts.get(fromAccountID);
		Account toAccount = accounts.get(toAccountID);
		fromAccount.withdraw(requestAmount);
		toAccount.deposit(requestAmount);
		MenuPrinter.printSuccess();
		MenuPrinter.printShowAccounts(accounts);
	}

	public void executeWithdraw(int fromAccountID, double requestAmount){
		Account fromAccount = accounts.get(fromAccountID);
		fromAccount.withdraw(requestAmount);
		MenuPrinter.printSuccess();
		MenuPrinter.printShowAccounts(accounts);
	}

	public void executeDeposit(int toAccountID, double requestAmount){
		Account toAccount = accounts.get(toAccountID);
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
