package app.bank.dummy.providers;

import app.bank.dummy.dtos.ClientTransactionDto;
import app.bank.dummy.dtos.NewTransactionDto;
import app.bank.dummy.dtos.TransactionDto;
import app.bank.dummy.entities.Account;
import app.bank.dummy.entities.Client;
import app.bank.dummy.entities.Transaction;
import app.bank.dummy.entities.TransactionCreditInfo;
import app.bank.dummy.entities.TransactionDebitInfo;
import app.bank.dummy.enums.AccountStatus;
import app.bank.dummy.enums.ClientStatus;
import app.bank.dummy.enums.Currency;
import app.bank.dummy.enums.TransactionType;
import app.bank.dummy.exceptions.AccountCloseException;
import app.bank.dummy.exceptions.AccountFondsInsuffisantException;
import app.bank.dummy.exceptions.AccountNotFoundException;
import app.bank.dummy.exceptions.ClientDeactivateException;
import app.bank.dummy.exceptions.ClientNotFoundException;
import app.bank.dummy.exceptions.RateTaxUnavailableException;
import app.bank.dummy.exceptions.TransactionNotFoundException;
import app.bank.dummy.mappers.TransactionMapper;
import app.bank.dummy.repositories.AccountRepository;
import app.bank.dummy.repositories.ClientRepository;
import app.bank.dummy.repositories.TransactionRepository;
import app.bank.dummy.services.TransactionService;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import jakarta.validation.constraints.Positive;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Comparator;
import java.util.UUID;
import java.util.stream.Stream;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Getter(AccessLevel.PRIVATE)
public class TransactionProvider implements TransactionService {

  private final TransactionRepository transactionRepository;
  private final TransactionMapper transactionMapper;
  private final AccountRepository accountRepository;
  private final ClientRepository clientRepository;
  @Value("${external-api.key}")
  private String key;

  private static Account getAccount(final @NonNull UUID accountId, final @NonNull Client client) {
    return client.getAccounts().stream().filter(acc -> acc.getId().equals(accountId)).findFirst().orElseThrow(() -> new AccountNotFoundException(accountId));
  }

  @Override
  public @NonNull Collection<@NonNull TransactionDto> getTransactions() {
    final Collection<Transaction> transactions = this.getTransactionRepository().findAll();
    return transactions.stream().map(transaction -> this.getTransactionMapper().toDto(transaction)).toList();
  }

  @Override
  public @NonNull Collection<@NonNull ClientTransactionDto> getClientAccountTransactions(final @NonNull Long clientId, final @NonNull UUID accountId) {
    final Client client = this.getClient(clientId);
    final Account account = getAccount(accountId, client);
    final Collection<ClientTransactionDto> debitClientTransactionDtos = this.getTransactionRepository().findByDebitInfo_Account_Id(account.getId()).stream().map(
            transaction -> this.getTransactionMapper().toDto(transaction, transaction.getDebitInfo().getTmpBalance(), TransactionType.DEBIT, transaction.getCreditInfo().getAccount().getId()))
        .toList();
    final Collection<ClientTransactionDto> creditClientTransactionDtos = this.getTransactionRepository().findByCreditInfo_Account_Id(account.getId()).stream().map(
            transaction -> this.getTransactionMapper().toDto(transaction, transaction.getCreditInfo().getTmpBalance(), TransactionType.CREDIT, transaction.getDebitInfo().getAccount().getId()))
        .toList();
    return Stream.concat(debitClientTransactionDtos.stream(), creditClientTransactionDtos.stream()).sorted(Comparator.comparing(ClientTransactionDto::dataTime)).toList();
  }

  @Override
  public @NonNull ClientTransactionDto getClientAccountTransaction(final @NonNull Long clientId, final @NonNull UUID accountId, final @NonNull UUID transactionId) {
    final Client client = this.getClient(clientId);
    final Account account = getAccount(accountId, client);
    final Transaction transaction = this.getTransactionRepository().findById(transactionId).orElseThrow(() -> new TransactionNotFoundException(transactionId));
    if (account.equals(transaction.getDebitInfo().getAccount())) {
      return this.getTransactionMapper().toDto(transaction, transaction.getDebitInfo().getTmpBalance(), TransactionType.DEBIT, transaction.getCreditInfo().getAccount().getId());
    } else if (account.equals(transaction.getCreditInfo().getAccount())) {
      return this.getTransactionMapper().toDto(transaction, transaction.getCreditInfo().getTmpBalance(), TransactionType.CREDIT, transaction.getDebitInfo().getAccount().getId());
    } else {
      throw new TransactionNotFoundException(transactionId);
    }
  }

  @Override
  public @NonNull ClientTransactionDto createClientAccountTransaction(final @NonNull Long clientId, final @NonNull UUID accountId, final @NonNull NewTransactionDto newTransactionDto) {
    final Transaction transaction = new Transaction();
    transaction.setDataTime(LocalDateTime.now());
    transaction.setAmount(newTransactionDto.amount());

    final Account debitUpdate = this.getDebitUpdate(clientId, accountId, newTransactionDto);
    final Account creditUpdate = this.getCreditUpdate(transaction, newTransactionDto, debitUpdate);

    final TransactionDebitInfo transactionDebitInfo = new TransactionDebitInfo();
    transactionDebitInfo.setAccount(debitUpdate);
    transaction.setDebitInfo(transactionDebitInfo);

    final TransactionCreditInfo transactionCreditInfo = new TransactionCreditInfo();
    transactionCreditInfo.setAccount(creditUpdate);
    transaction.setCreditInfo(transactionCreditInfo);

    final Transaction saved = this.getTransactionRepository().save(transaction);
    return this.getTransactionMapper().toDto(saved, debitUpdate.getBalance(), TransactionType.DEBIT, creditUpdate.getId());
  }

  private @NonNull Account getDebitUpdate(final @NonNull Long clientId, final @NonNull UUID accountId, final @NonNull NewTransactionDto newTransactionDto) {
    final Client client = this.getClient(clientId);
    final Account debit = getAccount(accountId, client);
    if (AccountStatus.CLOSE.equals(debit.getStatus())) {
      throw new AccountCloseException(debit.getId());
    } else {
      final double newDebitBalance = debit.getBalance() - newTransactionDto.amount();
      if (newDebitBalance < 0D) {
        throw new AccountFondsInsuffisantException(accountId);
      } else {
        debit.setBalance(newDebitBalance);
        return this.getAccountRepository().save(debit);
      }
    }
  }

  private @NonNull Account getCreditUpdate(final @NonNull Transaction transaction, final @NonNull NewTransactionDto newTransactionDto, final @NonNull Account debitUpdate) {
    final Account credit = this.getAccountRepository().findById(newTransactionDto.creditAccountId()).orElseThrow(() -> new AccountNotFoundException(newTransactionDto.creditAccountId()));
    if (AccountStatus.CLOSE.equals(credit.getStatus())) {
      throw new AccountCloseException(credit.getId());
    }
    final double taxRate = this.getTaxRate(debitUpdate.getCurrency(), credit.getCurrency());
    transaction.setTaxRate(taxRate);
    credit.setBalance(credit.getBalance() + (newTransactionDto.amount() * taxRate));
    return this.getAccountRepository().save(credit);
  }

  private @NonNull Client getClient(final @NonNull Long clientId) {
    final Client client = this.getClientRepository().findById(clientId).orElseThrow(() -> new ClientNotFoundException(clientId));
    if (ClientStatus.DEACTIVATE.equals(client.getInfo().getStatus())) {
      throw new ClientDeactivateException(clientId);
    } else {
      return client;
    }
  }

  @Positive
  private double getTaxRate(final @NonNull Currency debitCurrency, final @NonNull Currency creditCurrency) {
    final String url = "https://v6.exchangerate-api.com/v6/".concat(key).concat("/latest/").concat(creditCurrency.getName());
    final HttpRequest request = HttpRequest.newBuilder().uri(URI.create(url)).method("GET", HttpRequest.BodyPublishers.noBody()).build();

    try (final HttpClient httpClient = HttpClient.newHttpClient()) {
      final HttpResponse<InputStream> response = httpClient.send(request, HttpResponse.BodyHandlers.ofInputStream());
      final JsonElement root = JsonParser.parseReader(new InputStreamReader(response.body()));
      return root.getAsJsonObject().asMap().get("conversion_rates").getAsJsonObject().asMap().get(debitCurrency.getName()).getAsDouble();
    } catch (IOException | InterruptedException e) {
      Thread.currentThread().interrupt();
      throw new RateTaxUnavailableException();
    }
  }
}
