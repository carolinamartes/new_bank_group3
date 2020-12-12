package newbank.server;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;

public class MenuPrinter {

    public static PrintWriter out;

    public MenuPrinter(Socket s) throws IOException {
        out = new PrintWriter(s.getOutputStream(), true);
    }

     public void printLogo(){
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

    public void printShowAccounts(ArrayList<Account> accounts){
        out.flush();
        StringBuilder s = new StringBuilder();
        if (accounts.size() == 0) {
            out.println("No accounts found for this user");
        }
        for(Account a : accounts) {
            s.append(a.toString()).append(" ");
        }
        out.println(s);
    }

    public void printTransferableTo(ArrayList<Account> accounts, int fromAccountID) {
        Account fromAccount = accounts.get(fromAccountID);
        out.println("Select an account to transfer to:");
        for(int i=0; i < accounts.size();i++){
            int current = i + 1;
            if (!accounts.get(i).equals(fromAccount)) {
                out.print("(" + current + ") ");
                out.println(accounts.get(i).getAccountName());
            }
        }
    }

    public void askTransferQuantity(){
        out.println("How much would you like to transfer? Write TRANSFERAMOUNT {{amount}}");
    }

    public void printTransferableFrom(ArrayList<Account> accounts){
        out.flush();
        if (accounts.size() == 0){
            out.println("No valid accounts found for this user");
        }

        out.println("Select an account to transfer from:");
        for(int i=0; i < accounts.size();i++){
            int current = i + 1;
            out.print("("+ current +") ");
            out.println(accounts.get(i).getAccountName());
        }
    }

    public void printNewAccountsPg1(){
        out.flush();
      //print new account options page 1
        out.println("Which type of account would you like to create?");
        out.println();
        out.println("(1) Current");
        out.println("(2) Savings");

        System.out.println("printNewAccountsPg1");
    }

    public void printNewAccountsPg2(){
        out.flush();
        //print new account options page 2
        out.println("Create new" + "(placeholder account)");
        out.println();

        System.out.println("printNewAccountsPg2");
    }

    public void printSuccess(){
        out.println("This action was carried out successfully");
    }

    public void printAccountStatement(){
        out.flush();
      //print balance, transactions etc
        out.println("Balance: ");
        out.println("Transactions: ");
        out.println();
        out.println("Would you like to save this statement? (y/n)");

        System.out.println("printAccountStatement");
    }

    public void printLogOut(){
        out.println("Are you sure you want to log out? (y/n)");
    }
    public void printEnterUsername(){
        out.println("Enter Username");
    }
    public void printEnterPassword(){
        out.println("Enter Password");
    }
    public void printCheckingDetails(){
        out.println("What do you want to do?");
    }
    public void printTop(){
        out.println("What do you want to do?");
    }
    public void printFrozen() {out.println("This action was unable to be carried out as the account is frozen");}
    public void printFail(){ out.println("This action was unsuccessful"); }
    public void printInvalid(){ out.println("Invalid action"); }
}
