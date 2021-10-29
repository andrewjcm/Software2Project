package utils.auth;

import dao.UsersDoa;
import model.User;

public class VerifyUser {

    private static User loggedInUser;

    public static void setLoggedInUser(User user) { loggedInUser = user ;}

    public static User getLoggedInUser() { return loggedInUser; }

    public static boolean successfulLogin(String username, String password) {
        User validUser = UsersDoa.getAllUsers().stream().filter(
                u -> u.getUserName().equals(username)
        ).findAny().orElse(null);

        if (validUser == null)
            return false;
        else if (validUser.getPassword().equals(password)) {
            setLoggedInUser(validUser);
            return true;
        }
        else
            return false;
    }
}
