package app.bank.dummy.providers;

import app.bank.dummy.dtos.AccountDto;
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
import jakarta.validation.constraints.NotNull;
import java.util.Collection;
import java.util.UUID;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Getter(AccessLevel.PRIVATE)
public class AccountProvider implements AccountService {

  private final AccountRepository accountRepository;
  private final ClientRepository clientRepository;
  private final AccountMapper accountMapper;

  @Override
  public AccountDto getAccount(final UUID id) {
    final Account account = this.getAccountRepository().findById(id).orElseThrow(() -> new AccountNotFoundException(id));
    return this.getAccountMapper().toDto(account);
  }

  @Override
  public Collection<@NotNull AccountDto> getOpenAccounts() {
    final Collection<Account> accounts = this.getAccountRepository().findAll();
    return accounts.stream().map(this.getAccountMapper()::toDto).toList();
  }

  @Override
  public AccountDto closeAccount(final UUID id) {
    final Account account = this.getAccountRepository().findById(id).orElseThrow(() -> new AccountNotFoundException(id));
    account.setStatus(AccountStatus.CLOSE);
    final Account savedAccount = this.getAccountRepository().save(account);
    return this.getAccountMapper().toDto(savedAccount);
  }

  @Override
  public Collection<@NotNull AccountDto> getAccountsByClientId(final @NotNull Long clientId) {
    final Collection<Account> accounts = this.getAccountRepository().findByClient_Id(clientId);
    return accounts.stream().map(this.getAccountMapper()::toDto).toList();
  }

  @Override
  public AccountDto createAccount(final Long clientId, final NewAccountDto newAccountDto) {
    final Client client = this.getClientRepository().findById(clientId).orElseThrow(() -> new ClientNotFoundException(clientId));
    final Account newAccount = this.getAccountMapper().toEntity(newAccountDto);
    newAccount.setClient(client);
    newAccount.setStatus(AccountStatus.OPEN);
    final Account savedAccount = this.getAccountRepository().save(newAccount);
    return this.getAccountMapper().toDto(savedAccount);
  }
}
