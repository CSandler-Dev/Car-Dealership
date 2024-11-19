import java.util.*;

public class CM_UI {
    private static final Scanner scnr = new Scanner(System.in);
    private static final Fleet cmFleet = new Fleet();
    private static final UserRepo userRepo = new UserRepo();
    private static final UserReservations userReservations = new UserReservations();
    private static boolean dataInitialized = false;
    
    //MAIN and access point into CM UI / Menu Interface
    public static void main(String[] args) {
        if (!dataInitialized) {
            setupData();
            dataInitialized = true;
        }

        while (true) {
            System.out.println("\n-----[CM]-----\nWelcome to College Motors!");
            int role = userOadmin();
            if (role == 0) {
                System.out.println("Exiting system. Goodbye!");
                break;
            }

            if (role == 1 || role == 2) {
                handleLogin(role);
            }
        }
    }

    // Establishes user error loop when initially accessing the CM UI
    private static int userOadmin() {
        while (true) {
            try {
                System.out.print("1: Student Login\n2: Admin Login\n0: Exit\n-----[CM]-----\nEnter choice: ");
                int role = scnr.nextInt();
                scnr.nextLine();
                if (role >= 0 && role <= 2) {
                    return role;
                }
                System.out.println("Invalid choice. Enter 0, 1, or 2.");
            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Enter a number.");
                scnr.next();
            }
        }
    }

    private static void handleLogin(int role) {
        System.out.print("\nEnter your [CM] ID: ");
        String userId = scnr.nextLine();
        System.out.print("Enter your Passphrase: ");
        String password = scnr.nextLine();

        if (role == 1) {
            Student student = userRepo.getStudent(userId);
            if (student != null && student.login(password)) {
                studentMenu(student);
            } else {
                System.out.println("Invalid credentials for student.");
            }
        } else if (role == 2) {
            Admin admin = userRepo.getAdmin(userId);
            if (admin != null && admin.login(password)) {
                adminMenu(admin);
            } else {
                System.out.println("Invalid credentials for admin.");
            }
        }
    }

    private static void studentMenu(Student student) {
        while (true) {
            System.out.println("\nStudent Menu");
            System.out.println("1. Reserve a Car");
            System.out.println("2. View Reservations");
            System.out.println("3. End Reservation");
            System.out.println("4. Account Center");
            System.out.println("5. Logout");

            switch (getSubMenuChoice(5)) {
                case 1 -> reserveCar(student);
                case 2 -> student.viewReservations(userReservations);
                case 3 -> endReservation(student);
                case 4 -> accountCenter(student);
                case 5 -> {
                    return;
                }
                default -> System.out.println("Invalid choice.");
            }
        }
    }

    private static void adminMenu(Admin admin) {
        while (true) {
            System.out.println("\nAdmin Menu");
            System.out.println("1. Manage Users");
            System.out.println("2. Manage Fleet");
            System.out.println("3. Logout");

            switch (getSubMenuChoice(3)) {
                case 1 -> manageUsers(admin);
                case 2 -> manageFleet(admin);
                case 3 -> {
                    return;
                }
                default -> System.out.println("Invalid choice.");
            }
        }
    }

    private static void manageUsers(Admin admin) {
        while (true) {
            System.out.println("\nManage Users");
            System.out.println("1. Add User");
            System.out.println("2. Remove User");
            System.out.println("3. View All Users");
            System.out.println("0. Return to Admin Menu");

            switch (getSubMenuChoice(3)) {
                case 1 -> {
                    System.out.print("Enter User Type (1: Student, 2: Admin): ");
                    int userType = getSubMenuChoice(2);
                    System.out.print("Enter User ID: ");
                    String userId = scnr.nextLine();
                    System.out.print("Enter Password: ");
                    String password = scnr.nextLine();

                    if (userType == 1) {
                        admin.addStudent(userRepo, new Student(userId, password));
                    } else {
                        admin.addAdmin(userRepo, new Admin(userId, password));
                    }
                }
                case 2 -> {
                    System.out.print("Enter User ID to remove: ");
                    String userId = scnr.nextLine();
                    admin.removeUser(userRepo, userId);
                }
                case 3 -> admin.viewAllUsers(userRepo);
                case 0 -> {
                    return;
                }
                default -> System.out.println("Invalid choice.");
            }
        }
    }

    private static void manageFleet(Admin admin) {
        while (true) {
            System.out.println("\nManage Fleet");
            System.out.println("1. Add a Car");
            System.out.println("2. Remove a Car");
            System.out.println("3. View Fleet");
            System.out.println("0. Return to Admin Menu");

            switch (getSubMenuChoice(3)) {
                case 1 -> {
                    System.out.print("Enter Car Model: ");
                    String model = scnr.nextLine();
                    System.out.print("Enter License Plate: ");
                    String licensePlate = scnr.nextLine();
                    System.out.print("Enter Location: ");
                    String location = scnr.nextLine();
                    System.out.print("Enter Car Type (Gas: 0, Electric: 1, Hybrid: 2): ");
                    int type = getSubMenuChoice(2);

                    Cars car = switch (type) {
                        case 1 -> {
                            System.out.print("Enter Charging Status: ");
                            String chargingStatus = scnr.nextLine();
                            yield new ElectricCar(model, licensePlate, location, chargingStatus);
                        }
                        case 2 -> {
                            System.out.print("Enter Fuel Type: ");
                            String fuelType = scnr.nextLine();
                            yield new HybridCar(model, licensePlate, location, fuelType);
                        }
                        default -> new Cars(model, licensePlate, location);
                    };

                    admin.addCarToFleet(cmFleet, car);
                }
                case 2 -> {
                    System.out.print("Enter License Plate of the Car to Remove: ");
                    String licensePlate = scnr.nextLine();
                    admin.removeCarFromFleet(cmFleet, licensePlate);
                }
                case 3 -> cmFleet.displayFleet();
                case 0 -> {
                    return;
                }
                default -> System.out.println("Invalid choice.");
            }
        }
    }

    private static void reserveCar(Student student) {
        System.out.print("\nEnter car model (or press Enter to skip): ");
        String model = scnr.nextLine();
        System.out.print("Enter location (or press Enter to skip): ");
        String location = scnr.nextLine();

        List<Cars> availableCars = cmFleet.filterCars(model, location, true);
        if (availableCars.isEmpty()) {
            System.out.println("No cars matching your search criteria.");
            return;
        }

        System.out.println("\nAvailable cars:");
        availableCars.forEach(car -> System.out.println(car.getCarDetails()));

        System.out.print("Enter license plate of the car you want to reserve: ");
        String licensePlate = scnr.nextLine();

        Cars selectedCar = availableCars.stream()
                .filter(car -> car.getLicensePlate().equals(licensePlate))
                .findFirst()
                .orElse(null);

        if (selectedCar != null) {
            System.out.println("Enter reservation start date (YYYY-MM-DD HH:MM): ");
            Date startDate = readDateTime();
            System.out.println("Enter reservation end date (YYYY-MM-DD HH:MM): ");
            Date endDate = readDateTime();

            student.createReservation(selectedCar, startDate, endDate, userReservations);
        } else {
            System.out.println("Car not found.");
        }
    }

    private static void endReservation(Student student) {
        List<Reservations> reservations = userReservations.getReservationsForUser(student.getId());
        if (reservations.isEmpty()) {
            System.out.println("No active reservations to end.");
            return;
        }

        System.out.println("Select a reservation to end:");
        for (int i = 0; i < reservations.size(); i++) {
            System.out.println((i + 1) + ". " + reservations.get(i).getReservationDetails());
        }
        int choice = getSubMenuChoice(reservations.size()) - 1;
        if (choice >= 0) {
            student.endReservation(userReservations, reservations.get(choice));
        } else {
            System.out.println("Invalid selection.");
        }
    }

    private static void accountCenter(Student student) {
        System.out.println("\nOutstanding Balance: $" + student.getOutstandingFees());
        System.out.print("Enter payment amount: ");
        double payment = scnr.nextDouble();
        scnr.nextLine();
        student.payFees(payment);
    }

    private static int getSubMenuChoice(int maxOption) {
        while (true) {
            try {
                System.out.print("Enter choice: ");
                int choice = scnr.nextInt();
                scnr.nextLine();
                if (choice >= 0 && choice <= maxOption) {
                    return choice;
                }
                System.out.println("Invalid choice. Please choose between 0 and " + maxOption + ".");
            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Enter a number.");
                scnr.next();
            }
        }
    }

    private static Date readDateTime() {
        while (true) {
            try {
                System.out.print("Enter date and time (YYYY-MM-DD HH:MM): ");
                String input = scnr.nextLine();
                return new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm").parse(input);
            } catch (Exception e) {
                System.out.println("Invalid date format. Try again.");
            }
        }
    }

    private static void setupData() {
        cmFleet.addCar(new ElectricCar("Tesla", "123451", "Athens", "Full"));
        cmFleet.addCar(new HybridCar("Toyota", "123452", "Athens", "Gasoline"));
        cmFleet.addCar(new Cars("Ford", "123453", "Athens"));
        cmFleet.addCar(new Cars("BMW", "123454", "Athens"));
        cmFleet.addCar(new ElectricCar("Rivian", "123459", "Atlanta", "Half"));
        cmFleet.addCar(new HybridCar("Toyota", "123452", "Athens", "Plug in hybrid"));
        cmFleet.addCar(new Cars("Ford", "12345F", "Atlanta"));
        cmFleet.addCar(new Cars("BMW", "12345W", "Atlanta"));
        cmFleet.addCar(new HybridCar("Toyota", "123452", "Athens", "Plug in hybrid"));
        cmFleet.addCar(new Cars("Ford", "12345FK", "Savannah"));
        cmFleet.addCar(new Cars("Jeep", "12345WJ", "Savannah"));
        cmFleet.addCar(new HybridCar("Honda", "123452", "Atlanta", "Plug in hybrid"));
        cmFleet.addCar(new Cars("Chevy", "12345F", "Atlanta"));
        cmFleet.addCar(new Cars("Chevy", "12345W", "Atlanta"));

        userRepo.addStudent(new Student("student1", "pass1231"));
        userRepo.addStudent(new Student("student2", "pass1232"));
        userRepo.addStudent(new Student("student3", "pass1233"));
        userRepo.addStudent(new Student("student4", "pass1234"));
        userRepo.addAdmin(new Admin("caleb-admin1", "dr.agarsclass1"));
        userRepo.addAdmin(new Admin("rishi-admin2", "dr.agarsclass2"));
    }
}
