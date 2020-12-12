package newbank.server;

import java.util.ArrayList;

public class Customer {
	
	private final ArrayList<Account> accounts;
	private final Integer ID;
	//private String CustomerPassword;

	
	public Customer(Integer ID) { accounts = new ArrayList<>(); this.ID=ID;}

	public Integer ID(){
		return ID;
	}
	
	public void printAccountBalance(MenuPrinter menuPrinter) {
		menuPrinter.printShowAccounts(accounts);
	}

	public void printTransferableFromAccounts(double requestAmount, MenuPrinter menuPrinter) {
		ArrayList<Account> transferableFromAccounts = new ArrayList<Account>();
		for(Account a : accounts) {
			if (a.getCurrentBalance() >= requestAmount){
				transferableFromAccounts.add(a);
			}
		}
		menuPrinter.printTransferableFrom(transferableFromAccounts);
	}

	public void executeTransfer(int fromAccountID, int toAccountID, double requestAmount, MenuPrinter menuPrinter){
		Account fromAccount = accounts.get(fromAccountID);
		Account toAccount = accounts.get(toAccountID);
		fromAccount.withdraw(requestAmount);
		toAccount.deposit(requestAmount);
		menuPrinter.printSuccess();
		menuPrinter.printShowAccounts(accounts);
	}

	public void printTransferableToAccounts(int fromAccountID, MenuPrinter menuPrinter) {
		menuPrinter.printTransferableTo(accounts, fromAccountID);
	}

	public void addAccount(Account account) {
		accounts.add(account);		
	}

	public void freezeAccounts() {
		for (int i = accounts.size(); i > 0; i --) {
			accounts.get(i).freezeAccount();
		}
	}
	public void unfreezeAccounts() {
		for (int i = accounts.size(); i > 0; i --) {
			accounts.get(i).unfreezeAccount();
		}
	}
}
