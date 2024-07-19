package app.bank.dummy.controllers;

import app.bank.dummy.assemblers.TransactionAssembler;
import app.bank.dummy.dtos.TransactionDto;
import app.bank.dummy.requests.TransactionRequest;
import app.bank.dummy.services.TransactionService;
import java.util.Collection;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Getter(AccessLevel.PRIVATE)
public class TransactionController implements TransactionRequest {

  private final TransactionService transactionService;
  private final TransactionAssembler transactionAssembler;

  @Override
  public CollectionModel<EntityModel<TransactionDto>> getTransactions() {
    final Collection<TransactionDto> transactionDtos = this.getTransactionService().getTransactions();
    return this.getTransactionAssembler().toCollectionModel(transactionDtos);
  }
}
