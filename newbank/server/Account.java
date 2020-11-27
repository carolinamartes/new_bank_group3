package newbank.server;

public class Account {
	
	private String accountName;
	private String accountType;
	private double openingBalance;
	private double currentBalance;


	public Account(String accountName, double openingBalance) {
		this.accountName = accountName;
		this.accountType = accountType;
		this.openingBalance = openingBalance;
		// We will later on build the logic for updating
		// openingBalance to currentBalance
		this.currentBalance = openingBalance;

	}

	public double getBalance(){
		return this.currentBalance;
	}

	public void addTransaction(double amount, String memo){

		//create a new transaction object and add it to our list
		Transaction newTrans = new Transaction(amount, memo, this);
		this.transactions.add(newTrans);
	}

	public String toString() {
		return (accountName + ": " + currentBalance);
	}

}
