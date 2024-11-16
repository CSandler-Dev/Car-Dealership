import java.util.ArrayList;

public class UserRepo {
    private ArrayList<User> users;

    public UserRepo() {
        this.users = new ArrayList<>();
    }

    public void addUser(User user) {
        users.add(user);
    }

    public User authenticateUser(String userId, String password, int role) {
        for (User user : users) {
            if (user.getId().equals(userId) && user.login(password)) {
                if ((role == 1 && user instanceof Student) || (role == 2 && user instanceof Admin)) {
                    return user;
                }
            }
        }
        return null;
    }
}
