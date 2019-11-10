package model;

import java.util.ArrayList;

public class ClinicReport {
    private float averageRate;
    private ArrayList<Doctor> doctors;
    private float income;

    public ClinicReport(float averageRate, ArrayList<Doctor> doctors, float income) {
        this.averageRate = averageRate;
        this.doctors = doctors;
        this.income = income;
    }

    public ClinicReport(){

    }

    public float getAverageRate() {
        return averageRate;
    }

    public void setAverageRate(float averageRate) {
        this.averageRate = averageRate;
    }

    public ArrayList<Doctor> getDoctors() {
        return doctors;
    }

    public void setDoctors(ArrayList<Doctor> doctors) {
        this.doctors = doctors;
    }

    public float getIncome() {
        return income;
    }

    public void setIncome(float income) {
        this.income = income;
    }
}
