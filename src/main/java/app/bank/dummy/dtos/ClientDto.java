package app.bank.dummy.dtos;

import java.io.Serial;
import java.io.Serializable;

/**
 * DTO for {@link app.bank.dummy.entities.Client}
 */
public record ClientDto(Long id, String firstName, String lastName, String login) implements Serializable {

  @Serial
  private static final long serialVersionUID = -8034144794145795627L;
}