package app.bank.dummy.exceptions;

import lombok.experimental.StandardException;

@StandardException
public class ClientNotFoundException extends RuntimeException {

  public ClientNotFoundException(final long clientId) {
    super(String.format("Client id %s not found ", clientId));
  }
}
