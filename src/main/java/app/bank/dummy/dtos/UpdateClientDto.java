package app.bank.dummy.dtos;

import java.io.Serial;
import java.io.Serializable;

/**
 * DTO for {@link app.bank.dummy.entities.Client}
 */
public record UpdateClientDto(String infoFirstName, String infoLastName) implements Serializable {
  @Serial
  private static final long serialVersionUID = 8269726892091350890L;
}