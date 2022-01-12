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
public class AwardDto implements Serializable {

  private UUID id;

  private Date receiveDate;

  private Long amount;

  public AwardDto() {
  }

  public AwardDto(UUID id, Date receiveDate, Long amount) {
    this.id = id;
    this.receiveDate = receiveDate;
    this.amount = amount;
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

  public Long getAmount() {
    return amount;
  }

  public void setAmount(Long amount) {
    this.amount = amount;
  }
}
