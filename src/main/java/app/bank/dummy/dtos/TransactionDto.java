package app.bank.dummy.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.PositiveOrZero;
import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.UUID;

/**
 * DTO for {@link app.bank.dummy.models.Transaction}
 */
public record TransactionDto(@NotNull UUID id, @NotNull @PastOrPresent LocalDateTime dataTime, @NotNull UUID debitAccountId, @NotNull @NotBlank @NotEmpty String debitAccountCurrencyCode, @PositiveOrZero double debitAccountBalance,
                             @NotNull UUID creditAccountId, @NotNull @NotBlank @NotEmpty String creditAccountCurrencyCode, @PositiveOrZero double creditAccountBalance) implements Serializable {

  @Serial
  private static final long serialVersionUID = 3968489493926344390L;
}