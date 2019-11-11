package model;

import java.util.ArrayList;

public class Nurse extends User{
    private String insuranceID;
    private ArrayList<Recipe> recipes;

    public Nurse() {
    }

    public Nurse(String insuranceID) {
        this.insuranceID = insuranceID;
        this.recipes = new ArrayList<Recipe>();
    }

    public Nurse(String username, String password, String email, String name, String surname, String city, String address, String state, String phone, String insuranceID) {
        super(username, password, email, name, surname, city, address, state, phone, UserRole.Nurse);
        this.insuranceID = insuranceID;
        this.recipes = new ArrayList<Recipe>();
    }

    public String getInsuranceID() {
        return insuranceID;
    }

    public void setInsuranceID(String InsuranceID) {
        this.insuranceID = insuranceID;
    }

    public ArrayList<Recipe> getRecipes() {
        return recipes;
    }

    public void setRecipes(ArrayList<Recipe> recipes) {
        this.recipes = recipes;
    }
}
