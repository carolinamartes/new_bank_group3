package newbank.server;

import java.util.ArrayList;

public class Customer {
	private final MenuPrinter menuPrinter;
	private ArrayList<Account> accounts;
	private Integer ID;
	private String CustomerPassword;
	
	public Customer(Integer ID) {
		accounts = new ArrayList<Account>();
		this.ID = ID;
	}

	public Integer ID(){
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
		menuPrinter.printShowAccounts(accounts);
	}

	public void printAccountsToText() {
		menuPrinter.printAccountsToText(accounts);
	}

	public void printTransferableFromAccounts(double requestAmount) {
		ArrayList<Account> transferableFromAccounts = new ArrayList<Account>();
		for(Account a : accounts) {
			if (a.getCurrentBalance() >= requestAmount){
				transferableFromAccounts.add(a);
			}
		}
		menuPrinter.printTransferableFrom(transferableFromAccounts);
	}

	public void printDepositToAccounts() {
		menuPrinter.printDepositTo(accounts);
	}

	public void executeTransfer(int fromAccountID, int toAccountID, double requestAmount){
		Account fromAccount = accounts.get(fromAccountID);
		Account toAccount = accounts.get(toAccountID);
		fromAccount.withdraw(requestAmount);
		toAccount.deposit(requestAmount);
		menuPrinter.printSuccess();
		menuPrinter.printShowAccounts(accounts);
	}

	public void executeWithdraw(int fromAccountID, double requestAmount){
		Account fromAccount = accounts.get(fromAccountID);
		fromAccount.withdraw(requestAmount);
		menuPrinter.printSuccess();
		menuPrinter.printShowAccounts(accounts);
	}

	public void executeDeposit(int toAccountID, double requestAmount){
		Account toAccount = accounts.get(toAccountID);
		toAccount.deposit(requestAmount);
		menuPrinter.printSuccess();
		menuPrinter.printShowAccounts(accounts);
	}

	public void printTransferableToAccounts(int fromAccountID) {
		menuPrinter.printTransferableTo(accounts, fromAccountID);
	}

	public void addAccount(Account account) {
		accounts.add(account);
	}
}
