package model;
import javax.persistence.*;


@Entity
public class Diagnosis {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name= "name",nullable = false)
    private String name;

    @Column(name= "tag",nullable = false)
    private String tag;

    @Column(name= "code",nullable = false)
    private String code;


    public Diagnosis(){

    }

    public Diagnosis(String code, String tag, String name) {
        super();
        this.name = name;
        this.tag = tag;
        this.code = code;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
