package by.bsuir.psp.model.dto;

import java.io.Serializable;
import java.util.UUID;

/**
 * DESCRIPTION
 *
 * @author Vladislav_Karpeka
 * @version 1.0.0
 */
public class OverratedTimeDto implements Serializable {

//  private static final long serialVersionUID = 1501966901716303660L;

  private UUID id;

  private Integer hourValue;

  public OverratedTimeDto() {
  }

  public OverratedTimeDto(UUID id, Integer hourValue) {
    this.id = id;
    this.hourValue = hourValue;
  }

  public UUID getId() {
    return id;
  }

  public void setId(UUID id) {
    this.id = id;
  }

  public Integer getHourValue() {
    return hourValue;
  }

  public void setHourValue(Integer hourValue) {
    this.hourValue = hourValue;
  }

  @Override
  public String toString() {
    return String.valueOf(hourValue);
  }
}
