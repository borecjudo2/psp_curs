package by.bsuir.psp.model.dto;


import java.io.Serializable;
import java.util.Date;
import java.util.UUID;


public class ReviewDto  implements Serializable{
    private UUID id;
    private String name;
    private String rew;
    private int star;
    private Date birthDate;

    public ReviewDto() {
    }

    public ReviewDto(UUID id, String name,String rew, int star, Date birthDate) {
        this.id = id;
        this.name = name;
        this.rew = rew;
        this.star = star;
        this.birthDate = birthDate;
    }


    public UUID getId() {
        return id;
    }
    public void setId(UUID id) {
        this.id = id;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getRew() {
        return rew;
    }
    public void setRew(String rew) {
        this.rew = rew;
    }

    public int getStar() {
        return star;
    }
    public void setStar(int star) {
        this.star = star;
    }

    public Date getBirthDate() {
        return birthDate;
    }
    public void setBirthDate(Date birthDate) {
        this.birthDate = birthDate;
    }

    @Override
    public String toString() {
        return name + " " + star;
    }
}


