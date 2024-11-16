import java.util.ArrayList;

public class Student extends User {
    private ArrayList<Reservations> reservations;

    public Student(String id, String password) {
        super(id, password);
        this.reservations = new ArrayList<>();
    }

    public void addReservation(Reservations reservation) {
        this.reservations.add(reservation);
    }

    public ArrayList<Reservations> getReservations() {
        return reservations;
    }
}
