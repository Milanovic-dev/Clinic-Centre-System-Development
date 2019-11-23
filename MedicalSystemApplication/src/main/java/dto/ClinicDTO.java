package dto;

import model.Clinic;

public class ClinicDTO {

    private String name;
    private String address;
    private String city;
    private String state;
    private String description;
    private float rating;

    public ClinicDTO(String name, String address, String city, String state, String description,float rating) {
        this.name = name;
        this.address = address;
        this.city = city;
        this.state = state;
        this.description = description;
        this.rating = rating;
    }
    
    public ClinicDTO(Clinic clinic)
    {
    	this.name = clinic.getName();
        this.address = clinic.getAddress();
        this.city = clinic.getCity();
        this.state = clinic.getState();
        this.description = clinic.getDescription();
        
        this.rating = clinic.calculateRating();
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
}
