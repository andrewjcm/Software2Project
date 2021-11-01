package utils.alerts;

import controller.GlobalController;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

import java.util.Optional;
import java.util.ResourceBundle;

/**
 * Confirmation alerts model.
 * @author Andrew Cesar-Metzgus
 */
public class Confirm {

    private static final ResourceBundle resourceBundle = GlobalController.getResourceBundle();

    /**
     * Confirm delete alert.
     * @return Confirm button.
     */
    public static Optional<ButtonType> delete() {
        Alert confirmCancel = new Alert(
                Alert.AlertType.CONFIRMATION);
        confirmCancel.setTitle(resourceBundle.getString("Confirm"));
        confirmCancel.setHeaderText(resourceBundle.getString("Delete") + "?");

        return confirmCancel.showAndWait();

    }

    /**
     * Confirm delete associated Appointments connected to Customer object being deleted.
     * @param numAppt Number of associated appointments.
     * @return Confirm button.
     */
    public static Optional<ButtonType> deleteAssociatedAppts(int numAppt) {
        Alert confirmCancel = new Alert(
                Alert.AlertType.CONFIRMATION);
        confirmCancel.setTitle(resourceBundle.getString("Confirm"));
        confirmCancel.setHeaderText(resourceBundle.getString("Delete") + " " +
                numAppt + " " + resourceBundle.getString("Associated") + "?");

        return confirmCancel.showAndWait();
    }

    /**
     * Form cancel confirmation.
     * @return Confirm button.
     */
    public static Optional<ButtonType> cancel() {
        Alert confirmCancel = new Alert(
                Alert.AlertType.CONFIRMATION, resourceBundle.getString("Alert_Data_Lost")
        );
        confirmCancel.setTitle(resourceBundle.getString("Cancel"));
        confirmCancel.setHeaderText(resourceBundle.getString("Continue") + "?");

        return confirmCancel.showAndWait();
    }

    /**
     * Modify form cancel confirmation.
     * @return Confirm button.
     */
    public static Optional<ButtonType> cancelMod() {
        Alert confirmCancel = new Alert(
                Alert.AlertType.CONFIRMATION, resourceBundle.getString("Alert_Mod_Data_Lost")
        );
        confirmCancel.setTitle(resourceBundle.getString("Cancel"));
        confirmCancel.setHeaderText(resourceBundle.getString("Continue") + "?");

        return confirmCancel.showAndWait();
    }
}
