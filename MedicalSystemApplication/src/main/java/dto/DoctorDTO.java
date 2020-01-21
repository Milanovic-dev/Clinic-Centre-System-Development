package dto;

import helpers.DateUtil;
import model.Doctor;
import model.User.UserRole;

public class DoctorDTO {

	private UserDTO user;
	private String type;
    private String shiftStart;
    private String shiftEnd;
    private String clinicName;
    private float avarageRating;
    
    public DoctorDTO()
    {
    	super();
    }
    
	public DoctorDTO(UserDTO user, String type, String insuranceId, String shiftStart, String shiftEnd,
			String clinicName, float avarageRating) {
		super();
		this.user = user;
		this.type = type;
		this.shiftStart = shiftStart;
		this.shiftEnd = shiftEnd;
		this.clinicName = clinicName;
		this.avarageRating = avarageRating;
	}
	
	public DoctorDTO(Doctor d)
	{
		UserDTO dto = new UserDTO();
		dto.setName(d.getName());
		dto.setSurname(d.getSurname());
		dto.setEmail(d.getEmail());
		dto.setAddress(d.getAddress());
		dto.setCity(d.getCity());
		dto.setPhone(d.getPhone());
		dto.setState(d.getState());
		dto.setRole(UserRole.Doctor);
		this.user = dto;
		
		if(d.getClinic() != null)
		{
			this.clinicName = d.getClinic().getName();			
		}
		else
		{
			this.clinicName = "N/A";
		}
		this.avarageRating = d.getAvarageRating();
		this.user.setInsuranceId(d.getInsuranceId());
		this.type = d.getType();
		this.shiftStart = DateUtil.getInstance().getString(d.getShiftStart(),"HH:mm");
		this.shiftEnd = DateUtil.getInstance().getString(d.getShiftEnd(),"HH:mm");
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

	public float getAvarageRating() {
		return avarageRating;
	}

	public void setAvarageRating(float avarageRating) {
		this.avarageRating = avarageRating;
	}
    
    
	
}
