package model;

import java.time.LocalDateTime;

public class Appointment {
    private int id;
    private String title;
    private String description;
    private String location;
    private String type;
    private LocalDateTime start;
    private LocalDateTime end;
    private LocalDateTime createDate;
    private String createdBy;
    private LocalDateTime lastUpdate;
    private String lastUpdatedBy;
    private Customer customer;
    private User user;
    private Contact contact;

    public Appointment(int id, String title, String description, String location, String type, LocalDateTime start,
                       LocalDateTime end, LocalDateTime createDate, String createdBy, LocalDateTime lastUpdate,
                       String updatedBy, Customer customer, User user, Contact contact) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.location = location;
        this.type = type;
        this.start = start;
        this.end = end;
        this.createDate = createDate;
        this.createdBy = createdBy;
        this.lastUpdate = lastUpdate;
        this.lastUpdatedBy = updatedBy;
        this.customer = customer;
        this.user = user;
        this.contact = contact;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public LocalDateTime getStart() {
        return start;
    }

    public void setStart(LocalDateTime start) {
        this.start = start;
    }

    public LocalDateTime getEnd() {
        return end;
    }

    public void setEnd(LocalDateTime end) {
        this.end = end;
    }

    public LocalDateTime getCreateDate() {
        return createDate;
    }

    public void setCreateDate(LocalDateTime createDate) {
        this.createDate = createDate;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public LocalDateTime getLastUpdate() {
        return lastUpdate;
    }

    public void setLastUpdate(LocalDateTime lastUpdate) {
        this.lastUpdate = lastUpdate;
    }

    public String getUpdatedBy() {
        return lastUpdatedBy;
    }

    public void setUpdatedBy(String updatedBy) {
        this.lastUpdatedBy = updatedBy;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Contact getContact() {
        return contact;
    }

    public void setContact(Contact contact) {
        this.contact = contact;
    }

    public String getDate() { return this.start.toLocalDate().toString(); }

    public String getStartTime() { return this.start.toLocalTime().toString(); }

    public String getEndTime() { return this.end.toLocalTime().toString(); }

    public String toString(){
        return "ID: " + id + " " + customer + " " + start;
    }

    public boolean equals(Appointment appointment){
        return this.id == appointment.getId() &&
            this.title.equals(appointment.getTitle()) &&
            this.description.equals(appointment.getDescription()) &&
            this.location.equals(appointment.getLocation()) &&
                    this.type.equals(appointment.getType()) &&
                    this.start.equals(appointment.getStart()) &&
                    this.customer.getId() == appointment.getCustomer().getId() &&
                    this.user.getId() == appointment.getUser().getId() &&
                    this.contact.getId() == appointment.getContact().getId();

    }
}
