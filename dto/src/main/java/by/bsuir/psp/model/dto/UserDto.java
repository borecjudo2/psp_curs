package by.bsuir.psp.model.dto;

import java.io.Serializable;
import java.util.List;
import java.util.UUID;

public class UserDto implements Serializable {

  private UUID id;

  private UserRole role;

  private String login;

  private String name;

  private String password;

  private InsuranceDto insurance;

  private List<ReviewDto> reviewDto;

  private Integer middleRate;

  public UserDto() {
  }

  public UserDto(UUID id, UserRole role, String login, String name, String password, InsuranceDto insurance,
      List<ReviewDto> reviewDto, Integer middleRate) {
    this.id = id;
    this.role = role;
    this.login = login;
    this.name = name;
    this.password = password;
    this.insurance = insurance;
    this.reviewDto = reviewDto;
    this.middleRate = getMiddleRate();
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

  public InsuranceDto getInsurance() {
    return insurance;
  }

  public void setInsurance(InsuranceDto insurance) {
    this.insurance = insurance;
  }

  public List<ReviewDto> getReviewDto() {
    return reviewDto;
  }

  public void setReviewDto(List<ReviewDto> reviewDto) {
    this.reviewDto = reviewDto;
  }

  public Integer getMiddleRate() {
    return reviewDto.stream().mapToInt(ReviewDto::getStar).sum();
  }

  public void setMiddleRate(Integer middleRate) {
    this.middleRate = middleRate;
  }

  @Override
  public String toString() {
    return name;
  }
}
