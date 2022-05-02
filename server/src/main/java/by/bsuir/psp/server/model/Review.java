package by.bsuir.psp.server.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.UUID;


@Entity
@Table
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

public class Review implements Serializable {
    @Id
    @GeneratedValue
    private UUID id;

    private String name;

    private String rew;

    private int star;

    private Date birthDate;

}
