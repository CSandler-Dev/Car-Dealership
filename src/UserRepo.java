import java.util.HashMap;

public class UserRepo {
    private final HashMap<String, Student> students;
    private final HashMap<String, Admin> admins;

    public UserRepo() {
        this.students = new HashMap<>();
        this.admins = new HashMap<>();
    }

    public void addStudent(Student student) {
        if (students.containsKey(student.getId())) {
            System.out.println("Student with ID " + student.getId() + " already exists.");
            return;
        }
        students.put(student.getId(), student);
        System.out.println("Student added: " + student.getId());
    }

    public void addAdmin(Admin admin) {
        if (admins.containsKey(admin.getId())) {
            System.out.println("Admin with ID " + admin.getId() + " already exists.");
            return;
        }
        admins.put(admin.getId(), admin);
        System.out.println("Admin added: " + admin.getId());
    }

    public Student getStudent(String userId) {
        return students.get(userId);
    }

    public Admin getAdmin(String userId) {
        return admins.get(userId);
    }

    public void removeUser(String userId) {
        if (students.remove(userId) != null || admins.remove(userId) != null) {
            System.out.println("User removed: " + userId);
        } else {
            System.out.println("User with ID " + userId + " not found.");
        }
    }

    public void listAllUsers() {
        System.out.println("Students:");
        students.values().forEach(student -> System.out.println(" - " + student.getId()));
        System.out.println("Admins:");
        admins.values().forEach(admin -> System.out.println(" - " + admin.getId()));
    }
}

