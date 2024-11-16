public class User {
    private String id;
    private String password;
    private double outstandingFees;

    public User(String id, String password) {
        this.id = id;
        this.password = password;
        this.outstandingFees = 0.0;
    }

    public String getId() {
        return id;
    }

    public boolean login(String enteredPassword) {
        return this.password.equals(enteredPassword);
    }

    public double getOutstandingFees() {
        return outstandingFees;
    }

    public void addFees(double amount) {
        this.outstandingFees += amount;
    }
}
