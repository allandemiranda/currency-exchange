package app.bank.dummy.dtos;

import java.io.Serial;
import java.io.Serializable;

/**
 * DTO for {@link app.bank.dummy.entities.Client}
 */
public record UpdateClientDto(String firstName, String lastName) implements Serializable {
  @Serial
  private static final long serialVersionUID = 8269726892091350890L;
}