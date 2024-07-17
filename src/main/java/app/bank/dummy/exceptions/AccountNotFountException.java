package app.bank.dummy.exceptions;

import java.util.UUID;
import lombok.experimental.StandardException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@StandardException
@ResponseStatus(HttpStatus.NOT_FOUND)
public class AccountNotFountException extends RuntimeException {

  public AccountNotFountException(final UUID id) {
    super(String.format("Account id %s not found ", id));
  }

}
