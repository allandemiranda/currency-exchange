package app.bank.dummy.requests;

import app.bank.dummy.dtos.CurrencyDto;
import jakarta.validation.Valid;
import java.util.Collection;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;

@RequestMapping("/currencies")
public interface CurrencyRequest {

  @GetMapping()
  @ResponseStatus(HttpStatus.OK)
  Collection<CurrencyDto> getCurrencies();

  @PostMapping()
  @ResponseStatus(HttpStatus.CREATED)
  CurrencyDto createCurrency(final @RequestBody @Valid CurrencyDto currencyDto);
}
