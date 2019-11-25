package dto;

import model.Drug;

public class DrugDTO {

    private String name;
    private String code;

    public DrugDTO(String name, String code) {
        this.name = name;
        this.code = code;
    }

    public DrugDTO(Drug drug)
    {
        this.name = drug.getName();
        this.code = drug.getCode();
    }

    public String getName() {
        return name;
    }

    public String getCode() {
        return code;
    }
}
