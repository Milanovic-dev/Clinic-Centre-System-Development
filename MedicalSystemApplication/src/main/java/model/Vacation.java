package model;

import java.sql.Date;

public class Vacation 
{
	private Long id;
	private Date startDate;
	private Date endDate;
	
	public Vacation() {
		super();
		// TODO Auto-generated constructor stub
	}
	public Vacation(Date startDate, Date endDate, User vacationUser) {
		super();
		this.startDate = startDate;
		this.endDate = endDate;
	}
	
	public Vacation(VacationRequest request)
	{
		this.startDate = request.getStartDate();
		this.endDate = request.getEndDate();
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

	
	
}
