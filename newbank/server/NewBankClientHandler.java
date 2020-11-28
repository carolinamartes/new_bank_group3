package newbank.server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Stack;

public class NewBankClientHandler extends Thread {

	private final NewBank bank;
	private final BufferedReader in;
	private final PrintWriter out;
	private final MenuPrinter menuPrinter;
	private static Stack<String> state = new Stack<String>();

	public NewBankClientHandler(Socket s) throws IOException {
		bank = NewBank.getBank();
		in = new BufferedReader(new InputStreamReader(s.getInputStream()));
		out = new PrintWriter(s.getOutputStream(), true);
		menuPrinter = new MenuPrinter(s);
	}
	public CustomerID login() throws IOException {
		//User will get 3 chances
		int attempt = 0;
		while (attempt < 3) {
			out.println("Enter details for login");
			out.println("Enter Username");
			// ask for user name
			String userName = in.readLine();
			// ask for password
			out.println("Enter Password");
			String password = in.readLine();
			out.println("Checking Details...");
			// authenticate user and get customer ID token from bank for use in subsequent requests
			CustomerID customer = bank.checkLogInDetails(userName, password);
			// if the user is authenticated then get requests from the user and process them
			if (customer != null)
				return customer;
			out.println("Invalid Username or Password");
			attempt++;
		}
		System.out.println("You have entered an incorrect User or password three times, please try again later.");
		System.exit(-1);
		return null;

	}

	public void run() {

		MenuPrinter.printLogo();

		// keep getting requests from the client and processing them
		try {
			//Salutation
			out.println("Welcome to NewBank");
			String entry = "";
			CustomerID customer = null;
			customer = login();
			if( customer!= null) {
				out.println("Log In Successful. What do you want to do?");

				while(true) {

					String request = in.readLine();

					System.out.println("Request from " + customer.getKey());

					/*if (bank.validator(customer).equals("valid")){

						//processRequest();
					}
					else {
						out.println("FAIL");*/
					}
				}

			else {
				out.println("Log In Failed");
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		finally {
			try {
				in.close();
				out.close();
			} catch (IOException e) {
				e.printStackTrace();
				Thread.currentThread().interrupt();
			}
		}
	}


	public synchronized void processRequest(CustomerID customer, String request) {
		//add relevant code here
		switch (request) {
			case "SHOWMYACCOUNTS" :
				NewBank.showMyAccounts(customer);
				state.push(request);
				break;
			case "NEWACCOUNT" :
				MenuPrinter.printNewAccountsPg1();
				state.push(request);
				break;
			case "LOGOUT" :
				MenuPrinter.printLogOut();
				state.push(request);
				break;
			case "BACK" :
				request = state.pop();
				processRequest(customer, request);
				break;
			case "1" :
			case "2" :
				if (state.peek().equals("NEWACCOUNT")){
					MenuPrinter.printNewAccountsPg2();
					break;
				} else {
					out.println("Invalid Request");
					break;
				}
			default :
				MenuPrinter.printFail();
				break;
		}
	}
}


