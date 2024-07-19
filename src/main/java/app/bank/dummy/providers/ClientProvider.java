package app.bank.dummy.providers;

import app.bank.dummy.dtos.ClientDto;
import app.bank.dummy.dtos.NewClientDto;
import app.bank.dummy.dtos.UpdateClientDto;
import app.bank.dummy.entities.Account;
import app.bank.dummy.entities.Client;
import app.bank.dummy.enums.AccountStatus;
import app.bank.dummy.enums.ClientStatus;
import app.bank.dummy.exceptions.AccountNotFoundException;
import app.bank.dummy.exceptions.ClientNotFoundException;
import app.bank.dummy.mappers.ClientMapper;
import app.bank.dummy.repositories.AccountRepository;
import app.bank.dummy.repositories.ClientRepository;
import app.bank.dummy.services.ClientService;
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
public class ClientProvider implements ClientService {

  private final ClientRepository clientRepository;
  private final AccountRepository accountRepository;
  private final ClientMapper clientMapper;

  @Override
  public ClientDto getClient(final Long clientId) {
    final Client client = this.getClientRepository().findById(clientId).orElseThrow(() -> new ClientNotFoundException(clientId));
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
    newClient.getInfo().setStatus(ClientStatus.ACTIVATE);
    final Client savedClient = this.getClientRepository().save(newClient);
    return this.getClientMapper().toDto(savedClient);
  }

  @Override
  public ClientDto updateClient(final Long clientId, final @NotNull UpdateClientDto updateClientDto) {
    final Client client = this.getClientRepository().findById(clientId).orElseThrow(() -> new ClientNotFoundException(clientId));
    final Client updatedClient = this.getClientMapper().partialUpdate(updateClientDto, client);
    final Client savedClient = this.getClientRepository().save(updatedClient);
    return this.getClientMapper().toDto(savedClient);
  }

  @Override
  public ClientDto deactivateClient(final Long clientId) {
    final Client client = this.getClientRepository().findById(clientId).orElseThrow(() -> new ClientNotFoundException(clientId));
    final Collection<Account> accounts = client.getAccounts().stream().peek(account -> account.setStatus(AccountStatus.CLOSE)).toList();
    this.getAccountRepository().saveAll(accounts);
    return this.getClientMapper().toDto(client);
  }

  @Override
  public ClientDto getClientByAccountId(final UUID accountId) {
    final Account account = this.getAccountRepository().findById(accountId).orElseThrow(() -> new AccountNotFoundException(accountId));
    return this.getClientMapper().toDto(account.getClient());
  }
}
