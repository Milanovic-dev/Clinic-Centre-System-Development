package model;

public class RegistrationRequest extends User{
    private boolean approved = false;

    public RegistrationRequest() {
    	super();
    }

    public RegistrationRequest(String username, String password, String email, String name, String surname, String city, String address, String state, String phone, UserRole role, boolean approved) {
        super(username, password, email, name, surname, city, address, state, phone, role);
        this.approved = approved;
    }

    public boolean isApproved() {
        return approved;
    }

    public void setApproved(boolean approved) {
        this.approved = approved;
    }

}
