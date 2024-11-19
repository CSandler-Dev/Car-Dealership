import java.util.ArrayList;
import java.util.List;

public class UserReservations {
    private final List<Reservations> reservations;

    public UserReservations() {
        this.reservations = new ArrayList<>();
    }

    public void addReservation(Reservations reservation) {
        if (reservation != null) {
            reservations.add(reservation);
            System.out.println("\nReservation added for user: " + reservation.getStudentId());
        } else {
            System.out.println("Invalid reservation. Cannot be added.");
        }
    }

    public void removeReservation(Reservations reservation) {
        if (reservations.remove(reservation)) {
            System.out.println("\nReservation removed for user: " + reservation.getStudentId());
        } else {
            System.out.println("Reservation not found. Cannot remove.");
        }
    }

    public List<Reservations> getReservationsForUser(String userId) {
        List<Reservations> userReservations = new ArrayList<>();
        for (Reservations reservation : reservations) {
            if (reservation.getStudentId().equals(userId)) {
                userReservations.add(reservation);
            }
        }
        return userReservations;
    }

    public List<Reservations> getAllReservations() {
        return new ArrayList<>(reservations);
    }
}

