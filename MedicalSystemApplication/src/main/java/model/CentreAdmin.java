package model;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;


@Entity
public class CentreAdmin extends User {

    @Column(name= "predefined", nullable = true)
    private boolean predefined = false;

    public CentreAdmin() {
        super();
    }

    public CentreAdmin(String username, String password, String email, String name, String surname, String city, String address, String state, String phone, ClinicCenter clinicCentre) {
        super(username, password, email, name, surname, city, address, state, phone, UserRole.CentreAdmin);
        this.setIsFirstLog(true);
    }

}
