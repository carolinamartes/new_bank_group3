package newbank.server;
public class CustomerPassword {
    private final String password;
    public CustomerPassword(String password) {
        this.password = password;
    }

    public boolean verifyPassword(String password){
        return this.password.equals(password);
    }

   }
