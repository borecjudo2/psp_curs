package by.bsuir.psp.model.dto;

import java.io.Serializable;
import java.util.Date;
import java.util.UUID;

/**
 * DESCRIPTION
 *
 * @author Vladislav_Karpeka
 * @version 1.0.0
 */
public class PaymentDto implements Serializable {

  private UUID id;

  private Date receiveDate;

  private Long salary;

  private Long award;

  public PaymentDto() {
  }

  public PaymentDto(UUID id, Date receiveDate, Long salary, Long award) {
    this.id = id;
    this.receiveDate = receiveDate;
    this.salary = salary;
    this.award = award;
  }

  public PaymentDto(Date receiveDate, Long salary, Long award) {
    this.receiveDate = receiveDate;
    this.salary = salary;
    this.award = award;
  }

  public UUID getId() {
    return id;
  }

  public void setId(UUID id) {
    this.id = id;
  }

  public Date getReceiveDate() {
    return receiveDate;
  }

  public void setReceiveDate(Date receiveDate) {
    this.receiveDate = receiveDate;
  }

  public Long getSalary() {
    return salary;
  }

  public void setSalary(Long salary) {
    this.salary = salary;
  }

  public Long getAward() {
    return award;
  }

  public void setAward(Long award) {
    this.award = award;
  }
}
