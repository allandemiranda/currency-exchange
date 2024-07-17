package app.bank.dummy.exceptions;

import lombok.experimental.StandardException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@StandardException
@ResponseStatus(HttpStatus.NOT_FOUND)
public class ClientNotFoundException extends RuntimeException {

  public ClientNotFoundException(final long clientId) {
    super(String.format("Client id %s not found ", clientId));
  }
}
