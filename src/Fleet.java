import java.util.ArrayList;

public class Fleet {
    private ArrayList<Cars> cars;

    public Fleet() {
        this.cars = new ArrayList<>();
    }

    public void addCar(Cars car) {
        cars.add(car);
    }

    public void removeCar(Cars car) {
        cars.remove(car);
    }

    public ArrayList<Cars> filterCars(String model, String location, boolean availableOnly) {
        ArrayList<Cars> filteredCars = new ArrayList<>();
        for (Cars car : cars) {
            if (car.getModel().equals(model) && car.getLocation().equals(location) &&
               (!availableOnly || car.isAvailable())) {
                filteredCars.add(car);
            }
        }
        return filteredCars;
    }
}
