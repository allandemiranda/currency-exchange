package app.bank.dummy.assemblers;

import app.bank.dummy.dtos.AccountDto;
import app.bank.dummy.dtos.ClientAccountDto;
import app.bank.dummy.requests.AccountRequest;
import app.bank.dummy.requests.ClientRequest;
import jakarta.validation.constraints.NotNull;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;
import lombok.NonNull;
import org.springframework.hateoas.CollectionModel;
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

  public @NonNull EntityModel<ClientAccountDto> toModel(final @NonNull ClientAccountDto clientAccountDto, final @NotNull Long clientId) {
    return EntityModel.of(clientAccountDto, WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(ClientRequest.class).getClientAccount(clientId, clientAccountDto.id())).withSelfRel(),
        WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(ClientRequest.class).getClientAccounts(clientId)).withSelfRel());
  }

  public CollectionModel<EntityModel<ClientAccountDto>> toCollectionModel(final @NonNull Iterable<ClientAccountDto> entities, final @NotNull Long clientId) {
    return StreamSupport.stream(entities.spliterator(), false).map(accountDto -> this.toModel(accountDto, clientId))
        .collect(Collectors.collectingAndThen(Collectors.toList(), CollectionModel::of));
  }
}
