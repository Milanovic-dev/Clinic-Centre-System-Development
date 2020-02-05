package dto;

import java.util.Date;

import helpers.DateUtil;
import model.Vacation;
import model.VacationRequest;

public class VacationDTO
{
	private String date;
	private String end;
	private UserDTO user;
	private Long id;


	public VacationDTO(String date, String end,UserDTO userDTO,Long id) {
		super();
		this.date = date;
		this.end = end;
		this.user = userDTO;
		this.id = id;
	}
	public VacationDTO(VacationRequest vrq)
	{
		this.date = DateUtil.getInstance().getString(vrq.getDate(), "dd-MM-yyyy");
		this.end = DateUtil.getInstance().getString(vrq.getEnd(), "dd-MM-yyyy");
		this.user = new UserDTO(vrq.getUser());
		this.id = vrq.getId();

	}

	public VacationDTO(Vacation vac)
	{
		this.date = DateUtil.getInstance().getString(vac.getStartDate(), "dd-MM-yyyy HH:mm");
		this.end = DateUtil.getInstance().getString(vac.getEndDate(), "dd-MM-yyyy HH:mm");
		this.user = new UserDTO(vac.getUser());
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
	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getEnd() {
		return end;
	}

	public void setEnd(String end) {
		this.end = end;
	}
	public UserDTO getUser() {
		return user;
	}
	public void setUser(UserDTO user) {
		this.user = user;
	}




}
