package model;

import java.util.ArrayList;

public class Clinic 
{
	  private String name;
	  private String address;
	  private String city;
	  private String state;
	  private String description;
	  private Long id;
	
    private ArrayList<Hall> halls;
    private ArrayList<ClinicAdmin> clinicAdmins;
    private ArrayList<Doctor> doctors;
    private ArrayList<Appointment> appointments;
    private ArrayList<ClinicReview> reviews;
	
	
	
    public Clinic() {
      super();
      // TODO Auto-generated constructor stub
    }

    public Clinic(String name, String address, String city, String state, String description, Long id) {
      super();
      this.name = name;
      this.address = address;
      this.city = city;
      this.state = state;
      this.description = description;
      this.id = id;

      halls = new ArrayList<Hall>();
      clinicAdmins = new ArrayList<ClinicAdmin>();
      doctors = new ArrayList<Doctor>();
      appointments = new ArrayList<Appointment>();
      reviews = new ArrayList<ClinicReview>();
    }

    public String getName() {
      return name;
    }
    public void setName(String name) {
      this.name = name;
    }
    public String getAddress() {
      return address;
    }
    public void setAddress(String address) {
      this.address = address;
    }
    public String getCity() {
      return city;
    }
    public void setCity(String city) {
      this.city = city;
    }
    public String getState() {
      return state;
    }
    public void setState(String state) {
      this.state = state;
    }
    public String getDescription() {
      return description;
    }
    public void setDescription(String description) {
      this.description = description;
    }
    public Long getId() {
      return id;
    }
    public void setId(Long id) {
      this.id = id;
    }
    public ArrayList<Hall> getHalls() {
      return halls;
    }
    public void setHalls(ArrayList<Hall> halls) {
      this.halls = halls;
    }
    public ArrayList<ClinicAdmin> getClinicAdmins() {
      return clinicAdmins;
    }
    public void setClinicAdmins(ArrayList<ClinicAdmin> clinicAdmins) {
      this.clinicAdmins = clinicAdmins;
    }
    public ArrayList<Doctor> getDoctors() {
      return doctors;
    }
    public void setDoctors(ArrayList<Doctor> doctors) {
      this.doctors = doctors;
    }
    public ArrayList<Appointment> getAppointments() {
      return appointments;
    }
    public void setAppointments(ArrayList<Appointment> appointments) {
      this.appointments = appointments;
    }
    public ArrayList<ClinicReview> getReviews() {
      return reviews;
    }
    public void setReviews(ArrayList<ClinicReview> reviews) {
      this.reviews = reviews;
    }
	
}
