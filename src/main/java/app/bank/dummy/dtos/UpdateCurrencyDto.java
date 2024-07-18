package app.bank.dummy.dtos;

import java.io.Serial;
import java.io.Serializable;

/**
 * DTO for {@link app.bank.dummy.entities.Currency}
 */
public record UpdateCurrencyDto(String name, String symbol) implements Serializable {

  @Serial
  private static final long serialVersionUID = -5237485339570325019L;
}