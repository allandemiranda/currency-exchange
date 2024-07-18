package app.bank.dummy.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;
import java.io.Serial;
import java.io.Serializable;

/**
 * DTO for {@link app.bank.dummy.entities.Client}
 */
public record NewClientDto(@NotNull @NotEmpty @NotBlank String name, @NotNull @NotEmpty @NotBlank String login, @NotNull @Size(min = 5) String password,
                           @NotNull @NotEmpty @NotBlank @Size(min = 3, max = 3) String accountCurrencyCode, @PositiveOrZero double accountInitialBalance) implements Serializable {

  @Serial
  private static final long serialVersionUID = 2792670703876215032L;
}