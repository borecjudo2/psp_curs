package by.bsuir.psp.model.dto;

import java.io.Serializable;
import java.util.UUID;

public class InsuranceDto implements Serializable {
    private UUID id;

    private String name;

    private String info;


    public InsuranceDto() {
    }

    public InsuranceDto(UUID id, String name, String info) {
        this.id = id;
        this.name = name;
        this.info = info;
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

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    @Override
    public String toString() {
        return name;
    }

}
