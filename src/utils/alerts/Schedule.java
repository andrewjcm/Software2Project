package utils.alerts;

import controller.GlobalController;
import javafx.scene.control.Alert;

import java.util.ResourceBundle;

public class Schedule {

    private static final ResourceBundle resourceBundle = GlobalController.getResourceBundle();

    public static void overlap () {
        Alert warning = new Alert(
                Alert.AlertType.ERROR, resourceBundle.getString("Time_Not_Avail")
        );
        warning.setTitle(resourceBundle.getString("Appointment"));
        warning.setHeaderText(resourceBundle.getString("Error"));

        warning.showAndWait();
    }

    public static void outsideBizHours() {
        Alert warning = new Alert(
                Alert.AlertType.ERROR, resourceBundle.getString("Outside_Biz_Hours")
        );
        warning.setTitle(resourceBundle.getString("Appointment"));
        warning.setHeaderText(resourceBundle.getString("Error"));

        warning.showAndWait();
    }
}
