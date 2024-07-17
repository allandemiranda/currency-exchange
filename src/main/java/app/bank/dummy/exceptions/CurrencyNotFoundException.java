package app.bank.dummy.exceptions;

import java.util.UUID;
import lombok.NonNull;
import lombok.experimental.StandardException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@StandardException
@ResponseStatus(HttpStatus.NOT_FOUND)
public class CurrencyNotFoundException extends RuntimeException {

  public CurrencyNotFoundException(final @NonNull UUID clientId) {
    super(String.format("Currency id %s not found ", clientId));
  }

  public CurrencyNotFoundException(final String code) {
    super(String.format("Currency code %s not found ", code));
  }
}
