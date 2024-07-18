package app.bank.dummy.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import java.io.Serial;
import java.io.Serializable;

/**
 * DTO for {@link app.bank.dummy.entities.Client}
 */
public record ClientDto(@NotNull Long id, @NotNull @NotEmpty @NotBlank String name, @NotNull @NotEmpty @NotBlank String login, @NotNull AccountDto account) implements Serializable {

  @Serial
  private static final long serialVersionUID = 2001903643095794630L;
}