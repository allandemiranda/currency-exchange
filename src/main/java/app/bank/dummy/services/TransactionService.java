package app.bank.dummy.services;

import app.bank.dummy.dtos.ClientTransactionDto;
import app.bank.dummy.dtos.NewTransactionDto;
import app.bank.dummy.dtos.TransactionDto;
import java.util.Collection;
import java.util.UUID;
import lombok.NonNull;
import org.springframework.transaction.annotation.Transactional;

public interface TransactionService {

  @Transactional(readOnly = true)
  @NonNull
  Collection<@NonNull TransactionDto> getTransactions();

  @Transactional(readOnly = true)
  @NonNull
  Collection<@NonNull ClientTransactionDto> getClientAccountTransactions(final @NonNull Long clientId, final @NonNull UUID accountId);

  @Transactional(readOnly = true)
  @NonNull
  ClientTransactionDto getClientAccountTransaction(final @NonNull Long clientId, final @NonNull UUID accountId, final @NonNull UUID transactionId);

  @Transactional
  @NonNull
  ClientTransactionDto createClientAccountTransaction(final @NonNull Long clientId, final @NonNull UUID accountId, final @NonNull NewTransactionDto newTransactionDto);

}
