package app.bank.dummy.services;

import app.bank.dummy.dtos.NewTransactionDto;
import app.bank.dummy.dtos.TransactionDto;
import jakarta.validation.constraints.NotNull;
import java.util.Collection;
import java.util.UUID;
import org.springframework.transaction.annotation.Transactional;

public interface TransactionService {

  @Transactional(readOnly = true)
  @NotNull
  Collection<@NotNull TransactionDto> getTransactions(final @NotNull UUID accountId);

  @Transactional(readOnly = true)
  @NotNull
  TransactionDto getTransaction(final @NotNull UUID accountId, final @NotNull UUID id);

  @Transactional
  @NotNull
  TransactionDto createTransaction(final @NotNull UUID accountId, final @NotNull NewTransactionDto newTransactionDto);

}
