package app.bank.dummy.exceptions;

import lombok.experimental.StandardException;


public class RateTaxUnavailableException extends RuntimeException {

  public RateTaxUnavailableException(final String currencyCode) {
    super("Rate tax not available for currency: ".concat(currencyCode));
  }
}
