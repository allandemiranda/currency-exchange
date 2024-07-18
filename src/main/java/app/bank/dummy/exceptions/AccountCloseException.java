package app.bank.dummy.exceptions;

import java.util.UUID;
import lombok.experimental.StandardException;

@StandardException
public class AccountCloseException extends RuntimeException {

  public AccountCloseException(final UUID id) {
    super(String.format("Account id %s close ", id));
  }
}
