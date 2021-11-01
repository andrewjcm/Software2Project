package utils.alerts;

import controller.GlobalController;
import javafx.scene.control.Alert;
import model.Appointment;

import java.util.ResourceBundle;

public class Login {

    private static final ResourceBundle resourceBundle = GlobalController.getResourceBundle();

    public static void noUsername(){
        Alert warning = new Alert(
                Alert.AlertType.WARNING, resourceBundle.getString("No_Username")
        );
        warning.setTitle(resourceBundle.getString("Login"));
        warning.setHeaderText(resourceBundle.getString("Warning"));

        warning.showAndWait();
    }

    public static void noPassword(){
        Alert warning = new Alert(
                Alert.AlertType.WARNING, resourceBundle.getString("No_Password")
        );
        warning.setTitle(resourceBundle.getString("Login"));
        warning.setHeaderText(resourceBundle.getString("Warning"));

        warning.showAndWait();

    }

    public static void invalidCredentials(){
        Alert warning = new Alert(
                Alert.AlertType.WARNING, resourceBundle.getString("Invalid_Creds")
        );
        warning.setTitle(resourceBundle.getString("Login"));
        warning.setHeaderText(resourceBundle.getString("Warning"));

        warning.showAndWait();

    }

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

    public static void appointment() {
        Alert message = new Alert(
                Alert.AlertType.INFORMATION, resourceBundle.getString("No_Appointments_Text")
        );
        message.setTitle(resourceBundle.getString("Welcome"));
        message.setHeaderText(resourceBundle.getString("No_Appointments_Header"));

        message.showAndWait();
    }
}
