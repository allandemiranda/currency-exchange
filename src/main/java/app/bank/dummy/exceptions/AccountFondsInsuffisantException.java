package app.bank.dummy.exceptions;

import java.util.UUID;
import lombok.experimental.StandardException;

@StandardException
public class AccountFondsInsuffisantException extends RuntimeException {

  public AccountFondsInsuffisantException(final UUID id) {
    super(String.format("Account id %s fonds not enough for the transaction", id));
  }

}
