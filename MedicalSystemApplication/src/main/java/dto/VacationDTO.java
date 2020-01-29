package dto;

import java.util.Date;

import helpers.DateUtil;
import model.VacationRequest;

public class VacationDTO 
{
	private String startDate;
	private String endDate;
	private UserDTO user;
	private Long id;
	
	
	public VacationDTO(String startDate, String endDate,UserDTO userDTO,Long id) {
		super();
		this.startDate = startDate;
		this.endDate = endDate;
		this.user = userDTO;
		this.id = id;
	}
	public VacationDTO(VacationRequest vrq)
	{
		this.startDate = DateUtil.getInstance().getString(vrq.getStartDate(), "dd-MM-yyyy");
		this.endDate = DateUtil.getInstance().getString(vrq.getEndDate(), "dd-MM-yyyy");
		this.user = new UserDTO(vrq.getUser());
		this.id = vrq.getId();
		
	}
	
	public VacationDTO() {
		super();
		// TODO Auto-generated constructor stub
	}


	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
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
	public UserDTO getUser() {
		return user;
	}
	public void setUser(UserDTO user) {
		this.user = user;
	}


	
	
}
