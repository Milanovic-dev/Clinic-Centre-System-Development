package model;

import java.sql.Date;

public class VacationRequest extends Vacation
{
	private Long id;
	

	public VacationRequest() {
		super();
		// TODO Auto-generated constructor stub
	}
	

	public VacationRequest(Date startDate, Date endDate, User vacationUser, Long id) {
		super(startDate, endDate, vacationUser);
		this.id = id;
	}


	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	

}
