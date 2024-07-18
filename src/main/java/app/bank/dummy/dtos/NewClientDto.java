package app.bank.dummy.dtos;

import java.io.Serial;
import java.io.Serializable;

/**
 * DTO for {@link app.bank.dummy.entities.Client}
 */
public record NewClientDto(String infoFirstName, String infoLastName, String credentialsLogin, String credentialsPassword) implements Serializable {

  @Serial
  private static final long serialVersionUID = 4529695744952952529L;
}