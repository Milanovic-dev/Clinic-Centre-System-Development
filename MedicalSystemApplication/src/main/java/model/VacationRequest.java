package model;

import java.sql.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
public class VacationRequest
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
    @Column(name = "startDate", nullable = false)
	private Date startDate;
    
    @Column(name = "endDate", nullable = false)
	private Date endDate;
    
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", referencedColumnName = "id")
	private User vacationUser;

	public VacationRequest() {
		super();
		// TODO Auto-generated constructor stub
	}
	

	public VacationRequest(Date startDate, Date endDate, User vacationUser) {
		this.startDate = startDate;
		this.endDate = endDate;
		this.vacationUser = vacationUser;
	}


	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}


	public Date getStartDate() {
		return startDate;
	}


	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}


	public Date getEndDate() {
		return endDate;
	}


	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}


	public User getVacationUser() {
		return vacationUser;
	}


	public void setVacationUser(User vacationUser) {
		this.vacationUser = vacationUser;
	}
	
	
	

}
