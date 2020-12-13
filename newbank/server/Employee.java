package newbank.server;

public class Employee extends BankUser {

    private Integer ID;

    public Employee(Integer ID) {
        super(ID);
    }

    public Integer ID(){
        return ID;
    }
}