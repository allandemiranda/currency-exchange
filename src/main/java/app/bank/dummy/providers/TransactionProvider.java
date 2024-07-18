package app.bank.dummy.providers;

import app.bank.dummy.dtos.NewTransactionDto;
import app.bank.dummy.dtos.TransactionDto;
import app.bank.dummy.entities.Account;
import app.bank.dummy.entities.Transaction;
import app.bank.dummy.enums.AccountStatus;
import app.bank.dummy.enums.Currency;
import app.bank.dummy.enums.TransactionType;
import app.bank.dummy.exceptions.AccountCloseException;
import app.bank.dummy.exceptions.AccountNotFoundException;
import app.bank.dummy.exceptions.TransactionNotFoundException;
import app.bank.dummy.mappers.TransactionMapper;
import app.bank.dummy.repositories.AccountRepository;
import app.bank.dummy.repositories.TransactionRepository;
import app.bank.dummy.services.TransactionService;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Comparator;
import java.util.UUID;
import java.util.stream.Stream;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Getter(AccessLevel.PRIVATE)
public class TransactionProvider implements TransactionService {

  private final TransactionRepository transactionRepository;
  private final TransactionMapper transactionMapper;
  private final AccountRepository accountRepository;

  @Override
  public Collection<@NotNull TransactionDto> getTransactions(final UUID accountId) {
    final Account account = this.getAccountRepository().findById(accountId).orElseThrow(() -> new AccountNotFoundException(accountId));
    final Collection<TransactionDto> debitTransactionDtos = account.getDebitTransactions().stream().map(
            transaction -> this.getTransactionMapper().toDto(transaction, transaction.getDebitInfo().getTmpBalance(), TransactionType.DEBIT, transaction.getCreditInfo().getAccount().getId()))
        .toList();
    final Collection<TransactionDto> creditTransactionDtos = account.getDebitTransactions().stream().map(
            transaction -> this.getTransactionMapper().toDto(transaction, transaction.getCreditInfo().getTmpBalance(), TransactionType.CREDIT, transaction.getDebitInfo().getAccount().getId()))
        .toList();
    return Stream.concat(debitTransactionDtos.stream(), creditTransactionDtos.stream()).sorted(Comparator.comparing(TransactionDto::dataTime)).toList();
  }

  @Override
  public TransactionDto getTransaction(final UUID accountId, final UUID id) {
    final Account account = this.getAccountRepository().findById(accountId).orElseThrow(() -> new AccountNotFoundException(accountId));
    final Transaction transaction = this.getTransactionRepository().findById(id).orElseThrow(() -> new TransactionNotFoundException(id));
    if (account.equals(transaction.getDebitInfo().getAccount())) {
      return this.getTransactionMapper().toDto(transaction, transaction.getDebitInfo().getTmpBalance(), TransactionType.DEBIT, transaction.getCreditInfo().getAccount().getId());
    } else if (account.equals(transaction.getCreditInfo().getAccount())) {
      return this.getTransactionMapper().toDto(transaction, transaction.getCreditInfo().getTmpBalance(), TransactionType.CREDIT, transaction.getDebitInfo().getAccount().getId());
    } else {
      throw new TransactionNotFoundException(id);
    }
  }

  @Override
  public TransactionDto createTransaction(final UUID accountId, final @NonNull NewTransactionDto newTransactionDto) {
    final Account debit = this.getAccountRepository().findById(accountId).orElseThrow(() -> new AccountNotFoundException(accountId));
    if (AccountStatus.CLOSE.equals(debit.getStatus())) {
      throw new AccountCloseException(debit.getId());
    }
    debit.setBalance(debit.getBalance() - newTransactionDto.amount());
    final Account debitUpdate = this.getAccountRepository().save(debit);

    final Account credit = this.getAccountRepository().findById(newTransactionDto.creditAccountId()).orElseThrow(() -> new AccountNotFoundException(newTransactionDto.creditAccountId()));
    if (AccountStatus.CLOSE.equals(debit.getStatus())) {
      throw new AccountCloseException(debit.getId());
    }
    final double taxRate = this.getTaxRate(debit.getCurrency(), credit.getCurrency());
    credit.setBalance(credit.getBalance() + (newTransactionDto.amount() * taxRate));
    final Account creditUpdate = this.getAccountRepository().save(credit);

    final Transaction transaction = new Transaction();
    transaction.setDataTime(LocalDateTime.now());
    transaction.setTaxRate(taxRate);
    transaction.setAmount(newTransactionDto.amount());
    transaction.getDebitInfo().setAccount(debitUpdate);
    transaction.getCreditInfo().setAccount(creditUpdate);

    final Transaction saved = this.getTransactionRepository().save(transaction);
    return this.getTransactionMapper().toDto(saved, debitUpdate.getBalance(), TransactionType.DEBIT, credit.getId());
  }

  private double getTaxRate(final @NotNull Currency debitCurrency, final Currency creditCurrency) {
//
//    final String url = "https://v6.exchangerate-api.com/v6/".concat(key).concat("/latest/").concat(creditCode);
//    final HttpRequest request = HttpRequest.newBuilder().uri(URI.create(url)).method("GET", HttpRequest.BodyPublishers.noBody()).build();
//
//    try (final HttpClient httpClient = HttpClient.newHttpClient()) {
//      final HttpResponse<InputStream> response = httpClient.send(request, HttpResponse.BodyHandlers.ofInputStream());
//      final JsonElement root = JsonParser.parseReader(new InputStreamReader(response.body()));
//      return root.getAsJsonObject().asMap().get("conversion_rates").getAsJsonObject().asMap().get(debitCode).getAsDouble();
//    } catch (IOException | InterruptedException e) {
//      Thread.currentThread().interrupt();
//      throw new RateTaxUnavailableException();
//    }
//    throw new RateTaxUnavailableException("ERRO TESTE");
    return 1.5;
  }
}
