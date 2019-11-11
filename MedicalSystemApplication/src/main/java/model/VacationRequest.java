package model;

import java.sql.Date;

public class VacationRequest
{
	private Long id;
	
	private Date startDate;
	private Date endDate;
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
