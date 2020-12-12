package newbank.server;

public class EmployeePassword {

    private final String password;

    public EmployeePassword(String password) {
        this.password = password;
    }

    public boolean verifyPassword(String password){
        return this.password.equals(password);
    }

}