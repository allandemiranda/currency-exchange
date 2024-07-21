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
public class ClientProvider implements ClientService {

  private final ClientRepository clientRepository;
  private final AccountRepository accountRepository;
  private final ClientMapper clientMapper;

  @Override
  public @NonNull ClientDto getClient(final @NonNull Long clientId) {
    final Client client = this.getClientEntity(clientId);
    return this.getClientMapper().toDto(client);
  }

  @Override
  public @NonNull Collection<@NonNull ClientDto> getClients() {
    final Collection<Client> clients = this.getClientRepository().findAll();
    return clients.stream().map(this.getClientMapper()::toDto).toList();
  }

  @Override
  public @NonNull ClientDto createClient(final @NonNull NewClientDto newClientDto) {
    final Client newClient = this.getClientMapper().toEntity(newClientDto);
    newClient.getInfo().setStatus(ClientStatus.ACTIVATE);
    final Client savedClient = this.getClientRepository().save(newClient);
    return this.getClientMapper().toDto(savedClient);
  }

  @Override
  public @NonNull ClientDto updateClient(final @NonNull Long clientId, final @NonNull UpdateClientDto updateClientDto) {
    final Client client = this.getClientEntity(clientId);
    final Client updatedClient = this.getClientMapper().partialUpdate(updateClientDto, client);
    final Client savedClient = this.getClientRepository().save(updatedClient);
    return this.getClientMapper().toDto(savedClient);
  }

  @Override
  public void deactivateClient(final @NonNull Long clientId) {
    final Client client = this.getClientEntity(clientId);
    client.getAccounts().forEach(account -> account.setStatus(AccountStatus.CLOSE));
    client.getInfo().setStatus(ClientStatus.DEACTIVATE);
    this.getClientRepository().save(client);
  }

  @Override
  public @NonNull ClientDto getClientByAccountId(final @NonNull UUID accountId) {
    final Account account = this.getAccountRepository().findById(accountId).orElseThrow(() -> new AccountNotFoundException(accountId));
    return this.getClientMapper().toDto(account.getClient());
  }

  private Client getClientEntity(final @NonNull Long clientId) {
    return this.getClientRepository().findById(clientId).orElseThrow(() -> new ClientNotFoundException(clientId));
  }
}
