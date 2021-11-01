package utils.alerts;

import controller.GlobalController;
import javafx.scene.control.Alert;

import java.util.ResourceBundle;

public class Error {

    private static final ResourceBundle resourceBundle = GlobalController.getResourceBundle();

    public static void noChangesMade() {
        Alert warning = new Alert(
                Alert.AlertType.ERROR, resourceBundle.getString("No_Changes")
        );
        warning.setTitle("");
        warning.setHeaderText(resourceBundle.getString("Error"));

        warning.showAndWait();
    }

    public static void noItemSelected() {
        Alert warning = new Alert(
                Alert.AlertType.ERROR, resourceBundle.getString("No_Selected")
        );
        warning.setTitle("");
        warning.setHeaderText(resourceBundle.getString("Error"));

        warning.showAndWait();

    }
}
