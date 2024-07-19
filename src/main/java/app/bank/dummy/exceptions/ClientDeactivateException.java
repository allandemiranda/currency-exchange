package app.bank.dummy.exceptions;

import lombok.experimental.StandardException;

@StandardException
public class ClientDeactivateException extends RuntimeException {

  public ClientDeactivateException(final long clientId) {
    super(String.format("Client id %s deactivate ", clientId));
  }
}
