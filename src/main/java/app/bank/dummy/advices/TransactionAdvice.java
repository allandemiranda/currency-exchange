package app.bank.dummy.advices;

import app.bank.dummy.exceptions.AccountFondsInsuffisantException;
import app.bank.dummy.exceptions.RateTaxUnavailableException;
import app.bank.dummy.exceptions.TransactionNotFoundException;
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
public class TransactionAdvice {

  @Getter(AccessLevel.PRIVATE)
  private final CustomErrorAttributesHandler attributes;

  @ExceptionHandler(TransactionNotFoundException.class)
  @ResponseStatus(HttpStatus.NOT_FOUND)
  public Map<String, Object> transactionNotFoundHandler(final @NonNull WebRequest webRequest) {
    return this.getAttributes().extractErrorAttributes(webRequest, HttpStatus.NOT_FOUND);
  }

  @ExceptionHandler(RateTaxUnavailableException.class)
  @ResponseStatus(HttpStatus.SERVICE_UNAVAILABLE)
  public Map<String, Object> rateTaxUnavailableHandler(final @NonNull WebRequest webRequest) {
    return this.getAttributes().extractErrorAttributes(webRequest, HttpStatus.SERVICE_UNAVAILABLE);
  }

  @ExceptionHandler(AccountFondsInsuffisantException.class)
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  public Map<String, Object> foundsInsuffisantHandler(final @NonNull WebRequest webRequest) {
    return this.getAttributes().extractErrorAttributes(webRequest, HttpStatus.BAD_REQUEST);
  }

}
