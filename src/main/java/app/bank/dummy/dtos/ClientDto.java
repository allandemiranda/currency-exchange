package app.bank.dummy.dtos;

import jakarta.validation.constraints.NotNull;
import java.io.Serial;
import java.io.Serializable;

/**
 * DTO for {@link app.bank.dummy.entities.Client}
 */
public record ClientDto(@NotNull Long id, String infoFirstName, String infoLastName, String credentialsLogin) implements Serializable {
  @Serial
  private static final long serialVersionUID = -8034144794145795627L;
  }