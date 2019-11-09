package model;

import java.util.ArrayList;

public class Nurse extends User{
    private String InsuranceID;
    private ArrayList<Recipe> recipes;

    public Nurse() {
    }

    public Nurse(String InsuranceID, ArrayList<Recipe> recipes) {
        this.InsuranceID = InsuranceID;
        this.recipes = recipes;
    }

    public Nurse(String username, String password, String email, String name, String surname, String city, String address, String state, String phone, UserRole role, String InsuranceID, ArrayList<Recipe> recipes) {
        super(username, password, email, name, surname, city, address, state, phone, role);
        this.InsuranceID = InsuranceID;
        this.recipes = recipes;
    }

    public String getInsuranceID() {
        return InsuranceID;
    }

    public void setInsuranceID(String InsuranceID) {
        this.InsuranceID = InsuranceID;
    }

    public ArrayList<Recipe> getRecipes() {
        return recipes;
    }

    public void setRecipes(ArrayList<Recipe> recipes) {
        this.recipes = recipes;
    }
}
