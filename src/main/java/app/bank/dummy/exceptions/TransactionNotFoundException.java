package app.bank.dummy.exceptions;

import java.util.UUID;
import lombok.experimental.StandardException;

@StandardException
public class TransactionNotFoundException extends RuntimeException {

  public TransactionNotFoundException(final UUID id) {
    super(String.format("Transaction id %s not found ", id));
  }

}
