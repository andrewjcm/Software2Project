package controller;

import dao.AppointmentsDao;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import model.Appointment;

import java.io.IOException;
import java.net.URL;
import java.time.DayOfWeek;
import java.time.LocalDateTime;
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
    public TableView appointmentsTable;
    public TableColumn idCol;
    public TableColumn customerCol;
    public TableColumn titleCol;
    public TableColumn descriptionCol;
    public TableColumn typeCol;
    public TableColumn locationCol;
    public TableColumn dateCol;
    public TableColumn startCol;
    public TableColumn endCol;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        appointmentsTable.setItems(AppointmentsDao.getAllAppointments());

        idCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        customerCol.setCellValueFactory(new PropertyValueFactory<>("customer"));
        titleCol.setCellValueFactory(new PropertyValueFactory<>("title"));
        descriptionCol.setCellValueFactory(new PropertyValueFactory<>("description"));
        typeCol.setCellValueFactory(new PropertyValueFactory<>("type"));
        locationCol.setCellValueFactory(new PropertyValueFactory<>("location"));
        dateCol.setCellValueFactory(new PropertyValueFactory<>("date"));
        startCol.setCellValueFactory(new PropertyValueFactory<>("startTime"));
        endCol.setCellValueFactory(new PropertyValueFactory<>("endTime"));
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
        Appointment selectedAppt = (Appointment) appointmentsTable.getSelectionModel().getSelectedItem();
        if (selectedAppt == null)
            // TODO: Error alert
            System.out.println("No appointment selected.");
        else
            ModifyAppointmentScreen.setAppointmentToMod(selectedAppt);
            Stage stage = (Stage) modifyAppointmentButton.getScene().getWindow();
            GlobalController.modifyAppointmentScreen(stage);
    }

    public void onDeleteAppointmentButton(ActionEvent actionEvent) {
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
}
