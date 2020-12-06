package newbank.server;

import javax.naming.NamingEnumeration;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import static javax.swing.text.html.parser.DTDConstants.ID;

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
            s += a.toString() + "  ";
        }
        out.println(s);
    }

    //Method for printing accounts to a local text file
    public static void printaccountstotext(ArrayList<Account> accounts){
        out.flush();
        String s = "";
        if (accounts.size() == 0) {
            out.println("No accounts found for this user");
        }
        for(Account a : accounts) {
            LocalDate time = LocalDate.now();
            Date date = Calendar.getInstance().getTime();
            DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
            String t = dateFormat.format(date);
            s += a.toString() + "  ";
            // I need to modify the below path so that I can save to a user's drive as oppose to just mine
            Path path = Paths.get("C:\\Users\\samtak01\\OneDrive - Arm\\Documents\\AccountBalance.txt");
            String contents = t + System.lineSeparator() + s;

            try {
                Files.writeString(path, contents, StandardCharsets.UTF_8);
            } catch (IOException ex) {
                out.println("Something went wrong.");
            }
        }
        out.println("Statement printed to your local device");

    }
    public static void printTransferableTo(ArrayList<Account> accounts, int fromAccountID) {
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

    public static void askTransferQuantity(){
        out.println("How much would you like to transfer? Write TRANSFERAMOUNT {{amount}}");
    }

    public static void printTransferableFrom(ArrayList<Account> accounts){
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
