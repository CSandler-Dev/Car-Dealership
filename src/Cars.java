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

    public void toggleAvailability() {
        this.isAvailable = !this.isAvailable;
    }

    public boolean isAvailable() {
        return this.isAvailable;
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
}
