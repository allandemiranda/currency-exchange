package app.bank.dummy.dtos;

import app.bank.dummy.enums.AccountStatus;
import app.bank.dummy.enums.Currency;
import java.io.Serial;
import java.io.Serializable;
import java.util.UUID;

/**
 * DTO for {@link app.bank.dummy.entities.Account}
 */
public record AccountDto(UUID id, Currency currency, AccountStatus status) implements Serializable {

  @Serial
  private static final long serialVersionUID = -7807811461714587265L;

}