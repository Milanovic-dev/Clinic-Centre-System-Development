package dto;

import java.util.Date;

public class VacationDTO 
{
	private String startDate;
	private String endDate;
	private String userEmail;
	
	
	public VacationDTO(String startDate, String endDate, String userEmail) {
		super();
		this.startDate = startDate;
		this.endDate = endDate;
		this.userEmail = userEmail;
	}

	public VacationDTO() {
		super();
		// TODO Auto-generated constructor stub
	}


	
	public String getStartDate() {
		return startDate;
	}

	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

	public String getEndDate() {
		return endDate;
	}

	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}

	public String getUserEmail() {
		return userEmail;
	}
	public void setUserEmail(String userEmail) {
		this.userEmail = userEmail;
	}
	
	
}
