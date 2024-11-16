import java.util.Date;

public class Reservations {
    private String studentId;
    private Cars car;
    private Date startDate;
    private Date endDate;

    public Reservations(String studentId, Cars car, Date startDate, Date endDate) {
        this.studentId = studentId;
        this.car = car;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public Cars getCar() {
        return car;
    }

    public String getStudentId() {
        return studentId;
    }
}
