package model;

import java.sql.Timestamp;
import java.time.format.DateTimeFormatter;

public class Appointment {
    private int id;
    private String title;
    private String description;
    private String location;
    private String type;
    private DateTimeFormatter start;
    private DateTimeFormatter end;
    private DateTimeFormatter createDate;
    private String createdBy;
    private Timestamp lastUpdate;
    private String lastUpdatedBy;
    private Customer customer;
    private User user;
    private Contact contact;

    public Appointment(int id, String title, String description, String location, String type, DateTimeFormatter start,
                       DateTimeFormatter end, DateTimeFormatter createDate, String createdBy, Timestamp lastUpdate,
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

    public DateTimeFormatter getStart() {
        return start;
    }

    public void setStart(DateTimeFormatter start) {
        this.start = start;
    }

    public DateTimeFormatter getEnd() {
        return end;
    }

    public void setEnd(DateTimeFormatter end) {
        this.end = end;
    }

    public DateTimeFormatter getCreateDate() {
        return createDate;
    }

    public void setCreateDate(DateTimeFormatter createDate) {
        this.createDate = createDate;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public Timestamp getLastUpdate() {
        return lastUpdate;
    }

    public void setLastUpdate(Timestamp lastUpdate) {
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
}
