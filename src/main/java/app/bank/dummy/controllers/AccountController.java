package app.bank.dummy.controllers;

import app.bank.dummy.assemblers.AccountAssembler;
import app.bank.dummy.assemblers.ClientAssembler;
import app.bank.dummy.assemblers.TransactionAssembler;
import app.bank.dummy.dtos.AccountDto;
import app.bank.dummy.dtos.ClientDto;
import app.bank.dummy.dtos.NewTransactionDto;
import app.bank.dummy.dtos.TransactionDto;
import app.bank.dummy.requests.AccountRequest;
import app.bank.dummy.services.AccountService;
import app.bank.dummy.services.ClientService;
import app.bank.dummy.services.TransactionService;
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
  private final ClientService clientService;
  private final AccountAssembler accountAssembler;
  private final ClientAssembler clientAssembler;
  private final TransactionService transactionService;
  private final TransactionAssembler transactionAssembler;


  @Override
  public CollectionModel<EntityModel<AccountDto>> getAccounts() {
    final Collection<AccountDto> accountDtos = this.getAccountService().getOpenAccounts();
    return this.getAccountAssembler().toCollectionModel(accountDtos);
  }

  @Override
  public EntityModel<AccountDto> getAccount(final UUID id) {
    final AccountDto accountDto = this.getAccountService().getAccount(id);
    return this.getAccountAssembler().toModel(accountDto);
  }

  @Override
  public ResponseEntity<EntityModel<AccountDto>> closeAccount(final UUID id) {
    final AccountDto accountDto = this.getAccountService().closeAccount(id);
    final EntityModel<AccountDto> entityModel = this.getAccountAssembler().toModel(accountDto);
    return ResponseEntity.created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri()).body(entityModel);
  }

  @Override
  public EntityModel<ClientDto> getAccountClient(final UUID id) {
    final ClientDto clientDto = this.getClientService().getClientByAccountId(id);
    return this.getClientAssembler().toModel(clientDto);
  }

  @Override
  public CollectionModel<EntityModel<TransactionDto>> getAccountTransactions(final UUID id) {
    final Collection<TransactionDto> transactionDtos = this.getTransactionService().getTransactions(id);
    return this.getTransactionAssembler().toCollectionModel(transactionDtos);
  }

  @Override
  public EntityModel<TransactionDto> createAccountTransaction(final UUID id, final NewTransactionDto newTransactionDto) {
    final TransactionDto transactionDto = this.getTransactionService().createTransaction(id, newTransactionDto);
    return this.getTransactionAssembler().toModel(transactionDto);
  }

  @Override
  public EntityModel<TransactionDto> getAccountTransaction(final UUID id, final UUID transactionId) {
    final TransactionDto transactionDto = this.getTransactionService().getTransaction(id, transactionId);
    return this.getTransactionAssembler().toModel(transactionDto);
  }
}
