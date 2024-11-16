import java.util.*;

public class CM_UI {
    private static Scanner scnr = new Scanner(System.in);
    private static Fleet fleet = new Fleet();
    private static UserRepo userRepo = new UserRepo();
    private static ArrayList<Reservations> reservations = new ArrayList<>();
    private static User loggedInUser;

    public static void main(String[] args) {
        setupData();
        System.out.println("Welcome to College Motors!");
        int role = getUserRole();
        loggedInUser = authenticateUser(role);

        if (loggedInUser != null) {
            navigateMenu(role);
        } else {
            System.out.println("Authentication failed. Exiting.");
        }
    }

    private static void setupData() {
        fleet.addCar(new ElectricCar("Tesla Model 3", "ABC123", "Campus", "Full"));
        fleet.addCar(new HybridCar("Toyota Prius", "DEF456", "Campus", "Gasoline"));
        
        userRepo.addUser(new Student("student1", "pass123"));
        userRepo.addUser(new Admin("admin1", "admin123"));
    }

    

    private static int getUserRole() {
        int role;
        while (true) {
            System.out.print("1: Student Login\n2: Admin Login\n------------\nEnter choice: ");
            role = scnr.nextInt();
            if (role == 1 || role == 2) {
                break;
            } else {
                System.out.println("Invalid role. Please enter 1 or 2.");
            }
        }
        return role;
    }

    private static User authenticateUser(int role) {
        System.out.print("Enter your User ID: ");
        scnr.nextLine();  // Consume newline
        String userId = scnr.nextLine();
        System.out.print("Enter your Password: ");
        String password = scnr.nextLine();
        return userRepo.authenticateUser(userId, password, role);
    }

    private static void navigateMenu(int role) {
        if (role == 1) {
            studentMenu();
        } else if (role == 2) {
            adminMenu();
        }
    }

    private static void studentMenu() {
        while (true) {
            System.out.println("\nStudent Menu");
            System.out.println("1. Search and filter cars");
            System.out.println("2. View reservations");
            System.out.println("3. Checkout");
            System.out.println("4. Logout");

            int choice = scnr.nextInt();
            switch (choice) {
                case 1 -> searchAndFilterCars();
                case 2 -> viewReservations();
                case 3 -> checkout();
                case 4 -> { return; }
                default -> System.out.println("Invalid choice");
            }
        }
    }

    private static void adminMenu() {
        while (true) {
            System.out.println("\nAdmin Menu");
            System.out.println("1. Add car to fleet");
            System.out.println("2. Remove car from fleet");
            System.out.println("3. View all reservations");
            System.out.println("4. Logout");

            int choice = scnr.nextInt();
            scnr.nextLine();  // consume newline

            switch (choice) {
                case 1 -> addCarToFleet();
                case 2 -> removeCarFromFleet();
                case 3 -> viewAllReservations();
                case 4 -> { return; }
                default -> System.out.println("Invalid choice");
            }
        }
    }

    private static void searchAndFilterCars() {
        System.out.print("Enter model: ");
        String model = scnr.next();
        System.out.print("Enter location: ");
        String location = scnr.next();

        ArrayList<Cars> availableCars = fleet.filterCars(model, location, true);

        if (availableCars.isEmpty()) {
            System.out.println("No cars available for the given criteria.");
            return;
        }

        System.out.println("Available cars:");
        for (Cars car : availableCars) {
            System.out.println(car.getLicensePlate() + ": " + car.getModel());
        }

        System.out.print("Enter license plate of car to reserve: ");
        String licensePlate = scnr.next();
        Cars selectedCar = availableCars.stream()
                .filter(car -> car.getLicensePlate().equals(licensePlate))
                .findFirst()
                .orElse(null);

        if (selectedCar != null) {
            Date startDate = new Date();
            Date endDate = new Date();
            Reservations reservation = new Reservations(loggedInUser.getId(), selectedCar, startDate, endDate);
            reservations.add(reservation);
            ((Student) loggedInUser).addReservation(reservation);
            selectedCar.toggleAvailability();
            System.out.println("Car Reserved!!!!");
        } else {
            System.out.println("We couldn't find it, try again");
        }
    }

    private static void viewReservations() {
        System.out.println("Your reservations:");
        for (Reservations reservation : ((Student) loggedInUser).getReservations()) {
            System.out.println("Car: " + reservation.getCar().getLicensePlate() + ", Model: " + reservation.getCar().getModel());
        }
    }

    private static void checkout() {
        double outstandingFees = loggedInUser.getOutstandingFees();
        if (outstandingFees > 0) {
            System.out.println("You have outstanding fees of $" + outstandingFees + ". Please settle them before checking out.");
            return;
        }

        System.out.println("Confirming checkout for all reserved cars...");
        for (Reservations reservation : ((Student) loggedInUser).getReservations()) {
            System.out.println("Checked out car: " + reservation.getCar().getLicensePlate());
            reservation.getCar().toggleAvailability();
        }
        ((Student) loggedInUser).getReservations().clear();
        System.out.println("Checkout complete. Thank you for using College Motors!");
    }

    private static void addCarToFleet() {
        System.out.print("Enter model: ");
        String model = scnr.next();
        System.out.print("Enter license plate: ");
        String licensePlate = scnr.next();
        System.out.print("Enter location: ");
        String location = scnr.next();
        System.out.print("Enter car type (1 for Electric, 2 for Hybrid): ");
        int carType = scnr.nextInt();

        Cars newCar;
        if (carType == 1) {
            System.out.print("Enter charging status: ");
            String chargingStatus = scnr.next();
            newCar = new ElectricCar(model, licensePlate, location, chargingStatus);
        } else {
            System.out.print("Enter fuel type: ");
            String fuelType = scnr.next();
            newCar = new HybridCar(model, licensePlate, location, fuelType);
        }

        fleet.addCar(newCar);
        System.out.println("Car added to fleet.");
    }

    private static void removeCarFromFleet() {
        System.out.print("Enter license plate of car to remove: ");
        String licensePlate = scnr.next();

        Cars carToRemove = fleet.filterCars("", "", false).stream()
                .filter(car -> car.getLicensePlate().equals(licensePlate))
                .findFirst()
                .orElse(null);

        if (carToRemove != null) {
            fleet.removeCar(carToRemove);
            System.out.println("Car removed from fleet.");
        } else {
            System.out.println("Car with specified license plate not found.");
        }
    }

    private static void viewAllReservations() {
        System.out.println("All reservations:");
        for (Reservations reservation : reservations) {
            System.out.println("Student ID: " + reservation.getStudentId() + ", Car: " + reservation.getCar().getLicensePlate());
        }
    }
}
