package app.bank.dummy.providers;

import app.bank.dummy.dtos.ClientDto;
import app.bank.dummy.dtos.CurrencyDto;
import app.bank.dummy.dtos.NewClientDto;
import app.bank.dummy.exceptions.ClientNotFoundException;
import app.bank.dummy.mappers.ClientMapper;
import app.bank.dummy.mappers.CurrencyMapper;
import app.bank.dummy.models.Client;
import app.bank.dummy.models.Currency;
import app.bank.dummy.repositories.ClientRepository;
import app.bank.dummy.services.ClientService;
import jakarta.validation.constraints.NotNull;
import java.util.Collection;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Getter(AccessLevel.PRIVATE)
public class ClientProvider implements ClientService {

  private final ClientRepository clientRepository;
  private final ClientMapper clientMapper;
  private final CurrencyMapper currencyMapper;

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
  public ClientDto createClient(final NewClientDto newClientDto, final CurrencyDto currencyDto) {
    final Client newClient = this.getClientMapper().toEntity(newClientDto);
    final Currency currency = this.getCurrencyMapper().toEntity(currencyDto);
    newClient.getAccount().setCurrency(currency);
    final Client savedClient = this.getClientRepository().saveAndFlush(newClient);
    return this.getClientMapper().toDto(savedClient);
  }
}
