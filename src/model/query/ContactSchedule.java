package model.query;

import java.time.LocalDateTime;

public class ContactSchedule {

    private String contactName;
    private int appointmentId;
    private String title;
    private String desc;
    private LocalDateTime start;
    private LocalDateTime end;
    private int customerId;

    public ContactSchedule(String contactName, int appointmentId, String title, String desc, LocalDateTime start, LocalDateTime end, int customerId) {
        this.contactName = contactName;
        this.appointmentId = appointmentId;
        this.title = title;
        this.desc = desc;
        this.start = start;
        this.end = end;
        this.customerId = customerId;
    }

    public String getContactName() {
        return contactName;
    }

    public void setContactName(String contactName) {
        this.contactName = contactName;
    }

    public int getAppointmentId() {
        return appointmentId;
    }

    public void setAppointmentId(int appointmentId) {
        this.appointmentId = appointmentId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
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

    public int getCustomerId() {
        return customerId;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }
}
