package app.bank.dummy.dtos;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.UUID;

/**
 * DTO for {@link app.bank.dummy.entities.Transaction}
 */
public record TransactionDto(@NotNull UUID id, @NotNull @PastOrPresent LocalDateTime dataTime, @Positive double amount, @Positive double taxRate, UUID debitAccountId,
                             String debitAccountCurrencyCode, @PositiveOrZero double debitTmpBalance, UUID creditAccountId, String creditAccountCurrencyCode,
                             @PositiveOrZero double creditTmpBalance) implements Serializable {

  @Serial
  private static final long serialVersionUID = -7034915829762841049L;

}