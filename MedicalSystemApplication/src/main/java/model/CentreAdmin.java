package model;

import java.util.ArrayList;

public class CentreAdmin extends User {
    private ArrayList<RegistrationRequest> registrationRequests;


    public CentreAdmin() {
    }

    public CentreAdmin(String username, String password, String email, String name, String surname, String city, String address, String state, String phone, UserRole role, ArrayList<RegistrationRequest> registrationRequests) {
        super(username, password, email, name, surname, city, address, state, phone, role);
        this.registrationRequests = registrationRequests;
    }

    public ArrayList<RegistrationRequest> getRegistrationRequests() {
        return registrationRequests;
    }

    public void setRegistrationRequests(ArrayList<RegistrationRequest> registrationRequests) {
        this.registrationRequests = registrationRequests;
    }
}
