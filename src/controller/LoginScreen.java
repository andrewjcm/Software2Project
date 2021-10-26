package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import utils.GlobalLocale;

import java.io.IOException;
import java.net.URL;
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
        locationLabel.setText(resourceBundle.getString("Location") + ": " + Locale.getDefault().getDisplayCountry());
    }

    public void onLoginButton(ActionEvent actionEvent) throws IOException {
        // TODO: Add verify user security feature

        Stage stage = (Stage) loginButton.getScene().getWindow();
        GlobalController.viewAppointmentScreen(stage);
    }

    public void onUsernameKeyPressed(KeyEvent keyEvent) throws IOException {
        enterLogin(keyEvent);
    }

    public void onPasswordKeyPressed(KeyEvent keyEvent) throws IOException {
        enterLogin(keyEvent);
    }

    public void enterLogin(KeyEvent keyEvent) throws IOException {
        // TODO: Add verify user security feature
        Stage stage = (Stage) loginButton.getScene().getWindow();
        if (keyEvent.getCode() == KeyCode.ENTER) {
            GlobalController.viewAppointmentScreen(stage);
        }
    }

    public void onExitButton(ActionEvent actionEvent) {
        System.exit(0);
    }
}