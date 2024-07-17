package app.bank.dummy.dtos;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import java.io.Serial;
import java.io.Serializable;
import java.util.Map;
import java.util.UUID;

/**
 * DTO for {@link app.bank.dummy.models.Account}
 */
public record AccountDto(@NotNull UUID id, @NotNull CurrencyDto currency, @PositiveOrZero double balance, @NotNull Map<TransactionDto, Double> transactionHistoric) implements Serializable {

  @Serial
  private static final long serialVersionUID = -1409279032995393449L;

}