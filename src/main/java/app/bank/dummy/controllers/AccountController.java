package app.bank.dummy.controllers;

import app.bank.dummy.assemblers.AccountAssembler;
import app.bank.dummy.assemblers.ClientAssembler;
import app.bank.dummy.dtos.AccountDto;
import app.bank.dummy.dtos.ClientDto;
import app.bank.dummy.requests.AccountRequest;
import app.bank.dummy.services.AccountService;
import app.bank.dummy.services.ClientService;
import java.util.Collection;
import java.util.UUID;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Getter(AccessLevel.PRIVATE)
public class AccountController implements AccountRequest {

  private final AccountService accountService;
  private final ClientService clientService;
  private final AccountAssembler accountAssembler;
  private final ClientAssembler clientAssembler;

  @Override
  public CollectionModel<EntityModel<AccountDto>> getAccounts() {
    final Collection<AccountDto> clientAccountDtos = this.getAccountService().getAccounts();
    return this.getAccountAssembler().toCollectionModel(clientAccountDtos);
  }

  @Override
  public EntityModel<AccountDto> getAccount(final UUID id) {
    final AccountDto clientAccountDto = this.getAccountService().getAccount(id);
    return this.getAccountAssembler().toModel(clientAccountDto);
  }

  @Override
  public EntityModel<ClientDto> getAccountClient(final UUID accountId) {
    final ClientDto clientDto = this.getClientService().getClientByAccountId(accountId);
    return this.getClientAssembler().toModel(clientDto);
  }
}
