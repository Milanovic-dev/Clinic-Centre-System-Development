package model;

import java.util.Date;

public class DoctorReview {
    private String header;
    private String description;
    private int rating;
    private Date date;
    private String doctorID;

    public DoctorReview(String header, String description, int rating, Date date, String doctorID) {
        this.header = header;
        this.description = description;
        this.rating = rating;
        this.date = date;
        this.doctorID = doctorID;
    }

    public DoctorReview(){

    }

    public String getHeader() {
        return header;
    }

    public void setHeader(String header) {
        this.header = header;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getDoctorID() {
        return doctorID;
    }

    public void setDoctorID(String doctorID) {
        this.doctorID = doctorID;
    }
}
