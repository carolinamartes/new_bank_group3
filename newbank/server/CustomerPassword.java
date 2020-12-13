package newbank.server;

public class CustomerPassword {
    private String password;

    public CustomerPassword(String password) {
        this.password = password;
    }

    //Added functionality for changing password;
    public void changePassword(String password) {
        this.password = password;
    }

    public boolean verifyPassword(String password) {
        return this.password.equals(password);
    }
}
