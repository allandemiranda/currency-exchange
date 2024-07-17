package app.bank.dummy.exceptions;

import java.util.UUID;
import lombok.experimental.StandardException;

@StandardException
public class AccountNotFoundException extends RuntimeException {

  public AccountNotFoundException(final UUID id) {
    super(String.format("Account id %s not found ", id));
  }

}
