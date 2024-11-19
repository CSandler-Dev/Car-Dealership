import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class Student {
    private final String id;
    private final String password;
    private double outstandingFees;

    public Student(String id, String password) {
        this.id = id;
        this.password = password;
        this.outstandingFees = 0.0;
    }

    public String getId() {
        return id;
    }

    public double getOutstandingFees() {
        return outstandingFees;
    }

    public boolean login(String enteredPassword) {
        if (this.password.equals(enteredPassword)) {
            System.out.println("\nLogin successful for user: " + id + "\n-----[CM]-----");
            return true;
        } else {
            System.out.println("\nLogin failed for user: " + id + "\n-----[CM]-----");
            return false;
        }
    }

    public void addFees(double amount) {
        if (amount > 0) {
            outstandingFees += amount;
            System.out.println("\nAdded fees for user: " + id + ". New outstanding balance: $" + outstandingFees);
        }
    }

    public void payFees(double amount) {
        if (amount <= 0) {
            System.out.println("Invalid payment amount. Must be greater than zero.");
            return;
        }

        if (amount > outstandingFees) {
            System.out.println("Payment exceeds outstanding fees. Paying only the outstanding balance.");
            amount = outstandingFees;
        }

        outstandingFees -= amount;
        System.out.println("\nPayment successful for user: " + id + ". Remaining balance: $" + outstandingFees);
    }

    public boolean hasOutstandingFees() {
        return outstandingFees > 0;
    }

    public void viewReservations(UserReservations userReservations) {
        List<Reservations> reservations = userReservations.getReservationsForUser(id);
        if (reservations.isEmpty()) {
            System.out.println("No active reservations found.");
            return;
        }

        System.out.println("\nYour Reservations:");
        for (int i = 0; i < reservations.size(); i++) {
            System.out.println((i + 1) + ". " + reservations.get(i).getReservationDetails());
        }
    }

    public void createReservation(Cars car, Date startDate, Date endDate, UserReservations userReservations) {
        if (car == null || startDate == null || endDate == null || !endDate.after(startDate)) {
            System.out.println("Invalid reservation details. Please try again.");
            return;
        }

        Reservations newReservation = new Reservations(id, car, startDate, endDate);
        userReservations.addReservation(newReservation);
    }

    public void endReservation(UserReservations userReservations, Reservations reservation) {
        List<Reservations> reservations = userReservations.getReservationsForUser(id);

        if (!reservations.contains(reservation)) {
            System.out.println("Reservation not found.");
            return;
        }

        double rentalFee = calculateRentalFee(reservation);
        addFees(rentalFee);

        reservation.cancelReservation();
        userReservations.removeReservation(reservation);

        System.out.println("\nReservation ended. Rental Fee: $" + rentalFee);
    }

    private double calculateRentalFee(Reservations reservation) {
        double hourlyRate = 15.0;
        double baseCost = 25.0;

        if (reservation.getCar() instanceof ElectricCar) {
            hourlyRate = 20.0;
        } else if (reservation.getCar() instanceof HybridCar) {
            hourlyRate = 17.5;
        }

        long rentalHours = calculateRentalDurationInHours(reservation.getStartDate(), reservation.getEndDate());
        return (rentalHours * hourlyRate) + baseCost;
    }

    private long calculateRentalDurationInHours(Date startDate, Date endDate) {
        long diffInMillis = endDate.getTime() - startDate.getTime();
        return TimeUnit.HOURS.convert(diffInMillis, TimeUnit.MILLISECONDS);
    }
}



