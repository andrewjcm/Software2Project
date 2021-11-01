package controller;

import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import utils.auth.UserAuth;

import java.io.IOException;
import java.net.URL;
import java.time.ZoneId;
import java.util.ResourceBundle;

/**
 * Controller for login screen.
 * @author Andrew Cesar-Metzgus
 */
public class LoginScreen implements Initializable {
    public PasswordField passwordTextBox;
    public TextField usernameTextBox;
    public Button loginButton;
    public Button exitButton;
    public Label locationLabel;

    /**
     * Initializes the login screen.
     * @param url url
     * @param resourceBundle Resource Bundle.
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        locationLabel.setText(resourceBundle.getString("Location") + ": " + ZoneId.systemDefault());
    }

    /**
     * Button that checks login credentials and moves to view appointment screen
     * on successful login.
     * @param actionEvent Button click.
     * @throws IOException
     */
    public void onLoginButton(ActionEvent actionEvent) throws IOException {
        Stage stage = (Stage) loginButton.getScene().getWindow();
        if (UserAuth.successfulLogin(usernameTextBox.getText(), passwordTextBox.getText())) {
            GlobalController.viewAppointmentScreen(stage);
        }
    }

    /**
     * Checks the key event of username text field.
     * @param keyEvent Key event.
     * @throws IOException
     */
    public void onUsernameKeyPressed(KeyEvent keyEvent) throws IOException {
        enterLogin(keyEvent);
    }

    /**
     * Checks the key event of username text field.
     * @param keyEvent Key event.
     * @throws IOException
     */
    public void onPasswordKeyPressed(KeyEvent keyEvent) throws IOException {
        enterLogin(keyEvent);
    }

    /**
     * Checks if key event was the enter button pressed. If the enter
     * button is press, user credentials are checked and if valid,
     * customer is logged in and moved to view appointment screen.
     * @param keyEvent
     * @throws IOException
     */
    public void enterLogin(KeyEvent keyEvent) throws IOException {
        Stage stage = (Stage) loginButton.getScene().getWindow();
        if (keyEvent.getCode() == KeyCode.ENTER)
            if (UserAuth.successfulLogin(usernameTextBox.getText(), passwordTextBox.getText()))
                GlobalController.viewAppointmentScreen(stage);
    }

    /**
     * Closes the program.
     * @param actionEvent Exit button pressed.
     */
    public void onExitButton(ActionEvent actionEvent) {
        System.exit(0);
    }
}
