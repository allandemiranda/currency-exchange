package app.bank.dummy.services;

import app.bank.dummy.dtos.AccountDto;
import app.bank.dummy.dtos.ClientDto;
import app.bank.dummy.dtos.NewClientDto;
import app.bank.dummy.dtos.UpdateClientDto;
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
  ClientDto createClient(final @NotNull NewClientDto newClientDto);

  @Transactional
  @NotNull
  ClientDto updateClient(final @NotNull Long id, @NotNull final UpdateClientDto updateClientDto);

  @Transactional
  void deleteClient(final @NotNull Long id);

  @Transactional(readOnly = true)
  @NotNull
  Collection<@NotNull AccountDto> getAccountsByClientId(final @NotNull Long id);

}
