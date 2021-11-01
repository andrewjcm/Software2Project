package utils.alerts;

import controller.GlobalController;
import javafx.scene.control.Alert;

import java.util.ResourceBundle;

/**
 * Error alert object.
 */
public class Error {

    private static final ResourceBundle resourceBundle = GlobalController.getResourceBundle();

    /**
     * No changes made after saving alert.
     */
    public static void noChangesMade() {
        Alert warning = new Alert(
                Alert.AlertType.ERROR, resourceBundle.getString("No_Changes")
        );
        warning.setTitle("");
        warning.setHeaderText(resourceBundle.getString("Error"));

        warning.showAndWait();
    }

    /**
     * No item selected alert.
     */
    public static void noItemSelected() {
        Alert warning = new Alert(
                Alert.AlertType.ERROR, resourceBundle.getString("No_Selected")
        );
        warning.setTitle("");
        warning.setHeaderText(resourceBundle.getString("Error"));

        warning.showAndWait();

    }

    /**
     * Invalid or incomplete form alert.
     */
    public static void invalidForm() {
        Alert warning = new Alert(
                Alert.AlertType.ERROR, resourceBundle.getString("Form_Invalid")
        );
        warning.setTitle("");
        warning.setHeaderText(resourceBundle.getString("Error"));

        warning.showAndWait();

    }
}
