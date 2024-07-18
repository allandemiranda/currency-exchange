package app.bank.dummy.controllers;

import app.bank.dummy.assemblers.TransactionAssembler;
import app.bank.dummy.dtos.AccountDto;
import app.bank.dummy.dtos.NewTransactionDto;
import app.bank.dummy.dtos.TransactionDto;
import app.bank.dummy.requests.TransactionRequest;
import app.bank.dummy.services.AccountService;
import app.bank.dummy.services.ClientService;
import app.bank.dummy.services.CurrencyService;
import app.bank.dummy.services.TransactionService;
import java.util.Collection;
import java.util.UUID;
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
public class TransactionController implements TransactionRequest {

  private final TransactionService transactionService;
  private final AccountService accountService;
  private final ClientService clientService;
  private final CurrencyService currencyService;
  private final TransactionAssembler transactionAssembler;

  @Override
  public CollectionModel<EntityModel<TransactionDto>> getTransactions() {
    final Collection<TransactionDto> transactionDtos = this.getTransactionService().getAllTransactions();
    return this.getTransactionAssembler().toCollectionModel(transactionDtos);
  }

  @Override
  public ResponseEntity<EntityModel<TransactionDto>> createTransaction(final @NonNull NewTransactionDto newTransactionDto) {
    final AccountDto debitAccount = this.getAccountService().getAccountById(newTransactionDto.debitAccountId());
    final AccountDto creditAccount = this.getAccountService().getAccountById(newTransactionDto.creditAccountId());
    final double taxRate = this.getCurrencyService().getTaxRate(debitAccount.id(), creditAccount.id());
    final TransactionDto transaction = this.getTransactionService().createTransaction(debitAccount.id(), creditAccount.id(), newTransactionDto.amount(), taxRate);
    final EntityModel<TransactionDto> entityModel = this.getTransactionAssembler().toModel(transaction);
    return ResponseEntity.created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri()).body(entityModel);
  }

  @Override
  public EntityModel<TransactionDto> getTransaction(final UUID id) {
    final TransactionDto transaction = this.getTransactionService().getTransaction(id);
    return this.getTransactionAssembler().toModel(transaction);
  }

  @Override
  public CollectionModel<EntityModel<TransactionDto>> getTransactionDebitAccounts(final UUID id) {
    final Collection<TransactionDto> transactionDtos = this.getTransactionService().getTransactionsByDebitAccountId(id);
    return this.getTransactionAssembler().toCollectionModel(transactionDtos);
  }

  @Override
  public CollectionModel<EntityModel<TransactionDto>> getTransactionCreditAccounts(final UUID id) {
    final Collection<TransactionDto> transactionDtos = this.getTransactionService().getTransactionsByCreditAccountId(id);
    return this.getTransactionAssembler().toCollectionModel(transactionDtos);
  }
}
