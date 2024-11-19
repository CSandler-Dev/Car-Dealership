public class Cars {
    private String model;
    private String licensePlate;
    private String location;
    private boolean isAvailable;

    public Cars(String model, String licensePlate, String location) {
        this.model = model;
        this.licensePlate = licensePlate;
        this.location = location;
        this.isAvailable = true;
    }

    public String getModel() {
        return model;
    }

    public String getLicensePlate() {
        return licensePlate;
    }

    public String getLocation() {
        return location;
    }

    public boolean isAvailable() {
        return isAvailable;
    }

    public void toggleAvailability() {
        this.isAvailable = !this.isAvailable;
        System.out.println("\nCar availability updated: " + model + " (" + licensePlate + ") is now " + (isAvailable ? "available" : "unavailable"));
    }

    public void setAvailable() {
        this.isAvailable = true;
        System.out.println("\nCar is now available: " + model + " (" + licensePlate + ")");
    }

    public void setUnavailable() {
        this.isAvailable = false;
        System.out.println("\nCar is now unavailable: " + model + " (" + licensePlate + ")");
    }

    public String getCarDetails() {
        return "Model: " + model +
               "\nLicense Plate: " + licensePlate +
               "\nLocation: " + location +
               "\nStatus: " + (isAvailable ? "Available" : "Unavailable");
    }
}
