package newbank.server;

import java.util.Stack;

public class RequestProcessor {

    int fromAccountIndex = 0;
    int toAccountIndex = 0;
    double requestAmount = 0.0;
    private static final Stack<String> state = new Stack<String>();

    public synchronized void processRequest(CustomerID customer, String request, MenuPrinter menuPrinter, NewBank bank) {
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
            requestAmount = Double.parseDouble(request.replace("TRANSFERAMOUNT ", ""));
            bank.showTransferFromOptions(customer, requestAmount, menuPrinter);
            state.push("TRANSFERFROM");
            return;
        }
        switch (request) {
            case "FREEZEACCOUNTS" :
                bank.freezeAccounts(customer, menuPrinter);
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
                processRequest(customer,request, menuPrinter, bank);
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
                    menuPrinter.printInvalid();
                    break;
                }
            default :
                menuPrinter.printFail();
                break;
        }
    }
}
