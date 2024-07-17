package app.bank.dummy.advices;

import app.bank.dummy.exceptions.AccountNotFoundException;
import java.util.Map;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

@RestControllerAdvice
@RequiredArgsConstructor
public class AccountAdvice {

  @Getter(AccessLevel.PRIVATE)
  private final CustomErrorAttributesHandler attributes;

  @ExceptionHandler(AccountNotFoundException.class)
  @ResponseStatus(HttpStatus.NOT_FOUND)
  public Map<String, Object> accountNotFoundHandler(final @NonNull WebRequest webRequest) {
    return this.getAttributes().extractErrorAttributes(webRequest);
  }

}
