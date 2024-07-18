package app.bank.dummy.dtos;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Positive;
import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.UUID;

/**
 * DTO for {@link app.bank.dummy.entities.Transaction}
 */
public record TransactionDto(@NotNull UUID id, @NotNull @PastOrPresent LocalDateTime dataTime, @Positive(message = "{transaction.amount.validation.message}") double amount, double taxRate, double debitTmpBalance, double creditTmpBalance) implements
    Serializable {
  @Serial
  private static final long serialVersionUID = -6652091499004403908L;
  }