package app.bank.dummy.providers;

import app.bank.dummy.dtos.AccountDto;
import app.bank.dummy.dtos.TransactionDto;
import app.bank.dummy.mappers.AccountMapper;
import app.bank.dummy.mappers.TransactionMapper;
import app.bank.dummy.models.Account;
import app.bank.dummy.models.Transaction;
import app.bank.dummy.repositories.TransactionRepository;
import app.bank.dummy.services.TransactionService;
import java.time.LocalDateTime;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Getter(AccessLevel.PRIVATE)
public class TransactionProvider implements TransactionService {

  private final TransactionRepository transactionRepository;
  private final TransactionMapper transactionMapper;
  private final AccountMapper accountMapper;

  @Override
  public TransactionDto createTransaction(final AccountDto debitAccount, final AccountDto creditAccount, final double debitAmount, final double creditAmount) {
    final Transaction transaction = new Transaction();
    transaction.setDataTime(LocalDateTime.now());

    final Account debit = this.getAccountMapper().toEntity(debitAccount);
    debit.setBalance(debit.getBalance() + debitAmount);
    debit.getTransactionHistoric().put(transaction, debit.getBalance());
    transaction.setDebitAccount(debit);

    final Account credit = this.getAccountMapper().toEntity(creditAccount);
    credit.setBalance(credit.getBalance() + creditAmount);
    credit.getTransactionHistoric().put(transaction, credit.getBalance());
    transaction.setCreditAccount(credit);

    final Transaction saved = this.getTransactionRepository().save(transaction);
    return this.getTransactionMapper().toDto(saved);
  }
}
