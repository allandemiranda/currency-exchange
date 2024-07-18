package app.bank.dummy.services;

import app.bank.dummy.dtos.TransactionDto;
import jakarta.validation.constraints.Negative;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import java.util.Collection;
import java.util.UUID;
import org.springframework.transaction.annotation.Transactional;

public interface TransactionService {

  @Transactional
  @NotNull
  TransactionDto createTransaction(final @NotNull UUID debitAccountId, final @NotNull UUID creditAccountId, final @Negative double amount, final @Positive double taxRate);

  @Transactional(readOnly = true)
  @NotNull
  TransactionDto getTransaction(final @NotNull UUID transactionId);

  @Transactional(readOnly = true)
  @NotNull
  Collection<@NotNull TransactionDto> getAllTransactions();

  @Transactional(readOnly = true)
  @NotNull
  Collection<@NotNull TransactionDto> getTransactionsByDebitAccountId(final @NotNull UUID debitAccountId);

  @Transactional(readOnly = true)
  @NotNull
  Collection<@NotNull TransactionDto> getTransactionsByCreditAccountId(final @NotNull UUID creditAccountId);

}
