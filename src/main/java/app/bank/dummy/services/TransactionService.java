package app.bank.dummy.services;

import app.bank.dummy.dtos.ClientTransactionDto;
import app.bank.dummy.dtos.NewTransactionDto;
import app.bank.dummy.dtos.TransactionDto;
import jakarta.validation.constraints.NotNull;
import java.util.Collection;
import java.util.UUID;
import org.springframework.transaction.annotation.Transactional;

public interface TransactionService {

  @Transactional(readOnly = true)
  @NotNull
  Collection<@NotNull TransactionDto> getTransactions();

  @Transactional(readOnly = true)
  @NotNull
  Collection<@NotNull ClientTransactionDto> getClientAccountTransactions(final @NotNull Long clientId, final @NotNull UUID accountId);

  @Transactional(readOnly = true)
  @NotNull
  ClientTransactionDto getClientAccountTransaction(final @NotNull Long clientId, final @NotNull UUID accountId, final @NotNull UUID transactionId);

  @Transactional
  @NotNull
  ClientTransactionDto createClientAccountTransaction(final @NotNull Long clientId, final @NotNull UUID accountId, final @NotNull NewTransactionDto newTransactionDto);

}
