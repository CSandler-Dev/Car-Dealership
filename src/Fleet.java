import java.util.ArrayList;
import java.util.HashSet;

public class Fleet {
    private HashSet<Cars> cars;
    private static final String[] LOCATIONS = {"Athens", "Atlanta", "Savannah"};

    public Fleet() {
        cars = new HashSet<>();
    }

    public static String[] getLocations() {
        return LOCATIONS;
    }

    public void addCar(Cars car) {
        if (cars.add(car)) {
            System.out.println("\nCar added to fleet: " + car.getModel() + " (" + car.getLicensePlate() + ")");
        } else {
            System.out.println("\nCar with license plate " + car.getLicensePlate() + " already exists.");
        }
    }

    public void removeCar(Cars car) {
        if (cars.remove(car)) {
            System.out.println("\nCar removed from fleet: " + car.getModel() + " (" + car.getLicensePlate() + ")");
        } else {
            System.out.println("Car not found in fleet.");
        }
    }

    public void removeCar(String licensePlate) {
        Cars carToRemove = null;
    
        for (Cars car : cars) {
            if (car.getLicensePlate().equals(licensePlate)) {
                carToRemove = car;
                break;
            }
        }
    
        if (carToRemove != null) {
            cars.remove(carToRemove);
            System.out.println("\nCar removed from fleet: " + carToRemove.getModel() + " (" + carToRemove.getLicensePlate() + ")");
        } else {
            System.out.println("\nCar with license plate " + licensePlate + " not found.");
        }
    }

    public ArrayList<Cars> filterCars(String model, String location, boolean availableOnly) {
        ArrayList<Cars> filteredCars = new ArrayList<>();
        for (Cars car : cars) {
            if (car.getModel().equals(model) && car.getLocation().equals(location) &&
                    (!availableOnly || car.isAvailable())) {
                filteredCars.add(car);
            }
        }
        if (filteredCars.isEmpty()) {
            System.out.println("No cars matching criteria found.");
        }
        return filteredCars;
    }

    public ArrayList<Cars> getAvailableCars() {
        ArrayList<Cars> availableCars = new ArrayList<>();
        for (Cars car : cars) {
            if (car.isAvailable()) {
                availableCars.add(car);
            }
        }
        return availableCars;
    }

    public void displayFleet() {
        if (cars.isEmpty()) {
            System.out.println("\nNo cars in the fleet.");
            return;
        }

        System.out.println("\nFleet Overview:");
        for (Cars car : cars) {
            String status = car.isAvailable() ? "Available" : "Reserved";
            System.out.println(car.getCarDetails() + " | Status: " + status);
            System.out.println();
        }
    }

    public Cars findCarByLicensePlate(String licensePlate) {
        for (Cars car : cars) {
            if (car.getLicensePlate().equalsIgnoreCase(licensePlate)) {
                return car;
            }
        }
        System.out.println("Car with license plate " + licensePlate + " not found.");
        return null;
    }
}
