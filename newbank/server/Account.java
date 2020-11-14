package newbank.server;

public class Account {
	
	private String accountName;
	private double openingBalance;
	private double currentBalance;

	public Account(String accountName, double openingBalance) {
		this.accountName = accountName;
		this.openingBalance = openingBalance;
		// We will later on build the logic for updating
		// openingBalance to currentBalance
		this.currentBalance = openingBalance;
	}

	public String toString() {
		return (accountName + ": " + currentBalance);
	}

}
