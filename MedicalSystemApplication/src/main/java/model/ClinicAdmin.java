package model;

import javax.persistence.*;

@Entity(name = "clinicAdmins")
@Table
public class ClinicAdmin extends User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @OneToMany(fetch = FetchType.LAZY)
    private Clinic clinic;


    //zahtevi za salu
    //zahtevi godisnjih

    public ClinicAdmin(){
    }

    public Clinic getClinic() {
        return clinic;
    }

    public void setClinic(Clinic clinic) {
        this.clinic = clinic;
    }

}
