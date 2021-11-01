package utils.alerts;

import controller.GlobalController;
import javafx.scene.control.Alert;
import model.Appointment;

import java.util.ResourceBundle;

/**
 * Login alert object.
 */
public class Login {

    private static final ResourceBundle resourceBundle = GlobalController.getResourceBundle();

    /**
     * No username entered alert.
     */
    public static void noUsername(){
        Alert warning = new Alert(
                Alert.AlertType.WARNING, resourceBundle.getString("No_Username")
        );
        warning.setTitle(resourceBundle.getString("Login"));
        warning.setHeaderText(resourceBundle.getString("Warning"));

        warning.showAndWait();
    }

    /**
     * No password entered alert.
     */
    public static void noPassword(){
        Alert warning = new Alert(
                Alert.AlertType.WARNING, resourceBundle.getString("No_Password")
        );
        warning.setTitle(resourceBundle.getString("Login"));
        warning.setHeaderText(resourceBundle.getString("Warning"));

        warning.showAndWait();

    }

    /**
     * Invalid credentials alert.
     */
    public static void invalidCredentials(){
        Alert warning = new Alert(
                Alert.AlertType.WARNING, resourceBundle.getString("Invalid_Creds")
        );
        warning.setTitle(resourceBundle.getString("Login"));
        warning.setHeaderText(resourceBundle.getString("Warning"));

        warning.showAndWait();

    }

    /**
     * User has upcoming appointment alert.
     * @param appt Upcoming appointment.
     */
    public static void appointment(Appointment appt) {
        Alert message = new Alert(
                Alert.AlertType.INFORMATION, resourceBundle.getString("ID") +
                ": " + appt.getId() + ", " + resourceBundle.getString("Start") +
                ": " + appt.getStart()
        );
        message.setTitle(resourceBundle.getString("Welcome"));
        message.setHeaderText(resourceBundle.getString("Up_Coming_Appt"));

        message.showAndWait();
    }

    /**
     * User has no upcoming appointments alert.
     */
    public static void appointment() {
        Alert message = new Alert(
                Alert.AlertType.INFORMATION, resourceBundle.getString("No_Appointments_Text")
        );
        message.setTitle(resourceBundle.getString("Welcome"));
        message.setHeaderText(resourceBundle.getString("No_Appointments_Header"));

        message.showAndWait();
    }
}
