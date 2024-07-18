package app.bank.dummy.requests;

import app.bank.dummy.dtos.NewTransactionDto;
import app.bank.dummy.dtos.TransactionDto;
import jakarta.validation.Valid;
import java.util.UUID;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;

@RequestMapping("/transactions")
public interface TransactionRequest {

  @GetMapping()
  @ResponseStatus(HttpStatus.OK)
  CollectionModel<EntityModel<TransactionDto>> getTransactions();

  @PostMapping()
  @ResponseStatus(HttpStatus.CREATED)
  ResponseEntity<EntityModel<TransactionDto>> createTransaction(@RequestBody @Valid NewTransactionDto newTransactionDto);

  @GetMapping("/{id}")
  @ResponseStatus(HttpStatus.OK)
  EntityModel<TransactionDto> getTransaction(@PathVariable final UUID id);

  @GetMapping("/{id}/debitAccount")
  @ResponseStatus(HttpStatus.OK)
  CollectionModel<EntityModel<TransactionDto>> getTransactionDebitAccounts(@PathVariable final UUID id);

  @GetMapping("/{id}/creditAccount")
  @ResponseStatus(HttpStatus.OK)
  CollectionModel<EntityModel<TransactionDto>> getTransactionCreditAccounts(@PathVariable final UUID id);
}
