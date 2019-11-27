package dto;

import model.Diagnosis;

public class DiagnosisDTO {

    private String name;
    private String code;
    private String tag;

    public DiagnosisDTO(String code, String tag, String name) {
        this.name = name;
        this.code = code;
        this.tag = tag;
    }

    public DiagnosisDTO(Diagnosis diagnosis)
    {
        this.name = diagnosis.getName();
        this.code = diagnosis.getCode();
        this.tag = diagnosis.getTag();
    }

    public String getName() {
        return name;
    }

    public String getCode() {
        return code;
    }

    public String getTag(){
        return tag;
    }
}
