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
		out.println("Maximum Number of login attempts for this session reached");
		return null;
	}

	public void run() {

		menuPrinter.printLogo();

		// keep getting requests from the client and processing them
		try {

			out.println("Welcome to NewBank");
			CustomerID customer = null;
			customer = login();
			if (customer != null) {
				out.println("Log In Successful. What do you want to do?");


				while (true) {

					String request = in.readLine();

					System.out.println("Request from " + customer.getKey());
					processRequest(customer, request);

				}

			} else {
				out.println("Log In Failed");

			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				in.close();
				out.close();
			} catch (IOException e) {
				e.printStackTrace();
				Thread.currentThread().interrupt();
			}
		}
	}

	public synchronized void processRequest(CustomerID customer, String request) throws IOException {
		if (request.equals("1") || request.equals("2")){
			if (state.peek().equals("TRANSFERFROM")) {
				fromAccountIndex = Integer.parseInt(request) - 1;
			}
			if (state.peek().equals("TRANSFERTO")) {
				toAccountIndex = Integer.parseInt(request) - 1;
			}
			if (state.peek().equals("SEND")) {
				fromAccountIndex = Integer.parseInt(request) - 1;
			}
		}
		if (request.startsWith("TRANSFERAMOUNT")){
			boolean validAmount = checkIfValidAmountFormat(request, "TRANSFERAMOUNT ");
			if (validAmount) {
				NewBank.showTransferFromOptions(customer, requestAmount);
				state.push("TRANSFERFROM");
			}
			return;
		}
		if (request.startsWith("WITHDRAWAMOUNT")){
			boolean validAmount = checkIfValidAmountFormat(request, "WITHDRAWAMOUNT ");
			if (validAmount){
				NewBank.showTransferFromOptions(customer, requestAmount);
				state.push("WITHDRAW");
			}
			return;
		}
		if (request.startsWith("DEPOSITAMOUNT")){
			boolean validAmount = checkIfValidAmountFormat(request, "DEPOSITAMOUNT ");
			if (validAmount){
				NewBank.showDepositOptions(customer);
				state.push("DEPOSIT");
			}
			return;
		}
		if (request.startsWith("SENDAMOUNT")){
			boolean validAmount = checkIfValidAmountFormat(request, "SENDAMOUNT ");
			if (validAmount){
				NewBank.showTransferFromOptions(customer, requestAmount);
				state.push("SEND");
			}
			return;
		}
		if (request.startsWith("RECIPIENT")){
			String requestString = request;
			String recipient = requestString.replace("RECIPIENT ", "");
			Integer toAccountID = NewBank.getAccountID(recipient);
			if (toAccountID >= 0){
				NewBank.executeSendMoney(customer, recipient, toAccountID, fromAccountIndex, requestAmount);
			}
			else {
				MenuPrinter.printFail();
			}
			return;
		}
		switch (request) {
			case "SENDMONEY" :
				menuPrinter.askSendQuantity();
				state.push(request);
				break;
			case "SHOWMYACCOUNTS" :
				NewBank.showMyAccounts(customer);
				state.push(request);
				break;
			case "PRINTACCOUNTS":
				NewBank.printsAccountsToText(customer);
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
			case "CHANGEPASSWORD":
				out.println("Enter your userName");
				String userName = in.readLine();
				out.println("Enter your current password");
				String currentPassword = in.readLine();
				CustomerID PasswordChange = bank.checkLogInDetails(userName, currentPassword);
				if (customer == null) {
					out.println("user not recognized");
				} else {
					out.println("Enter your new password, this must be a minimum of 8 characters");
					String temp = in.readLine();
					if (temp.length() <= 7 || currentPassword.equals(temp)) {
						out.println("Something went wrong");
						return;
					} else {
						out.println("Please re-enter your new password");
						String temp2 = in.readLine();
						if (temp.equals(temp2)) {
							CustomerID ID = bank.ChangePassword(userName, temp, currentPassword);
							out.println("Password Successfully Changed");
							return;

						} else {
							out.println("Passwords do not match");
							return;
						}
					}
				}


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
				if (state.peek().equals("SEND")){
					menuPrinter.askRecipient();
					break;
				}
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
					menuPrinter.printInvalidRequest();
					break;
				}
			default :
				menuPrinter.printFail();
				break;
		}
	}
	public boolean checkIfValidAmountFormat(String requestString, String parseString){
		try {
			requestAmount = Double.parseDouble(requestString.replace(parseString, ""));
			if (requestAmount <= 0){
				menuPrinter.printInvalidRequest();
				return false;
			}
			return true;
		}
		catch(NumberFormatException e){
			MenuPrinter.printFail();
			return false;
		}
	}
}
