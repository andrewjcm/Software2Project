package controller;

import dao.AppointmentsDao;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import model.Appointment;
import model.User;
import utils.alerts.Confirm;
import utils.alerts.Error;
import utils.alerts.Login;
import utils.auth.UserAuth;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Optional;
import java.util.ResourceBundle;

public class ViewAppointmentsScreen implements Initializable {
    public Button appointmentsButton;
    public Button customersButton;
    public Button reportsButton;
    public Button logoutButton;
    public Button addAppointmentButton;
    public Button modifyAppointmentButton;
    public Button deleteAppointmentButton;
    public RadioButton viewWeekRadioButton;
    public RadioButton viewMonthRadioButton;
    public RadioButton viewAllRadioButton;
    public ToggleGroup tGroup;
    public TableView<Appointment> appointmentsTable;
    public TableColumn<Appointment, Integer> idCol;
    public TableColumn<Appointment, String> titleCol;
    public TableColumn<Appointment, String> descriptionCol;
    public TableColumn<Appointment, String> typeCol;
    public TableColumn<Appointment, String> locationCol;
    public TableColumn<Appointment, LocalDateTime> startCol;
    public TableColumn<Appointment, LocalDateTime> endCol;
    public TableColumn<Appointment, String> contactCol;
    public TableColumn<Appointment, Integer> customerIdCol;
    public TableColumn<Appointment, Integer> userIdCol;

    private static boolean initialLogin = true;

    public static void setInitialLogin(boolean bool) { initialLogin = bool; }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        appointmentsTable.setItems(AppointmentsDao.getAllAppointments());

        idCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        customerIdCol.setCellValueFactory(new PropertyValueFactory<>("customerId"));
        titleCol.setCellValueFactory(new PropertyValueFactory<>("title"));
        descriptionCol.setCellValueFactory(new PropertyValueFactory<>("description"));
        typeCol.setCellValueFactory(new PropertyValueFactory<>("type"));
        locationCol.setCellValueFactory(new PropertyValueFactory<>("location"));
        contactCol.setCellValueFactory(new PropertyValueFactory<>("contact"));
        startCol.setCellValueFactory(new PropertyValueFactory<>("start"));
        endCol.setCellValueFactory(new PropertyValueFactory<>("end"));
        userIdCol.setCellValueFactory(new PropertyValueFactory<>("userId"));

        viewAllRadioButton.setSelected(true);

        if (initialLogin)
            upComingAppointment();
    }

    public void onAppointmentsButton(ActionEvent actionEvent) {
        // Pass already on this screen.
    }

    public void onCustomersButton(ActionEvent actionEvent) throws IOException {
        Stage stage = (Stage) customersButton.getScene().getWindow();
        GlobalController.viewCustomerScreen(stage);
    }

    public void onReportsButton(ActionEvent actionEvent) throws IOException {
        Stage stage = (Stage) reportsButton.getScene().getWindow();
        GlobalController.reportsScreen(stage);
    }

    public void onLogoutButton(ActionEvent actionEvent) throws IOException {
        Stage stage = (Stage) logoutButton.getScene().getWindow();
        GlobalController.loginScreen(stage);
    }

    public void onAddAppointmentButton(ActionEvent actionEvent) throws IOException {
        Stage stage = (Stage) addAppointmentButton.getScene().getWindow();
        GlobalController.addAppointmentScreen(stage);
    }

    public void onModifyAppointmentButton(ActionEvent actionEvent) throws IOException {
        Appointment selectedAppt = appointmentsTable.getSelectionModel().getSelectedItem();
        if (selectedAppt == null)
            Error.noItemSelected();
        else {
            ModifyAppointmentScreen.setAppointmentToMod(selectedAppt);
            Stage stage = (Stage) modifyAppointmentButton.getScene().getWindow();
            GlobalController.modifyAppointmentScreen(stage);
        }
    }

    public void onDeleteAppointmentButton(ActionEvent actionEvent) throws SQLException {
        Appointment selectedAppt = (Appointment) appointmentsTable.getSelectionModel().getSelectedItem();
        if (selectedAppt == null)
            Error.noItemSelected();
        else {
            Optional<ButtonType> userResponse = Confirm.delete();
            if (userResponse.isPresent() && userResponse.get() == ButtonType.OK) {
                AppointmentsDao.deleteAppointment(selectedAppt);
                if (viewMonthRadioButton.isSelected())
                    onViewMonthRadioButton(actionEvent);
                else if (viewWeekRadioButton.isSelected())
                    onViewMonthRadioButton(actionEvent);
                else
                    onViewAllRadioButton(actionEvent);
            }
        }
    }

    public void onViewWeekRadioButton(ActionEvent actionEvent) {
        LocalDateTime now = LocalDateTime.now();
        appointmentsTable.setItems(
                AppointmentsDao.filterByDateRange(
                        now.with(DayOfWeek.MONDAY),
                        now.with(DayOfWeek.FRIDAY)
                )
        );
    }

    public void onViewMonthRadioButton(ActionEvent actionEvent) {
        LocalDateTime now = LocalDateTime.now();
        appointmentsTable.setItems(
                AppointmentsDao.filterByDateRange(
                        now.withDayOfMonth(1),
                        now.withDayOfMonth(now.getMonth().maxLength())
                )
        );
    }

    public void onViewAllRadioButton(ActionEvent actionEvent) {
        appointmentsTable.setItems(AppointmentsDao.getAllAppointments());
    }

    public void upComingAppointment() {
        User user = UserAuth.getLoggedInUser();
        LocalDateTime now = LocalDateTime.now();
        boolean apptExists = false;
        for (Appointment appt : AppointmentsDao.getAllAppointments()) {
            if (ChronoUnit.MINUTES.between(now, appt.getStart()) <= 15 &&
                    ChronoUnit.MINUTES.between(now, appt.getStart()) >= 0 &&
                    appt.getUser().equals(user)) {
                Login.appointment(appt);
                apptExists = true;
            }
        }
        if (!apptExists) {
            Login.appointment();
        }
        initialLogin = false;
    }
}
