package app.bank.dummy.services;

import app.bank.dummy.dtos.AccountDto;
import app.bank.dummy.dtos.TransactionDto;
import jakarta.validation.constraints.Negative;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import java.util.Collection;
import org.springframework.transaction.annotation.Transactional;

public interface TransactionService {

  @Transactional
  @NotNull
  TransactionDto createTransaction(final @NotNull AccountDto debitAccount, final @NotNull AccountDto creditAccount, final @Negative double amount, final @Positive double taxRate);

  @Transactional(readOnly = true)
  @NotNull
  Collection<@NotNull TransactionDto> getAllTransactions();

}
