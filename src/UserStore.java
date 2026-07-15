import java.util.ArrayList;
import java.util.List;

public class UserStore {

     private static final List<String[]> users = new ArrayList<>();

    static {
         users.add(new String[]{"user1", "user1@email.com", "123"});
    }

   
    public static boolean register(String username, String email, String password) {
        for (String[] u : users) {
            if (u[0].equalsIgnoreCase(username)) {
                return false; // username already exists
            }
        }
        users.add(new String[]{username, email, password});
        return true;
    }

    
    public static boolean loginValid(String username, String password) {
        for (String[] u : users) {
            if (u[0].equals(username) && u[2].equals(password)) {
                return true;
            }
        }
        return false;
    }

    
    public static boolean usernameExists(String username) {
        for (String[] u : users) {
            if (u[0].equalsIgnoreCase(username)) return true;
        }
        return false;
    }
}