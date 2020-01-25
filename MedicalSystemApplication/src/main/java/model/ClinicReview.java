package model;

import javax.persistence.*;
import java.util.Date;

@Entity
public class ClinicReview {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

    @Column(name = "rating", nullable = false)
    private int rating;

    @Column(name = "date", nullable = false)
    private Date date;
    
    @ManyToOne(fetch = FetchType.LAZY)
    private Patient patient;

    public ClinicReview(){

    }

        
    public ClinicReview(int rating, Date date, Patient patient) {
		super();
		this.rating = rating;
		this.date = date;
		this.patient = patient;
	}




	public Patient getPatient() {
		return patient;
	}

	public void setPatient(Patient patient) {
		this.patient = patient;
	}

	public Long getId()
    {
    	return id;
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

    public void setId(Long id) {
        this.id = id;
    }
}
