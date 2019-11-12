package model;

public class ClinicAdmin extends User {

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
