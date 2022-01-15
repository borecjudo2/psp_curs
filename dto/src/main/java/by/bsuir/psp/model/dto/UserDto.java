package by.bsuir.psp.model.dto;

import java.io.Serializable;
import java.util.List;
import java.util.UUID;

/**
 * DESCRIPTION
 *
 * @author Vladislav_Karpeka
 * @version 1.0.0
 */
public class UserDto implements Serializable {

  private UUID id;

  private UserRole role;

  private String login;

  private String name;

  private String password;

  private DepartmentDto department;

  private List<AwardDto> awards;

  public UserDto() {
  }

  public UserDto(UUID id, UserRole role, String login, String name, String password, DepartmentDto department, List<AwardDto> awards) {
    this.id = id;
    this.role = role;
    this.login = login;
    this.name = name;
    this.password = password;
    this.department = department;
    this.awards = awards;
  }

  public UUID getId() {
    return id;
  }

  public void setId(UUID id) {
    this.id = id;
  }

  public UserRole getRole() {
    return role;
  }

  public void setRole(UserRole role) {
    this.role = role;
  }

  public String getLogin() {
    return login;
  }

  public void setLogin(String login) {
    this.login = login;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public DepartmentDto getDepartment() {
    return department;
  }

  public void setDepartment(DepartmentDto department) {
    this.department = department;
  }

  public List<AwardDto> getAwards() {
    return awards;
  }

  public void setAwards(List<AwardDto> awards) {
    this.awards = awards;
  }
}
