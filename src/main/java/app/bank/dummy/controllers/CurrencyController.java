package app.bank.dummy.controllers;

import app.bank.dummy.assemblers.CurrencyAssembler;
import app.bank.dummy.dtos.CurrencyDto;
import app.bank.dummy.dtos.UpdateCurrencyDto;
import app.bank.dummy.requests.CurrencyRequest;
import app.bank.dummy.services.CurrencyService;
import java.util.Collection;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Getter(AccessLevel.PRIVATE)
public class CurrencyController implements CurrencyRequest {

  private final CurrencyService currencyService;
  private final CurrencyAssembler currencyAssembler;

  @Override
  public EntityModel<CurrencyDto> getCurrencyByCode(@NonNull final String code) {
    final CurrencyDto currency = this.getCurrencyService().getCurrencyByCode(code);
    return this.getCurrencyAssembler().toModel(currency);
  }

  @Override
  public CollectionModel<EntityModel<CurrencyDto>> getCurrencies() {
    final Collection<CurrencyDto> allCurrencies = this.getCurrencyService().getAllCurrencies();
    return this.getCurrencyAssembler().toCollectionModel(allCurrencies);
  }

  @Override
  public ResponseEntity<EntityModel<CurrencyDto>> createCurrency(final CurrencyDto currencyDto) {
    final CurrencyDto currency = this.getCurrencyService().createCurrency(currencyDto);
    final EntityModel<CurrencyDto> entityModel = this.getCurrencyAssembler().toModel(currency);
    return ResponseEntity.created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri()).body(entityModel);
  }

  @Override
  public ResponseEntity<EntityModel<CurrencyDto>> updateCurrencyByCode(final String code, final UpdateCurrencyDto updateCurrencyDto) {
    final CurrencyDto currency = this.getCurrencyService().updateCurrencyByCode(code, updateCurrencyDto);
    final EntityModel<CurrencyDto> entityModel = this.getCurrencyAssembler().toModel(currency);
    return ResponseEntity.created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri()).body(entityModel);
  }

}
