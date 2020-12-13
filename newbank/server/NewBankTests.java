package newbank.server;

import java.util.*;
import org.junit.Test;
import org.junit.Assert;

public class NewBankTests {
    AccountType mainAccountType = new AccountType("Main");
    AccountType savingAccountType = new AccountType("Saving");
    AccountType checkingAccountType = new AccountType("Checking");
    Customer testCustomer = new Customer(new CustomerID("01"));
    NewBank bank = NewBank.getBank();

    @Test
    public void testAddingAccount() {
        testCustomer.addAccount(new Account(mainAccountType, 1000.0));
        Assert.assertEquals(testCustomer.getAccountCount(), 1);
    }

    @Test
    public void testWithdrawal() {
        CustomerID customerID = new CustomerID("01");
        Customer testCustomer = new Customer(customerID);
        Account saving = new Account(savingAccountType, 1000.0);
        testCustomer.addAccount(saving);
        Account toAccount = testCustomer.getAccount(0);
        toAccount.withdraw(10);
        Assert.assertEquals(saving.getCurrentBalance(), 990.0, 0);
    }

    @Test
    public void testDeposit() {
        CustomerID customerID = new CustomerID("01");
        Customer testCustomer = new Customer(customerID);
        Account saving = new Account(savingAccountType, 1000.0);
        testCustomer.addAccount(saving);
        Account toAccount = testCustomer.getAccount(0);
        toAccount.deposit(10);
        Assert.assertEquals(saving.getCurrentBalance(), 1010.0, 0);
    }

}