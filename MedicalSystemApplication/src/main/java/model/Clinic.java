package model;

import java.util.ArrayList;

public class Clinic {
    private String name;
    private long id;
    private String address;
    private String description;
    private ArrayList<Appointment> freeAppointments;
    private ArrayList<Doctor> doctors;
    private ArrayList<Hall> halls;
    private ClinicAdmin clinicAdmin;
    //cenovnik
    private float averageRate;
    //prihodi

    public Clinic(){

    }

    public Clinic(String name, long id, String address, String description, float averageRate, ClinicAdmin clinicAdmin) {
        this.name = name;
        this.id = id;
        this.address = address;
        this.description = description;
        this.freeAppointments = new ArrayList<Appointment>();
        this.doctors = new ArrayList<Doctor>();
        this.halls = new ArrayList<Hall>();
        this.averageRate = averageRate;
        this.clinicAdmin = clinicAdmin;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public ArrayList<Appointment> getFreeAppointments() {
        return freeAppointments;
    }

    public void setFreeAppointments(ArrayList<Appointment> freeAppointments) {
        this.freeAppointments = freeAppointments;
    }

    public ArrayList<Doctor> getDoctors() {
        return doctors;
    }

    public void setDoctors(ArrayList<Doctor> doctors) {
        this.doctors = doctors;
    }

    public ArrayList<Hall> getHalls() {
        return halls;
    }

    public void setHalls(ArrayList<Hall> halls) {
        this.halls = halls;
    }

    public float getAverageRate() {
        return averageRate;
    }

    public void setAverageRate(float averageRate) {
        this.averageRate = averageRate;
    }

    public ClinicAdmin getClinicAdmin() {
        return clinicAdmin;
    }

    public void setClinicAdmin(ClinicAdmin clinicAdmin) {
        this.clinicAdmin = clinicAdmin;
    }
}
