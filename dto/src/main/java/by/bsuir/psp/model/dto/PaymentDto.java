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

//  private static final long serialVersionUID = -4852520871181574459L;

  private UUID id;

  private Date receiveDate;

  private Long salary;

  private Long award;

  private OverratedTimeDto overratedTime;

  public PaymentDto() {
  }

  public PaymentDto(UUID id, Date receiveDate, Long salary, Long award, OverratedTimeDto overratedTime) {
    this.id = id;
    this.receiveDate = receiveDate;
    this.salary = salary;
    this.award = award;
    this.overratedTime = overratedTime;
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

  public OverratedTimeDto getOverratedTime() {
    return overratedTime;
  }

  public void setOverratedTime(OverratedTimeDto overratedTime) {
    this.overratedTime = overratedTime;
  }

  @Override
  public String toString() {
    return "PaymentDto{" +
        "id=" + id +
        ", receiveDate=" + receiveDate +
        ", salary=" + salary +
        ", award=" + award +
        ", overratedTime=" + overratedTime +
        '}';
  }
}
