package app.bank.dummy.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.io.Serial;
import java.io.Serializable;

/**
 * DTO for {@link app.bank.dummy.models.Currency}
 */
public record NewCurrencyDto(@NotNull @Size(min = 3, max = 3) String code, @NotNull @NotEmpty @NotBlank String name, @NotNull @NotEmpty @NotBlank String symbol) implements Serializable {

  @Serial
  private static final long serialVersionUID = 5612803019832550600L;
}