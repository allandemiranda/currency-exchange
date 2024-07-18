package app.bank.dummy.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.io.Serial;
import java.io.Serializable;

/**
 * DTO for {@link app.bank.dummy.entities.Currency}
 */
public record NewCurrencyDto(@NotNull @Size(message = "{currency.code.validation.message}", min = 3, max = 3) String code, @NotNull @NotEmpty @NotBlank String name,
                             @NotNull @NotEmpty @NotBlank String symbol) implements Serializable {

  @Serial
  private static final long serialVersionUID = -625118658380119447L;
}