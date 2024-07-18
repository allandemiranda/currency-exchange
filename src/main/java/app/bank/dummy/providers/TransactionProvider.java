package app.bank.dummy.providers;

import app.bank.dummy.dtos.TransactionDto;
import app.bank.dummy.entities.Account;
import app.bank.dummy.entities.Transaction;
import app.bank.dummy.enums.AccountStatus;
import app.bank.dummy.exceptions.AccountNotFoundException;
import app.bank.dummy.exceptions.TransactionNotFoundException;
import app.bank.dummy.mappers.AccountMapper;
import app.bank.dummy.mappers.TransactionMapper;
import app.bank.dummy.repositories.AccountRepository;
import app.bank.dummy.repositories.TransactionRepository;
import app.bank.dummy.services.TransactionService;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.UUID;
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
  private final AccountRepository accountRepository;
  private final AccountMapper accountMapper;

  @Override
  public TransactionDto createTransaction(final @NotNull UUID debitAccountId, final @NotNull UUID creditAccountId, final double amount, final double taxRate) {
    final Account debit = this.getAccountRepository().findByIdAndStatus(debitAccountId, AccountStatus.OPEN).orElseThrow(() -> new AccountNotFoundException(debitAccountId));
    debit.setBalance(debit.getBalance() - amount);
    final Account debitUpdate = this.getAccountRepository().save(debit);

    final Account credit = this.getAccountRepository().findByIdAndStatus(creditAccountId, AccountStatus.OPEN).orElseThrow(() -> new AccountNotFoundException(creditAccountId));
    credit.setBalance(credit.getBalance() + (amount * taxRate));
    final Account creditUpdate = this.getAccountRepository().save(credit);

    final Transaction transaction = new Transaction();
    transaction.setDataTime(LocalDateTime.now());
    transaction.setTaxRate(taxRate);
    transaction.setAmount(amount);
    transaction.getDebitInfo().setAccount(debitUpdate);
    transaction.getCreditInfo().setAccount(creditUpdate);

    final Transaction saved = this.getTransactionRepository().save(transaction);
    return this.getTransactionMapper().toDto(saved);
  }

  @Override
  public TransactionDto getTransaction(final UUID transactionId) {
    final Transaction transaction = this.getTransactionRepository().findById(transactionId).orElseThrow(() -> new TransactionNotFoundException(transactionId));
    return this.getTransactionMapper().toDto(transaction);
  }

  @Override
  public Collection<@NotNull TransactionDto> getAllTransactions() {
    final Collection<Transaction> transactions = this.getTransactionRepository().findAll();
    return transactions.stream().map(this.getTransactionMapper()::toDto).toList();
  }

  @Override
  public Collection<@NotNull TransactionDto> getTransactionsByDebitAccountId(final UUID debitAccountId) {
    final Collection<Transaction> transactions = this.getTransactionRepository().findByDebitInfo_Account_Id(debitAccountId);
    return transactions.stream().map(this.getTransactionMapper()::toDto).toList();
  }

  @Override
  public Collection<@NotNull TransactionDto> getTransactionsByCreditAccountId(final UUID creditAccountId) {
    final Collection<Transaction> transactions = this.getTransactionRepository().findByCreditInfo_Account_Id(creditAccountId);
    return transactions.stream().map(this.getTransactionMapper()::toDto).toList();
  }
}
