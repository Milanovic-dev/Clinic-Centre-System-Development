package model;

import java.util.Date;

public class ClinicReview {
	
	private Long id;
    private String header;
    private String description;
    private int rating;
    private Date date;

    public ClinicReview(){

    }

    public ClinicReview(String header, String description, int rating, Date date) {
        this.header = header;
        this.description = description;
        this.rating = rating;
        this.date = date;
    }
    
    public Long getId()
    {
    	return id;
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

}
