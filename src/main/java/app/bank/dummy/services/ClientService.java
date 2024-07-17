package app.bank.dummy.services;

import app.bank.dummy.dtos.ClientDto;
import app.bank.dummy.dtos.CurrencyDto;
import app.bank.dummy.dtos.NewClientDto;
import jakarta.validation.constraints.NotNull;
import java.util.Collection;
import org.springframework.transaction.annotation.Transactional;

public interface ClientService {

  @Transactional(readOnly = true)
  @NotNull
  ClientDto getClientById(final @NotNull Long id);

  @Transactional(readOnly = true)
  @NotNull
  Collection<@NotNull ClientDto> getClients();

  @Transactional
  @NotNull
  ClientDto createClient(@NotNull final NewClientDto newClientDto, final @NotNull CurrencyDto currencyDto);

}
