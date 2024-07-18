package app.bank.dummy.dtos;

import java.io.Serial;
import java.io.Serializable;

/**
 * DTO for {@link app.bank.dummy.entities.Currency}
 */
public record CurrencyDto(String code, String name, String symbol) implements Serializable {

  @Serial
  private static final long serialVersionUID = -7595173277031226055L;
}