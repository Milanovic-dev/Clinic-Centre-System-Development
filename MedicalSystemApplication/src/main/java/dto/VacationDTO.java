package dto;

import java.util.Date;

import helpers.DateUtil;
import model.Vacation;
import model.VacationRequest;

public class VacationDTO
{
	private String startDate;
	private String endDate;
	private UserDTO user;
	private Long id;
	private int version;

	public VacationDTO(String date, String end,UserDTO userDTO,Long id) {
		super();
		this.startDate = date;
		this.endDate = end;
		this.user = userDTO;
		this.id = id;
	}
	public VacationDTO(VacationRequest vrq)
	{
		this.startDate = DateUtil.getInstance().getString(vrq.getStartDate(), "dd-MM-yyyy");
		this.endDate = DateUtil.getInstance().getString(vrq.getEndDate(), "dd-MM-yyyy");
		this.user = new UserDTO(vrq.getUser());
		this.id = vrq.getId();
		this.version = vrq.getVersion();
	}

	public VacationDTO(Vacation vac)
	{
		this.startDate = DateUtil.getInstance().getString(vac.getStartDate(), "dd-MM-yyyy HH:mm");
		this.endDate = DateUtil.getInstance().getString(vac.getEndDate(), "dd-MM-yyyy HH:mm");
		this.user = new UserDTO(vac.getUser());
	}


	public VacationDTO() {
		super();
		// TODO Auto-generated constructor stub
	}

	

	public int getVersion() {
		return version;
	}
	public void setVersion(int version) {
		this.version = version;
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

	public void setStartDate(String date) {
		this.startDate = date;
	}

	public String getEndDate() {
		return endDate;
	}

	public void setEndDate(String end) {
		this.endDate = end;
	}
	public UserDTO getUser() {
		return user;
	}
	public void setUser(UserDTO user) {
		this.user = user;
	}




}
