package newbank.server;

public class Account {

	private AccountType accountType;
	private double openingBalance;
	private double currentBalance;

	public Account(AccountType accountType, double openingBalance) {
		this.accountType = accountType;
		this.openingBalance = openingBalance;
		this.currentBalance = openingBalance;
	}

	public double getCurrentBalance (){
		return currentBalance;
	}

	public void withdraw (double withdrawAmount) {
		currentBalance-= withdrawAmount;
	}

	public void deposit (double depositAmount) {
		currentBalance+= depositAmount;
	}

	public AccountType getAccountType (){
		return accountType;
	}

	public String toString() {
		return (accountType.getKey() + ": " + currentBalance);
	}

}
