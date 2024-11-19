public class HybridCar extends Cars {
    private String fuelType;

    public HybridCar(String model, String licensePlate, String location, String fuelType) {
        super(model, licensePlate, location);
        this.fuelType = fuelType;
    }

    public String getFuelType() {
        return fuelType;
    }

    public void setFuelType(String fuelType) {
        this.fuelType = fuelType;
    }

    public String getCarDetails() {
        return super.getCarDetails() + ", Fuel Type: " + fuelType;
    }
}
