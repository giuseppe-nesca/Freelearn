package it.tweb.java.model;

import java.util.Date;

public class Lesson {
    enum Status {
        booked, cancelled;
    }

    public int getId() {
        return id;
    }

    public int getUserID() {
        return userID;
    }

    public int getCourseID() {
        return courseID;
    }

    public Date getDate() {
        return date;
    }

    public int getSlot() {
        return slot;
    }

    public Status getStatus() {
        return status;
    }

    public boolean isDone() {
        return done;
    }

    public Lesson(int id, int userID, int courseID, Date date, int slot, String status) {
        this.id = id;
        this.userID = userID;
        this.courseID = courseID;
        this.date = date;
        this.slot = slot;
        this.status = Status.valueOf(status);
        if (this.status == Status.booked && new Date().compareTo(this.date) >  0) {
            done = true;
        }
    }

    private int id;
    private int userID, courseID;
    private Date date;
    private int slot;
    private Status status;
    private boolean done = false;
}
