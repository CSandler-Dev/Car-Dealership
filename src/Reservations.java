import java.util.Date;

public class Reservations {
    private final String studentId;
    private final Cars car;
    private Date startDate;
    private Date endDate;
    private boolean isActive;

    public Reservations(String studentId, Cars car, Date startDate, Date endDate) {
        this.studentId = studentId;
        this.car = car;
        this.startDate = startDate;
        this.endDate = endDate;
        this.isActive = true;
        car.setUnavailable();
    }

    public String getStudentId() {
        return studentId;
    }

    public Cars getCar() {
        return car;
    }

    public Date getStartDate() {
        return new Date(startDate.getTime());
    }

    public Date getEndDate() {
        return new Date(endDate.getTime());
    }

    public boolean isActive() {
        return isActive;
    }

    public void updateDates(Date newStartDate, Date newEndDate) {
        if (!isActive) {
            System.out.println("Cannot update an inactive reservation.");
            return;
        }

        if (newEndDate.after(newStartDate)) {
            this.startDate = newStartDate;
            this.endDate = newEndDate;
            System.out.println("\nReservation dates updated: Start Date - " + newStartDate + ", End Date - " + newEndDate);
        } else {
            System.out.println("End date must be after the start date.");
        }
    }

    public void cancelReservation() {
        if (!isActive) {
            System.out.println("This reservation is already inactive.");
            return;
        }

        isActive = false;
        car.setAvailable();
        System.out.println("\nReservation canceled for car: " + car.getCarDetails());
    }

    public String getReservationDetails() {
        return  "\nCar: " + car.getCarDetails() +
                "\nStart Date: " + startDate + 
                "\nEnd Date: " + endDate + 
                "\nStatus: " + (isActive ? "Active" : "Inactive");
    }
}




