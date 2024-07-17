package app.bank.dummy.controllers;

import app.bank.dummy.dtos.CurrencyDto;
import app.bank.dummy.dtos.NewCurrencyDto;
import app.bank.dummy.requests.CurrencyRequest;
import app.bank.dummy.services.CurrencyService;
import java.util.Collection;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Getter(AccessLevel.PRIVATE)
public class CurrencyController implements CurrencyRequest {

  private final CurrencyService currencyService;

  @Override
  public Collection<CurrencyDto> getCurrencies() {
    return this.getCurrencyService().getAllCurrencies();
  }

  @Override
  public CurrencyDto createCurrency(final NewCurrencyDto newCurrencyDto) {
    return this.getCurrencyService().createCurrency(newCurrencyDto);
  }
}
