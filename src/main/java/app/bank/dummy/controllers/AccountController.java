package app.bank.dummy.controllers;

import app.bank.dummy.assemblers.AccountAssembler;
import app.bank.dummy.assemblers.ClientAssembler;
import app.bank.dummy.assemblers.CurrencyAssembler;
import app.bank.dummy.dtos.AccountDto;
import app.bank.dummy.dtos.ClientDto;
import app.bank.dummy.dtos.CurrencyDto;
import app.bank.dummy.requests.AccountRequest;
import app.bank.dummy.services.AccountService;
import java.util.Collection;
import java.util.UUID;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Getter(AccessLevel.PRIVATE)
public class AccountController implements AccountRequest {

  private final AccountService accountService;
  private final AccountAssembler accountAssembler;
  private final ClientAssembler clientAssembler;
  private final CurrencyAssembler currencyAssembler;

  @Override
  public CollectionModel<EntityModel<AccountDto>> getAccounts() {
    final Collection<AccountDto> accountDtos = this.getAccountService().getAllAccounts();
    return this.getAccountAssembler().toCollectionModel(accountDtos);
  }

  @Override
  public EntityModel<AccountDto> getAccount(final UUID id) {
    final AccountDto accountDto = this.getAccountService().getAccountById(id);
    return this.getAccountAssembler().toModel(accountDto);
  }

  @Override
  public ResponseEntity<EntityModel<AccountDto>> closeAccount(final UUID id) {
    final AccountDto accountDto = this.getAccountService().closeAccount(id);
    final EntityModel<AccountDto> entityModel = this.getAccountAssembler().toModel(accountDto);
    return ResponseEntity.created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri()).body(entityModel);
  }

  @Override
  public EntityModel<ClientDto> getAccountsClient(final UUID id) {
    final ClientDto clientDto = this.getAccountService().getClientByAccountId(id);
    return this.getClientAssembler().toModel(clientDto);
  }

  @Override
  public EntityModel<CurrencyDto> getAccountsCurrency(final UUID id) {
    final CurrencyDto currencyDto = this.getAccountService().getCurrencyByAccountId(id);
    return this.getCurrencyAssembler().toModel(currencyDto);
  }
}
