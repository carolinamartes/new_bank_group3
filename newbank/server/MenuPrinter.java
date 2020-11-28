package newbank.server;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;

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

    public static void printShowAccounts() {
        out.flush();
        //print accounts
        //for(int i = 0; i <= numofaccounts; i++)
        //System.out.println("(i)" + accountname: + openingbalance);
        //System.out.println("(i)" + sortcode/ + accountnumber);
    }
    public static void printShowAccounts(String s){
        out.flush();
        out.println("Here are your New Bank accounts:");
        out.println(s);

        System.out.println("printShowAccounts");
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
