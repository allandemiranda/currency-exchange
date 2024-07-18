package app.bank.dummy.dtos;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import java.io.Serial;
import java.io.Serializable;
import java.util.UUID;

/**
 * DTO for {@link app.bank.dummy.entities.Account}
 */
public record AccountDto(@NotNull UUID id, @NotNull CurrencyDto currency, @PositiveOrZero double balance) implements Serializable {

  @Serial
  private static final long serialVersionUID = 7362068803598733177L;
}