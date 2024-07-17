package app.bank.dummy.dtos;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import java.io.Serial;
import java.io.Serializable;

/**
 * DTO for {@link app.bank.dummy.models.Transaction}
 */
public record OpenTransactionDto(@NotNull Long debitUserId, @NotNull Long creditUserId, @Positive double amount) implements Serializable {

  @Serial
  private static final long serialVersionUID = 8861568482531098528L;
}