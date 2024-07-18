package app.bank.dummy.assemblers;

import app.bank.dummy.dtos.CurrencyDto;
import app.bank.dummy.requests.CurrencyRequest;
import lombok.NonNull;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.stereotype.Component;

@Component
public class CurrencyAssembler implements RepresentationModelAssembler<CurrencyDto, EntityModel<CurrencyDto>> {

  @Override
  public @NonNull EntityModel<CurrencyDto> toModel(final @NonNull CurrencyDto currencyDto) {
    return EntityModel.of(currencyDto, WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(CurrencyRequest.class).getCurrency(currencyDto.code())).withSelfRel(),
        WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(CurrencyRequest.class).getCurrencies()).withRel("currencies"));
  }
}
