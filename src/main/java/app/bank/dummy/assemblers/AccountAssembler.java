package app.bank.dummy.assemblers;

import app.bank.dummy.dtos.AccountDto;
import app.bank.dummy.requests.AccountRequest;
import lombok.NonNull;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.stereotype.Component;

@Component
public class AccountAssembler implements RepresentationModelAssembler<AccountDto, EntityModel<AccountDto>> {

  @Override
  public @NonNull EntityModel<AccountDto> toModel(final @NonNull AccountDto accountDto) {
    return EntityModel.of(accountDto, WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(AccountRequest.class).getAccount(accountDto.id())).withSelfRel(),
        WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(AccountRequest.class).getAccounts()).withRel("accounts"));
  }
}
