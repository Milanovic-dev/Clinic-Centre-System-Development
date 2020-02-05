package dto;

import helpers.DateUtil;
import model.Nurse;
import model.User;

public class NurseDTO {

	private UserDTO user;
	private String type;
	private String shiftStart;
	private String shiftEnd;
	private String clinicName;

	
	public NurseDTO()
	{
		super();
	}

	public NurseDTO(UserDTO user, String type, String shiftStart, String shiftEnd, String clinicName) {
		this.user = user;
		this.type = type;
		this.shiftStart = shiftStart;
		this.shiftEnd = shiftEnd;
		this.clinicName = clinicName;
	}

	public NurseDTO(Nurse n)
	{
		UserDTO dto = new UserDTO();
		dto.setName(n.getName());
		dto.setSurname(n.getSurname());
		dto.setEmail(n.getEmail());
		dto.setAddress(n.getAddress());
		dto.setCity(n.getCity());
		dto.setPhone(n.getPhone());
		dto.setState(n.getState());
		dto.setRole(User.UserRole.Nurse);
		this.user = dto;

		if(n.getClinic() != null)
		{
			this.clinicName = n.getClinic().getName();
		}
		else
		{
			this.clinicName = "N/A";
		}
		this.user.setInsuranceId(n.getInsuranceId());
		this.type = n.getType();
		this.shiftStart = DateUtil.getInstance().getString(n.getShiftStart(),"HH:mm");
		this.shiftEnd = DateUtil.getInstance().getString(n.getShiftEnd(),"HH:mm");
	}

	public UserDTO getUser() {
		return user;
	}

	public void setUser(UserDTO user) {
		this.user = user;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getShiftStart() {
		return shiftStart;
	}

	public void setShiftStart(String shiftStart) {
		this.shiftStart = shiftStart;
	}

	public String getShiftEnd() {
		return shiftEnd;
	}

	public void setShiftEnd(String shiftEnd) {
		this.shiftEnd = shiftEnd;
	}

	public String getClinicName() {
		return clinicName;
	}

	public void setClinicName(String clinicName) {
		this.clinicName = clinicName;
	}
}
