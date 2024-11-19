public class ElectricCar extends Cars {
    private String chargingStatus;

    public ElectricCar(String model, String licensePlate, String location, String chargingStatus) {
        super(model, licensePlate, location);
        this.chargingStatus = chargingStatus;
    }

    public String getChargingStatus() {
        return chargingStatus;
    }

    public void setChargingStatus(String chargingStatus) {
        this.chargingStatus = chargingStatus;
    }

    public String getCarDetails() {
        return super.getCarDetails() + ", Charging Status: " + chargingStatus;
    }
}
