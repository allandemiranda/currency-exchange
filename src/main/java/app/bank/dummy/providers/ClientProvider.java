package app.bank.dummy.providers;

import app.bank.dummy.dtos.AccountDto;
import app.bank.dummy.dtos.ClientDto;
import app.bank.dummy.dtos.NewClientDto;
import app.bank.dummy.dtos.UpdateClientDto;
import app.bank.dummy.entities.Account;
import app.bank.dummy.entities.Client;
import app.bank.dummy.enums.AccountStatus;
import app.bank.dummy.exceptions.ClientNotFoundException;
import app.bank.dummy.mappers.AccountMapper;
import app.bank.dummy.mappers.ClientMapper;
import app.bank.dummy.mappers.CurrencyMapper;
import app.bank.dummy.repositories.AccountRepository;
import app.bank.dummy.repositories.ClientRepository;
import app.bank.dummy.services.ClientService;
import jakarta.validation.constraints.NotNull;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Getter(AccessLevel.PRIVATE)
public class ClientProvider implements ClientService {

  private final ClientRepository clientRepository;
  private final AccountRepository accountRepository;
  private final ClientMapper clientMapper;
  private final CurrencyMapper currencyMapper;
  private final AccountMapper accountMapper;

  @Override
  public ClientDto getClientById(final Long id) {
    final Client client = this.getClientRepository().findById(id).orElseThrow(() -> new ClientNotFoundException(id));
    return this.getClientMapper().toDto(client);
  }

  @Override
  public Collection<@NotNull ClientDto> getClients() {
    final Collection<Client> clients = this.getClientRepository().findAll();
    return clients.stream().map(this.getClientMapper()::toDto).toList();
  }

  @Override
  public ClientDto createClient(final NewClientDto newClientDto) {
    final Client newClient = this.getClientMapper().toEntity(newClientDto);
    final Client savedClient = this.getClientRepository().saveAndFlush(newClient);
    return this.getClientMapper().toDto(savedClient);
  }

  @Override
  public ClientDto updateClient(final Long id, final @NotNull UpdateClientDto updateClientDto) {
    final Client client = this.getClientRepository().findById(id).orElseThrow(() -> new ClientNotFoundException(id));
    final Client updatedClient = this.getClientMapper().partialUpdate(updateClientDto, client);
    final Client savedClient = this.getClientRepository().saveAndFlush(updatedClient);
    return this.getClientMapper().toDto(savedClient);
  }

  @Override
  public void deleteClient(final Long id) {
    final Client client = this.getClientRepository().findById(id).orElseThrow(() -> new ClientNotFoundException(id));
    final Collection<Account> accounts = client.getAccounts().stream().peek(account -> account.setStatus(AccountStatus.CLOSE)).toList();
    this.getAccountRepository().saveAll(accounts);
    this.getClientRepository().deleteById(id);
  }

  @Override
  public Collection<@NotNull AccountDto> getAccountsByClientId(final Long id) {
    final Client client = this.getClientRepository().findById(id).orElseThrow(() -> new ClientNotFoundException(id));
    return client.getAccounts().stream().map(this.getAccountMapper()::toDto).collect(Collectors.toSet());
  }
}
