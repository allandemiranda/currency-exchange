package app.bank.dummy.dtos;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import java.io.Serial;
import java.io.Serializable;

public record OpenTransactionDto(@NotNull Long debitClientId, @NotNull Long creditClientId, @Positive double amount) implements Serializable {

  @Serial
  private static final long serialVersionUID = -1757582380673809539L;
}
