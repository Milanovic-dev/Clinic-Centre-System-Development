package dto;

import model.Drug;
import model.Nurse;
import model.Prescription;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class PrescriptionDTO {

    private String description;
    private Date validationDate;
    private List<String> drugs = new ArrayList<>();
    private String nurseEmail;
    private Boolean isValid;
    private long id;

    public PrescriptionDTO(){
        super();

    }

    public PrescriptionDTO(Prescription prescription)
    {
        super();
        this.description = prescription.getDescription();
        this.drugs = new ArrayList<>();
        for(Drug drug : prescription.getDrugs())
        {
            drugs.add(drug.getName());
        }
        this.isValid = false;
        this.id = prescription.getId();
    }

    public PrescriptionDTO(String description, Date validationDate,
                           List<String> drugs, String nurseEmail, Boolean isValid) {
        super();
        this.description = description;
        this.validationDate = validationDate;
        this.drugs = new ArrayList<>();
        for(String drug : drugs)
        {
            drugs.add(drug);
        }
        this.nurseEmail = nurseEmail;
        this.isValid = false;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getValidationDate() {
        return validationDate;
    }

    public void setValidationDate(Date validationDate) {
        this.validationDate = validationDate;
    }

    public List<String> getDrugs() {
        return drugs;
    }

    public void setDrugs(List<String> drugs) {
        this.drugs = drugs;
    }

    public String getNurseEmail() {
        return nurseEmail;
    }

    public void setNurseEmail(String nurseEmail) {
        this.nurseEmail = nurseEmail;
    }

    public Boolean getValid() {
        return isValid;
    }

    public void setValid(Boolean valid) {
        isValid = valid;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }
}
