package by.bsuir.psp.server.model;

import by.bsuir.psp.model.dto.ReviewDto;
import by.bsuir.psp.model.dto.UserRole;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import javax.persistence.*;


@Entity
@Table
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class User implements Serializable {

  @Id
  @GeneratedValue
  private UUID id;

  // TODO: 29.04.2022 USER 2 insur one2one, userdto = user pol9
  @Enumerated(EnumType.STRING)
  private UserRole role;

  private String login;

  private String name;

  private String password;

  @OneToOne(cascade = CascadeType.ALL)
  @JoinColumn
  private Insurance insurance;

  @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
  @JoinColumn
  private List<Review> reviewDto = new ArrayList<>();
}
