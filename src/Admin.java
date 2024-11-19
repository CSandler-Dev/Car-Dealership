public class Admin {
    private final String id;
    private final String password;

    public Admin(String id, String password) {
        this.id = id;
        this.password = password;
    }

    public String getId() {
        return id;
    }

    public boolean login(String enteredPassword) {
        return password.equals(enteredPassword);
    }

    public void addCarToFleet(Fleet fleet, Cars car) {
        if (fleet != null && car != null) {
            fleet.addCar(car);
        } else {
            System.out.println("Invalid car or fleet.");
        }
    }

    public void removeCarFromFleet(Fleet fleet, String licensePlate) {
        if (fleet != null && licensePlate != null) {
            fleet.removeCar(licensePlate);
        } else {
            System.out.println("Invalid car or fleet.");
        }
    }

    public void viewAllUsers(UserRepo userRepo) {
        userRepo.listAllUsers();
    }

    public void addStudent(UserRepo userRepo, Student student) {
        userRepo.addStudent(student);
    }

    public void addAdmin(UserRepo userRepo, Admin admin) {
        userRepo.addAdmin(admin);
    }

    public void removeUser(UserRepo userRepo, String userId) {
        userRepo.removeUser(userId);
    }
}


