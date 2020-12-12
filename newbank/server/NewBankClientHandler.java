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
	private RequestProcessor requestProcessor;
	//private static final Stack<String> state = new Stack<String>();

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
		menuPrinter.printLogo();
		try {
			CustomerID customer = login();
			if(customer != null) {
				// Start logout timer
				Time timer = new Time();
				out.println("Log In Successful. What do you want to do?");
				timer.timer(in, menuPrinter);
				// Accept input while the timer is running
				while (!timer.n) {
					String request = in.readLine();
					timer.reset();
					System.out.println("Request from " + customer.getKey());
					requestProcessor.processRequest(customer,request, menuPrinter, bank);
				}
				logout();
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

	public void logout() {
		out.println("You have been logged out");
		try {
			in.close();
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/*public synchronized void processRequest(CustomerID customer, String request) {
		Customer customerObj = bank
		if (request.equals("1") || request.equals("2")){
			System.out.println("HERE");
			if (state.peek().equals("TRANSFERFROM")) {
				fromAccountIndex = Integer.parseInt(request) - 1;
			}
			if (state.peek().equals("TRANSFERTO")) {
				toAccountIndex = Integer.parseInt(request) - 1;
			}
		}
		if (request.startsWith("TRANSFERAMOUNT")){
			String requestString = request;
			requestAmount = Double.parseDouble(requestString.replace("TRANSFERAMOUNT ", ""));
			bank.showTransferFromOptions(customer, requestAmount, menuPrinter);
			state.push("TRANSFERFROM");
			return;
		}
		switch (request) {
			case "FREEZEACCOUNTS" :

			case "SHOWMYACCOUNTS" :
				bank.showMyAccounts(customer, menuPrinter);
				state.push(request);
				break;
			case "MOVEMYMONEY" :
				menuPrinter.askTransferQuantity();
				state.push(request);
				break;
			case "NEWACCOUNT" :
				menuPrinter.printNewAccountsPg1();
				state.push(request);
				break;
			case "LOGOUT" :
				menuPrinter.printLogOut();
				state.push(request);
				break;
			case "BACK" :
				request = state.pop();
				processRequest(customer, request);
				break;
			case "1" :
			case "2" :
				if (state.peek().equals("NEWACCOUNT")){
					menuPrinter.printNewAccountsPg2();
					break;
				}
				if (state.peek().equals("TRANSFERFROM")){
					bank.showTransferToOptions(customer, fromAccountIndex, menuPrinter);
					state.push("TRANSFERTO");
					break;
				}
				if (state.peek().equals("TRANSFERTO")){
					bank.executeTransfer(customer, fromAccountIndex, toAccountIndex, requestAmount, menuPrinter);
					break;
				}
				else {
					out.println("Invalid Request");
					break;
				}
			default :
				MenuPrinter.printFail();
				break;
		}
	}*/
}
