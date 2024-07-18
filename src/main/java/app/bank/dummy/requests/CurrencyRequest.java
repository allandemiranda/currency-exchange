package app.bank.dummy.requests;

import app.bank.dummy.dtos.CurrencyDto;
import app.bank.dummy.dtos.UpdateCurrencyDto;
import jakarta.validation.Valid;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;

@RequestMapping("/currencies")
public interface CurrencyRequest {

  @GetMapping("/{code}")
  @ResponseStatus(HttpStatus.OK)
  EntityModel<CurrencyDto> getCurrencyByCode(final @PathVariable String code);

  @GetMapping()
  @ResponseStatus(HttpStatus.OK)
  CollectionModel<EntityModel<CurrencyDto>> getCurrencies();

  @PostMapping()
  @ResponseStatus(HttpStatus.CREATED)
  ResponseEntity<EntityModel<CurrencyDto>> createCurrency(final @RequestBody @Valid CurrencyDto currencyDto);

  @PutMapping("/{code}")
  @ResponseStatus(HttpStatus.CREATED)
  ResponseEntity<EntityModel<CurrencyDto>> updateCurrencyByCode(final @PathVariable String code, final @RequestBody @Valid UpdateCurrencyDto updateCurrencyDto);
}
