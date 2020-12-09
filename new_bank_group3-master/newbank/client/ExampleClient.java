package newbank.client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

public class ExampleClient extends Thread{
	
	private final Socket server;
	private final PrintWriter bankServerOut;
	private final BufferedReader userInput;

	public ExampleClient(String ip, int port) throws UnknownHostException, IOException {
		server = new Socket(ip,port);
		userInput = new BufferedReader(new InputStreamReader(System.in)); 
		bankServerOut = new PrintWriter(server.getOutputStream(), true);

		Thread bankServerResponseThread = new Thread() {
			private final BufferedReader bankServerIn = new BufferedReader(new InputStreamReader(server.getInputStream()));

			public void run() {
				try {
					while (true) {
						String response = bankServerIn.readLine();
						System.out.println(response);
					}
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		};
		bankServerResponseThread.start();
	}
	
	
	public void run() {
		while(true) {
			try {
				while(true) {
					String command = userInput.readLine();
					bankServerOut.println(command);
				}				
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	public static void main(String[] args) throws UnknownHostException, IOException, InterruptedException {
		new ExampleClient("localhost",14002).start();
	}
}
