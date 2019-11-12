package model;

import java.util.ArrayList;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
@Entity
public class CentreAdmin extends User {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	@OneToMany(fetch = FetchType.LAZY)
	@JoinColumn(name = "registrationRequest_id")
    private ArrayList<RegistrationRequest> registrationRequests;

    public CentreAdmin() {
    }

    public CentreAdmin(String username, String password, String email, String name, String surname, String city, String address, String state, String phone, ClinicCenter clinicCentre) {
        super(username, password, email, name, surname, city, address, state, phone, UserRole.CentreAdmin);
        this.registrationRequests = new ArrayList<RegistrationRequest>();
    }

    public ArrayList<RegistrationRequest> getRegistrationRequests() {
        return registrationRequests;
    }

    public void setRegistrationRequests(ArrayList<RegistrationRequest> registrationRequests) {
        this.registrationRequests = registrationRequests;
    }

}
