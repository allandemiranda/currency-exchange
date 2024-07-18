package app.bank.dummy.assemblers;

import app.bank.dummy.controllers.CurrencyController;
import app.bank.dummy.dtos.CurrencyDto;
import lombok.NonNull;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.stereotype.Component;

@Component
public class CurrencyAssembler implements RepresentationModelAssembler<CurrencyDto, EntityModel<CurrencyDto>> {

  @Override
  public @NonNull EntityModel<CurrencyDto> toModel(final @NonNull CurrencyDto currencyDto) {
    return EntityModel.of(currencyDto, WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(CurrencyController.class).getCurrencyByCode(currencyDto.code())).withSelfRel(),
        WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(CurrencyController.class).getCurrencies()).withRel("currencies"));
  }
}
