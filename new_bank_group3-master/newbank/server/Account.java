package newbank.server;

public class Account {
	
	private final String accountName;
	private double currentBalance;
	private boolean isFrozen = false;

	public Account(String accountName, double openingBalance) {
		this.accountName = accountName;
		// We will later on build the logic for updating
		// openingBalance to currentBalance
		this.currentBalance = openingBalance;

	}

	public double getCurrentBalance (){
			return currentBalance;
	}

	public void withdraw (double withdrawAmount) {
		if (!isFrozen) {
			currentBalance -= withdrawAmount;
		}
	}

	public void deposit (double depositAmount) {
		currentBalance+= depositAmount;
	}

	public String getAccountName (){
		return accountName;
	}

	public String toString() {
		return (accountName + ": " + currentBalance);
	}

	public void freezeAccount() {
		isFrozen = true;
	}
	public void unfreezeAccount() {
		isFrozen = false;
	}
}
