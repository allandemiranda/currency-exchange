package app.bank.dummy.dtos;

import jakarta.validation.constraints.PositiveOrZero;
import java.io.Serial;
import java.io.Serializable;

/**
 * DTO for {@link app.bank.dummy.entities.Account}
 */
public record NewAccountDto(String currencyCode, @PositiveOrZero(message = "{account.balance.validation.message}") double initialBalance) implements Serializable {

  @Serial
  private static final long serialVersionUID = -4852863749302361655L;
}