package app.bank.dummy.services;

import app.bank.dummy.dtos.CurrencyDto;
import app.bank.dummy.dtos.NewCurrencyDto;
import app.bank.dummy.dtos.UpdateCurrencyDto;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import java.util.Collection;
import java.util.UUID;
import lombok.NonNull;
import org.springframework.transaction.annotation.Transactional;

public interface CurrencyService {

  @Transactional(readOnly = true)
  @Positive
  double getTaxRate(final @NonNull UUID debitAccountId, final @NonNull UUID creditAccountId);

  @Transactional(readOnly = true)
  @NotNull
  CurrencyDto getCurrencyByCode(final @NonNull String code);

  @Transactional(readOnly = true)
  @NotNull
  Collection<@NotNull CurrencyDto> getAllCurrencies();

  @Transactional
  @NotNull
  CurrencyDto createCurrency(final @NotNull NewCurrencyDto newCurrencyDto);

  @Transactional
  @NotNull
  CurrencyDto updateCurrency(final @NotNull String code, final @NonNull UpdateCurrencyDto updateCurrencyDto);
}
