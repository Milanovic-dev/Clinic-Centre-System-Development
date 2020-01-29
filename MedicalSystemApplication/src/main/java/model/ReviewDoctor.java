package model;

import javax.persistence.*;
import java.util.Date;

@Entity
public class ReviewDoctor {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "rating", nullable = false)
    private int rating;

    @Column(name = "date", nullable = false)
    private Date date;
    
    @ManyToOne(fetch = FetchType.LAZY)
    private Patient patient;

    public ReviewDoctor(int rating, Date date, Patient patient) {
        this.rating = rating;
        this.date = date;
        this.patient = patient;
    }

    public ReviewDoctor(){

    }

  
    public Patient getPatient() {
		return patient;
	}

	public void setPatient(Patient patient) {
		this.patient = patient;
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

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
