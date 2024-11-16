public class ElectricCar extends Cars {
    private String chargingStatus;

    public ElectricCar(String model, String licensePlate, String location, String chargingStatus) {
        super(model, licensePlate, location);
        this.chargingStatus = chargingStatus;
    }
}
