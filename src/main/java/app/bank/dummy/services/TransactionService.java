package app.bank.dummy.services;

import app.bank.dummy.dtos.AccountDto;
import app.bank.dummy.dtos.TransactionDto;
import jakarta.validation.constraints.Negative;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import org.springframework.transaction.annotation.Transactional;

public interface TransactionService {

  @Transactional
  @NotNull
  TransactionDto createTransaction(final @NotNull AccountDto debitAccount, final @NotNull AccountDto creditAccount, final @Negative double debitAmount, final @Positive double creditAmount);

}
