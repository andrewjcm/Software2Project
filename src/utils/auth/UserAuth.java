package utils.auth;

import dao.UsersDoa;
import model.User;
import utils.alerts.Login;
import utils.auth.log.LoginActivity;

public class UserAuth {

    private static User loggedInUser;

    public static void setLoggedInUser(User user) { loggedInUser = user ;}

    public static User getLoggedInUser() { return loggedInUser; }

    public static boolean successfulLogin(String username, String password) {
        User validUser = UsersDoa.getAllUsers().stream().filter(
                u -> u.getUserName().equals(username)
        ).findAny().orElse(null);

        if (validUser == null) {
            Login.noUsername();
            LoginActivity.log(null, false, "No username entered.");
            return false;
        }
        else if (password.equals("")) {
            Login.noPassword();
            LoginActivity.log(validUser.toString(), false, "No password entered.");
            return false;
        }
        else if (validUser.getPassword().equals(password)) {
            setLoggedInUser(validUser);
            LoginActivity.log(validUser.toString(), true, "Valid credentials.");
            return true;
        }
        else {
            Login.invalidCredentials();
            LoginActivity.log(validUser.toString(), false, "Invalid credentials.");
            return false;
        }
    }
}
