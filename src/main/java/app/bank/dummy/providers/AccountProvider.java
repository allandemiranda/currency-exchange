package app.bank.dummy.providers;

import app.bank.dummy.dtos.AccountDto;
import app.bank.dummy.dtos.ClientAccountDto;
import app.bank.dummy.dtos.NewAccountDto;
import app.bank.dummy.entities.Account;
import app.bank.dummy.entities.Client;
import app.bank.dummy.enums.AccountStatus;
import app.bank.dummy.exceptions.AccountNotFoundException;
import app.bank.dummy.exceptions.ClientNotFoundException;
import app.bank.dummy.mappers.AccountMapper;
import app.bank.dummy.repositories.AccountRepository;
import app.bank.dummy.repositories.ClientRepository;
import app.bank.dummy.services.AccountService;
import java.util.Collection;
import java.util.UUID;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Getter(AccessLevel.PRIVATE)
public class AccountProvider implements AccountService {

  private final AccountRepository accountRepository;
  private final ClientRepository clientRepository;
  private final AccountMapper accountMapper;

  private static Account getAccount(final @NonNull UUID accountId, final @NonNull Client client) {
    return client.getAccounts().stream().filter(acc -> accountId.equals(acc.getId())).findFirst().orElseThrow(() -> new AccountNotFoundException(accountId));
  }

  @Override
  public @NonNull AccountDto getAccount(final @NonNull UUID accountId) {
    final Account account = this.getAccountRepository().findById(accountId).orElseThrow(() -> new AccountNotFoundException(accountId));
    return this.getAccountMapper().toDto(account);
  }

  @Override
  public @NonNull ClientAccountDto getClientAccount(final @NonNull Long clientId, final @NonNull UUID accountId) {
    final Client client = this.getClient(clientId);
    final Account account = getAccount(accountId, client);
    return this.getAccountMapper().toDtoClient(account);
  }

  @Override
  public @NonNull Collection<@NonNull AccountDto> getAccounts() {
    final Collection<Account> accounts = this.getAccountRepository().findAll();
    return accounts.stream().map(this.getAccountMapper()::toDto).toList();
  }

  @Override
  public @NonNull ClientAccountDto closeClientAccount(final @NonNull Long clientId, final @NonNull UUID accountId) {
    final Client client = this.getClient(clientId);
    final Account account = getAccount(accountId, client);
    account.setStatus(AccountStatus.CLOSE);
    final Account savedAccount = this.getAccountRepository().save(account);
    return this.getAccountMapper().toDtoClient(savedAccount);
  }

  @Override
  public @NonNull Collection<@NonNull ClientAccountDto> getClientAccounts(final @NonNull Long clientId) {
    final Collection<Account> accounts = this.getAccountRepository().findByClient_Id(clientId);
    return accounts.stream().map(this.getAccountMapper()::toDtoClient).toList();
  }

  @Override
  public @NonNull ClientAccountDto createClientAccount(final @NonNull Long clientId, final @NonNull NewAccountDto newAccountDto) {
    final Client client = this.getClient(clientId);
    final Account newAccount = this.getAccountMapper().toEntity(newAccountDto);
    newAccount.setClient(client);
    newAccount.setStatus(AccountStatus.OPEN);
    final Account savedAccount = this.getAccountRepository().save(newAccount);
    return this.getAccountMapper().toDtoClient(savedAccount);
  }

  private Client getClient(final @NonNull Long clientId) {
    return this.getClientRepository().findById(clientId).orElseThrow(() -> new ClientNotFoundException(clientId));
  }
}
