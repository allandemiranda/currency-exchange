package app.bank.dummy.exceptions;

import java.util.UUID;
import lombok.NonNull;
import lombok.experimental.StandardException;

@StandardException
public class CurrencyNotFoundException extends RuntimeException {

  public CurrencyNotFoundException(final @NonNull UUID clientId) {
    super(String.format("Currency id %s not found ", clientId));
  }

  public CurrencyNotFoundException(final String code) {
    super(String.format("Currency code %s not found ", code));
  }
}
