package newbank.server;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Scanner;

public class MenuPrinter {

    private static PrintWriter out;

    public MenuPrinter(Socket s) throws IOException {
        out = new PrintWriter(s.getOutputStream(), true);
    }

     public static void printLogo(){
        out.flush();
        //prints NewBank logo
        out.println("███╗░░██╗███████╗░██╗░░░░░░░██╗        ██████╗░░█████╗░███╗░░██╗██╗░░██╗");
        out.println("████╗░██║██╔════╝░██║░░██╗░░██║        ██╔══██╗██╔══██╗████╗░██║██║░██╔╝");
        out.println("██╔██╗██║█████╗░░░╚██╗████╗██╔╝        ██████╦╝███████║██╔██╗██║█████═╝░");
        out.println("██║╚████║██╔══╝░░░░████╔═████║░        ██╔══██╗██╔══██║██║╚████║██╔═██╗░");
        out.println("██║░╚███║███████╗░░╚██╔╝░╚██╔╝░        ██████╦╝██║░░██║██║░╚███║██║░╚██╗");
        out.println("╚═╝░░╚══╝╚══════╝░░░╚═╝░░░╚═╝░░        ╚═════╝░╚═╝░░╚═╝╚═╝░░╚══╝╚═╝░░╚═╝");
        out.println();
        out.println("************************************************************************");
        out.println();
    }

    public static void printShowAccounts(ArrayList<Account> accounts){
        out.flush();
        String s = "";
        if (accounts.size() == 0) {
            out.println("No accounts found for this user");
        }
        for(Account a : accounts) {
            s += a.toString() + " ";
        }
        out.println(s);
    }

    public static void printBalance(Customer customer){
        out.flush();
        //inits
        int theAcct;
        Scanner sc = new Scanner();
        double acctBal;

        //get account whose balance to print
        do {
            System.out.printf("Enter the number (1-%d) of the account: ", customer.numAccounts());
            theAcct = sc.nextInt()-1;
            if (theAcct < 0 || theAcct >= customer.numAccounts()){
                System.out.println("Invalid account, please try again.");
            }
        } while(theAcct < 0 || theAcct >= customer.numAccounts()); //loop until valid account entered
        acctBal = customer.getAcctBalance(theAcct);

        out.printf("Balance: £%.02f", acctBal);
    }

    public static void depositFunds(Customer customer){
        out.flush();
        // inits
        int theAcct;
        Scanner sc = new Scanner();
        double amount;
        double acctBal;
        String memo;

        //get the account to deposit to
        do {
            System.out.printf("Enter the number (1-%d) of the account to deposit to: ", customer.numAccounts());
            theAcct = sc.nextInt()-1;
            if (theAcct < 0 || theAcct >= customer.numAccounts()){
                System.out.println("Invalid account, please try again.");
            }
        } while(theAcct < 0 || theAcct >= customer.numAccounts()); //loop until valid account entered
        acctBal = customer.getAcctBalance(theAcct);

        //get the amount to deposit
        do {
            System.out.println("Enter the amount to deposit);
                    amount = sc.nextDouble();
            if (amount < 0) {
                System.out.println("Amount must be greater than zero.");
            }
        } while(amount < 0);

        //consume previous input
        sc.nextLine();

        //get a memo
        System.out.println("Enter a memo: ");
        memo = sc.nextLine();

        //do the deposit
        customer.addAcctTransaction(theAcct, amount, memo);
        out.printf("Deposit of: £%.02f made. Balance: £%.02f", amount ,acctBal);

    }

    public static void withdrawFunds(Customer customer){
        out.flush();
        // inits
        int theAcct;
        Scanner sc = new Scanner();
        double amount;
        double acctBal;
        String memo;

        //get the account to withdraw from
        do {
            System.out.printf("Enter the number (1-%d) of the account to withdraw from: ", customer.numAccounts());
            theAcct = sc.nextInt()-1;
            if (theAcct < 0 || theAcct >= customer.numAccounts()){
                System.out.println("Invalid account, please try again.");
            }
        } while(theAcct < 0 || theAcct >= customer.numAccounts()); //loop until valid account entered
        acctBal = customer.getAcctBalance(theAcct);

        //get the amount to withdraw
        do {
            System.out.printf("Enter the amount to withdraw (max £%.02f): $", acctBal);
            amount = sc.nextDouble();
            if (amount < 0) {
                System.out.println("Amount must be greater than zero.");
            } else if (amount > acctBal){
                System.out.printf("Amount must not be greater than balance of £%.02f.\n", acctBal);
            }
        } while(amount < 0 || amount > acctBal);

        //consume previous input
        sc.nextLine();

        //get a memo
        System.out.println("Enter a memo: ");
        memo = sc.nextLine();

        //do the withdraw
        customer.addAcctTransaction(theAcct, -1*amount, memo);
        out.printf("Withdrawal of: £%.02f made. Balance: £%.02f", amount ,acctBal);
    }

    public static void printNewAccountsPg1(){
        out.flush();
      //print new account options page 1
        out.println("Which type of account would you like to create?");
        out.println();
        out.println("(1) Current");
        out.println("(2) Savings");

        System.out.println("printNewAccountsPg1");
    }

    public static void printNewAccountsPg2(){
        out.flush();
        //print new account options page 2
        out.println("Create new" + "(placeholder account)");
        out.println();

        System.out.println("printNewAccountsPg2");
    }

    public static void printSuccess(){
        out.println("This action was carried out successfully");
    }

    public static void printAccountStatement(){
        out.flush();
      //print balance, transactions etc
        out.println("Balance: ");
        out.println("Transactions: ");
        out.println();
        out.println("Would you like to save this statement? (y/n)");

        System.out.println("printAccountStatement");
    }

    public static void printLogOut(){
        out.println("Are you sure you want to log out? (y/n)");
    }
    public static void printEnterUsername(){
        out.println("Enter Username");
    }
    public static void printEnterPassword(){
        out.println("Enter Password");
    }
    public static void printCheckingDetails(){
        out.println("What do you want to do?");
    }
    public static void printTop(){
        out.println("What do you want to do?");
    }

    public static void printFail(){
        out.println("nope");

        System.out.println("lol");
    }
}
