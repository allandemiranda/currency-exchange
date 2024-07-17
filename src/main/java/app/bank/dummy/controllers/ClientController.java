package app.bank.dummy.controllers;

import app.bank.dummy.dtos.ClientDto;
import app.bank.dummy.dtos.CurrencyDto;
import app.bank.dummy.dtos.NewClientDto;
import app.bank.dummy.requests.ClientRequest;
import app.bank.dummy.services.ClientService;
import app.bank.dummy.services.CurrencyService;
import java.util.Collection;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Getter(AccessLevel.PRIVATE)
public class ClientController implements ClientRequest {

  private final ClientService clientService;
  private final CurrencyService currencyService;

  @Override
  public Collection<ClientDto> getClients() {
    return this.getClientService().getClients();
  }

  @Override
  public ClientDto getClients(final Long id) {
    return this.getClientService().getClientById(id);
  }

  @Override
  public ClientDto createClient(final @NonNull NewClientDto newClientDto) {
    final CurrencyDto currencyDto = this.getCurrencyService().getCurrencyByCode(newClientDto.accountCurrencyCode());
    return this.getClientService().createClient(newClientDto, currencyDto);
  }
}
