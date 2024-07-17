package app.bank.dummy.requests;

import app.bank.dummy.dtos.OpenTransactionDto;
import app.bank.dummy.dtos.TransactionDto;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import java.util.Collection;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;

@RequestMapping("/transactions")
public interface TransactionRequest {

  @GetMapping()
  @ResponseStatus(HttpStatus.OK)
  Collection<TransactionDto> getTransactions();

  @PostMapping()
  @ResponseStatus(HttpStatus.CREATED)
  TransactionDto createTransaction(@RequestBody @Valid @NotNull OpenTransactionDto openTransactionDto);
}