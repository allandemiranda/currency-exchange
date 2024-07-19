package app.bank.dummy.dtos;

import app.bank.dummy.enums.AccountStatus;
import app.bank.dummy.enums.Currency;
import java.io.Serial;
import java.io.Serializable;
import java.util.UUID;

/**
 * DTO for {@link app.bank.dummy.entities.Account}
 */
public record ClientAccountDto(UUID id, double balance, Currency currency, AccountStatus status) implements Serializable {

  @Serial
  private static final long serialVersionUID = -6556668595556601603L;

}