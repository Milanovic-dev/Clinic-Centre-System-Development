package model;

import java.sql.Date;

public class Vacation 
{
	private Date startDate;
	private Date endDate;
	private User vacationUser;
	
	public Vacation() {
		super();
		// TODO Auto-generated constructor stub
	}
	public Vacation(Date startDate, Date endDate, User vacationUser) {
		super();
		this.startDate = startDate;
		this.endDate = endDate;
		this.vacationUser = vacationUser;
	}
	public User getVacationUser() {
		return vacationUser;
	}
	public void setVacationUser(User vacationUser) {
		this.vacationUser = vacationUser;
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

	
	
}
