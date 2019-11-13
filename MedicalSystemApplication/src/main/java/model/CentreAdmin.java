package model;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;


@Entity
public class CentreAdmin extends User {

    @OneToMany(fetch = FetchType.LAZY)
    private List<RegistrationRequest> registrationRequests;

    public CentreAdmin() {
    }

    public CentreAdmin(String username, String password, String email, String name, String surname, String city, String address, String state, String phone, ClinicCenter clinicCentre) {
        super(username, password, email, name, surname, city, address, state, phone, UserRole.CentreAdmin);
        this.registrationRequests = new ArrayList<RegistrationRequest>();
    }

    public List<RegistrationRequest> getRegistrationRequests() {
        return registrationRequests;
    }

    public void setRegistrationRequests(List<RegistrationRequest> registrationRequests) {
        this.registrationRequests = registrationRequests;
    }

}
