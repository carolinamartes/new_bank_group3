package newbank.server;

import java.util.ArrayList;

public class Customer {
	private ArrayList<Account> accounts;
	private CustomerID ID;
	private String CustomerPassword;
	
	public Customer(CustomerID ID) {
		accounts = new ArrayList<Account>();
		this.ID = ID;
	}

	public CustomerID ID(){
		return ID;
	}

	public int getCheckings() {
		for(Account a : accounts) {
			if (a.getAccountType().getKey().equals("Checking")){
				return accounts.indexOf(a);
			}
		}
		return -1;
	}
	
	public void printAccountBalance() {
		MenuPrinter.printShowAccounts(accounts);
	}

	public void printAccountsToText() {
		MenuPrinter.printAccountsToText(accounts);
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

	public int getAccountCount(){
		return accounts.size();
	}

	public Account getAccount(int accountID){
		return accounts.get(accountID);
	}
}
