public class HybridCar extends Cars {
    private String fuelType;

    public HybridCar(String model, String licensePlate, String location, String fuelType) {
        super(model, licensePlate, location);
        this.fuelType = fuelType;
    }
}
