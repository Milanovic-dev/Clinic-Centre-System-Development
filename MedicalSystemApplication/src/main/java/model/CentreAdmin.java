package model;


import java.util.ArrayList;

public class CentreAdmin extends User {
    private ArrayList<RegistrationRequest> registrationRequests;
    ClinicCenter clinicCentre;


    public CentreAdmin() {
    }

    public CentreAdmin(String username, String password, String email, String name, String surname, String city, String address, String state, String phone, ClinicCenter clinicCentre) {
        super(username, password, email, name, surname, city, address, state, phone, UserRole.CentreAdmin);
        this.registrationRequests = new ArrayList<RegistrationRequest>();
        this.clinicCentre = clinicCentre;
    }

    public ArrayList<RegistrationRequest> getRegistrationRequests() {
        return registrationRequests;
    }

    public void setRegistrationRequests(ArrayList<RegistrationRequest> registrationRequests) {
        this.registrationRequests = registrationRequests;
    }

    public ClinicCenter getClinicCentre() {
        return clinicCentre;
    }

    public void setClinicCentre(ClinicCenter clinicCentre) {
        this.clinicCentre = clinicCentre;
    }
}
