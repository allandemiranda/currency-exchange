package app.bank.dummy.services;

import app.bank.dummy.dtos.CurrencyDto;
import jakarta.validation.constraints.Positive;
import lombok.NonNull;
import org.springframework.transaction.annotation.Transactional;

public interface CurrencyService {

  @Transactional(readOnly = true)
  @Positive
  double getCreditAmount(final @NonNull CurrencyDto debitCurrency, final @NonNull CurrencyDto creditCurrency, final @Positive double amount);
}
