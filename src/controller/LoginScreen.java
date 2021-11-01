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
import java.util.Locale;
import java.util.ResourceBundle;

public class LoginScreen implements Initializable {
    public PasswordField passwordTextBox;
    public TextField usernameTextBox;
    public Button loginButton;
    public Button exitButton;
    public Label locationLabel;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        locationLabel.setText(resourceBundle.getString("Location") + ": " + ZoneId.systemDefault());
    }

    public void onLoginButton(ActionEvent actionEvent) throws IOException {
        Stage stage = (Stage) loginButton.getScene().getWindow();
        if (UserAuth.successfulLogin(usernameTextBox.getText(), passwordTextBox.getText())) {
            GlobalController.viewAppointmentScreen(stage);
        }
    }

    public void onUsernameKeyPressed(KeyEvent keyEvent) throws IOException {
        enterLogin(keyEvent);
    }

    public void onPasswordKeyPressed(KeyEvent keyEvent) throws IOException {
        enterLogin(keyEvent);
    }

    public void enterLogin(KeyEvent keyEvent) throws IOException {
        Stage stage = (Stage) loginButton.getScene().getWindow();
        if (keyEvent.getCode() == KeyCode.ENTER)
            if (UserAuth.successfulLogin(usernameTextBox.getText(), passwordTextBox.getText()))
                GlobalController.viewAppointmentScreen(stage);
    }

    public void onExitButton(ActionEvent actionEvent) {
        System.exit(0);
    }
}
