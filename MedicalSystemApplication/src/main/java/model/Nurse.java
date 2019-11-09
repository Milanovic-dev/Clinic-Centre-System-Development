package model;

import java.util.ArrayList;

public class Nurse extends User{
    private String idInsurance;
    private ArrayList<Recipe> recipes;

    public Nurse() {
    }

    public Nurse(String idInsurance, ArrayList<Recipe> recipes) {
        this.idInsurance = idInsurance;
        this.recipes = recipes;
    }

    public Nurse(String username, String password, String email, String name, String surname, String city, String address, String state, String phone, UserRole role, String idInsurance, ArrayList<Recipe> recipes) {
        super(username, password, email, name, surname, city, address, state, phone, role);
        this.idInsurance = idInsurance;
        this.recipes = recipes;
    }

    public String getIdInsurance() {
        return idInsurance;
    }

    public void setIdInsurance(String idInsurance) {
        this.idInsurance = idInsurance;
    }

    public ArrayList<Recipe> getRecipes() {
        return recipes;
    }

    public void setRecipes(ArrayList<Recipe> recipes) {
        this.recipes = recipes;
    }
}
