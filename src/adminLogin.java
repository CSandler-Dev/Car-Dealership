import java.util.*;

public class adminLogin extends login{

    Scanner scnr = new Scanner(System.in);

    public int showMenu() {
        System.out.println("Welcome Admin!");
        System.out.println("1. Login with your code");
        System.out.println("2. Become an admin");
        System.out.print("Menu Choice: ");
        int code = scnr.nextInt();

        return code;
    }

    public void createAdmin(String name, int code){
        ArrayList adminLog = new ArrayList();
    }

    public void codeLogin(int code){
        int userCode;
        userCode = code;

    }
}
