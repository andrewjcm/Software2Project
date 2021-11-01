# Customer Appointment Manager

The purpose of this application is to view, create, update and delete Customers and assign Appointments.
It also runs pre-specified reports of the number of appointments by month, type and division and a schedule of
appointments for each Contact.

### Author
Andrew Cesar-Metzgus<br>
acesarm@wgu.edu<br>

### Application Version
Nov. 1, 2021
v1.0

### Creation Software
IDE: Intellij Community Edition 2021.1.1<br>
JDK Version: 11.0.11<br>
JavaFX-SDK Version: 11.0.2<br>

### Instructions
1. Login
    1. Enter username and password
    2. Press enter or click login
        1. All Login activity is logged. Log file is located in source directory.
2. Filter by Appointment View by week, month or all
    1. Default view is all. Select week or month to apply desired filter.
3. Create Appointment
    1. Click Add on Appointments screen.
    2. Fill in all form fields for appointment.
    3. Click save.
        1. System will check to ensure no other appointments are scheduled for chosen time and that selected time is 
           within business hours.
4. Modify Appointment
    1. Select appointment to modify and click Modify on Appointments screen.
    2. Modify any form fields for appointment.
    3. Click save.
        1. System will check to ensure no other appointments are scheduled for chosen time and that selected time is
           within business hours.
5. Delete Appointment
   1. Select appointment to delete and click delete on Appointments screen.
   2. Confirm deletion.
6. View existing Customers
    1. Click Customers button
7. Create Customer
    1. Click Add on Customer screen.
    2. Fill in all form fields for appointment.
    3. Click save.
8. Modify Customer
    1. Select customer to modify and click Modify on Customer screen.
    2. Fill in all form fields for appointment.
    3. Click save.
9. Delete Customer
    1. Select customer to delete and click delete on Customer screen.
    2. Confirm deletion.
        1. System will check that there are no associated appointments. If appointments are associated, system will
        prompt user to delete all associated appointments.
10. View Reports
    1. From any screen click Reports
11. Logout
    1. From any screen click Logout
    
### Additional Report
The additional report queries the database with three table join statement. The report generates the number of 
appointments per first level division.

### Database version
mysql-connector-java-8.0.25