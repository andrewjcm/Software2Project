package utils.alerts;

import controller.GlobalController;
import javafx.scene.control.Alert;

import java.util.ResourceBundle;

/**
 * Schedule alert class.
 */
public class Schedule {

    private static final ResourceBundle resourceBundle = GlobalController.getResourceBundle();

    /**
     * Alerts user that appointment time is taken.
     */
    public static void overlap () {
        Alert warning = new Alert(
                Alert.AlertType.ERROR, resourceBundle.getString("Time_Not_Avail")
        );
        warning.setTitle(resourceBundle.getString("Appointment"));
        warning.setHeaderText(resourceBundle.getString("Error"));

        warning.showAndWait();
    }

    /**
     * Alerts user that selected appointment time is outside of business hours.
     */
    public static void outsideBizHours() {
        Alert warning = new Alert(
                Alert.AlertType.ERROR, resourceBundle.getString("Outside_Biz_Hours")
        );
        warning.setTitle(resourceBundle.getString("Appointment"));
        warning.setHeaderText(resourceBundle.getString("Error"));

        warning.showAndWait();
    }
}
