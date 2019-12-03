package dto;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;

import model.Clinic;
import model.Doctor;
import model.User.UserRole;

public class DoctorDTO {

	private UserDTO user;
	private String type;	
	private String insuranceId;
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
		this.insuranceId = insuranceId;
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
		dto.setUsername(d.getUsername());
		this.user = dto;
		
		this.clinicName = d.getClinic().getName();
		this.avarageRating = d.getAvarageRating();
		this.insuranceId = d.getInsuranceId();
		this.shiftStart = d.getShiftStart();
		this.shiftEnd = d.getShiftEnd();
		this.type = d.getType();
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

	public String getInsuranceId() {
		return insuranceId;
	}

	public void setInsuranceId(String insuranceId) {
		this.insuranceId = insuranceId;
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
