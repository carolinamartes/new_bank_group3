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
	int fromAccountIndex = 0;
	int toAccountIndex = 0;
	double requestAmount = 0.0;

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

		// keep getting requests from the client and processing them
		try {
			//ask which user
			out.println("Enter 1 for customer login, 2 for employee login.");
			int loginChoice = in.read();
			//eats next line
			in.readLine();
			// ask for user name and password
			out.println("Enter Username");
			String userName = in.readLine();
			out.println("Enter Password");
			String password = in.readLine();
			out.println("Checking Details...");

			// authenticate user and get customer ID token from bank for use in subsequent requests
			CustomerID customer = null;
			EmployeeID employee = null;
			if (loginChoice == 1){ customer = bank.checkLogInDetails(userName, password);}
			if (loginChoice == 2){ employee = bank.checkEmployeeLogInDetails(userName, password);}

			// if the user is authenticated then get requests from the user and process them
			if(customer != null) {

				out.println("Log In Successful. What do you want to do?");

				while(true) {

					String request = in.readLine();

					System.out.println("Request from " + customer.getKey());
					processRequest(customer, request);

					}

			} else if(employee != null){

				out.println("Log In Successful. What do you want to do?");

				while(true) {

					String request = in.readLine();

					System.out.println("Request from " + employee.getKey());
					processEmployeeRequest(employee, request);

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
			NewBank.showTransferFromOptions(customer, requestAmount);
			state.push("TRANSFERFROM");
			return;
		}
		if (request.startsWith("WITHDRAWAMOUNT")){
			String requestString = request;
			requestAmount = Double.parseDouble(requestString.replace("WITHDRAWAMOUNT ", ""));
			NewBank.showTransferFromOptions(customer, requestAmount);
			state.push("WITHDRAW");
			return;
		}
		if (request.startsWith("DEPOSITAMOUNT")){
			String requestString = request;
			requestAmount = Double.parseDouble(requestString.replace("DEPOSITAMOUNT ", ""));
			NewBank.showDepositOptions(customer);
			state.push("DEPOSIT");
			return;
		}
		switch (request) {
			case "SHOWMYACCOUNTS" :
				NewBank.showMyAccounts(customer);
				state.push(request);
				break;
			case "MOVEMYMONEY" :
				menuPrinter.askTransferQuantity();
				state.push(request);
				break;
			case "WITHDRAW" :
				menuPrinter.askWithdrawQuantity();
				state.push(request);
				break;
			case "DEPOSIT" :
				menuPrinter.askDepositQuantity();
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
					NewBank.showTransferToOptions(customer, fromAccountIndex);
					state.push("TRANSFERTO");
					break;
				}
				if (state.peek().equals("TRANSFERTO")){
					NewBank.executeTransfer(customer, fromAccountIndex, toAccountIndex, requestAmount);
					break;
				}
				if (state.peek().equals("WITHDRAW")){
					NewBank.executeWithdraw(customer, fromAccountIndex, requestAmount);
					break;
				}
				if (state.peek().equals("DEPOSIT")){
					NewBank.executeDeposit(customer, toAccountIndex, requestAmount);
					break;
				}
				else {
					out.println("Invalid Request");
					break;
				}
			default :
				menuPrinter.printFail();
				break;
		}
	}
	public synchronized void processEmployeeRequest(EmployeeID employee, String request) {

		/*if (request.startsWith("TRANSFERAMOUNT")){
			String requestString = request;
			requestAmount = Double.parseDouble(requestString.replace("TRANSFERAMOUNT ", ""));
			NewBank.showTransferFromOptions(customer, requestAmount);
			state.push("TRANSFERFROM");
			return;
		}
		if (request.startsWith("WITHDRAWAMOUNT")){
			String requestString = request;
			requestAmount = Double.parseDouble(requestString.replace("WITHDRAWAMOUNT ", ""));
			NewBank.showTransferFromOptions(customer, requestAmount);
			state.push("WITHDRAW");
			return;
		}
		if (request.startsWith("DEPOSITAMOUNT")){
			String requestString = request;
			requestAmount = Double.parseDouble(requestString.replace("DEPOSITAMOUNT ", ""));
			NewBank.showDepositOptions(customer);
			state.push("DEPOSIT");
			return;
		}*/
		switch (request) {
			case "SHOWMYACCOUNTS" :
				//NewBank.showMyAccounts(customer);
				state.push(request);
				break;
			case "MOVEMYMONEY" :
				menuPrinter.askTransferQuantity();
				state.push(request);
				break;
			case "WITHDRAW" :
				menuPrinter.askWithdrawQuantity();
				state.push(request);
				break;
			case "DEPOSIT" :
				menuPrinter.askDepositQuantity();
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
				//processRequest(customer, request);
				break;
			case "1" :
			case "2" :
				if (state.peek().equals("NEWACCOUNT")){
					menuPrinter.printNewAccountsPg2();
					break;
				}
				if (state.peek().equals("TRANSFERFROM")){
					//NewBank.showTransferToOptions(customer, fromAccountIndex);
					state.push("TRANSFERTO");
					break;
				}
				if (state.peek().equals("TRANSFERTO")){
					//NewBank.executeTransfer(customer, fromAccountIndex, toAccountIndex, requestAmount);
					break;
				}
				if (state.peek().equals("WITHDRAW")){
					//NewBank.executeWithdraw(customer, fromAccountIndex, requestAmount);
					break;
				}
				if (state.peek().equals("DEPOSIT")){
					//NewBank.executeDeposit(customer, toAccountIndex, requestAmount);
					break;
				}
				else {
					out.println("Invalid Request");
					break;
				}
			default :
				menuPrinter.printFail();
				break;
		}
	}
}
