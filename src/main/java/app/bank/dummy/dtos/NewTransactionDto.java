package app.bank.dummy.dtos;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import java.io.Serial;
import java.io.Serializable;
import java.util.UUID;

/**
 * DTO for {@link app.bank.dummy.entities.Transaction}
 */
public record NewTransactionDto(@Positive(message = "{transaction.amount.validation.message}") double amount, @NotNull UUID creditAccountId) implements Serializable {

  @Serial
  private static final long serialVersionUID = 5356485697242473120L;
}