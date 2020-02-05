package model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
public class VacationRequest
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
    @Column(name = "startDate", nullable = false)
	private Date date;
    
    @Column(name = "endDate", nullable = false)
	private Date end;
    
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", referencedColumnName = "id")
	private User user;
    
    @ManyToOne(fetch = FetchType.EAGER)
    private Clinic clinic;

	public VacationRequest() {
		super();
		// TODO Auto-generated constructor stub
	}
	

	public VacationRequest(Date startDate, Date endDate,Clinic clinic, User vacationUser) {
		this.date = startDate;
		this.end = endDate;
		this.user = vacationUser;
		this.clinic = clinic;
	}

	

	public Clinic getClinic() {
		return clinic;
	}


	public void setClinic(Clinic clinic) {
		this.clinic = clinic;
	}


	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}


	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public Date getEnd() {
		return end;
	}

	public void setEnd(Date end) {
		this.end = end;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User vacationUser) {
		this.user = vacationUser;
	}

}
