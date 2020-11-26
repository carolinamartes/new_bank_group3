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

    public String toString() {
        return (accountName + ": " + currentBalance);
    }

}
