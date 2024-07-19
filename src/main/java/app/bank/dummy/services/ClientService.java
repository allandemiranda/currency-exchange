package app.bank.dummy.services;

import app.bank.dummy.dtos.ClientDto;
import app.bank.dummy.dtos.NewClientDto;
import app.bank.dummy.dtos.UpdateClientDto;
import jakarta.validation.constraints.NotNull;
import java.util.Collection;
import java.util.UUID;
import org.springframework.transaction.annotation.Transactional;

public interface ClientService {

  @Transactional(readOnly = true)
  @NotNull
  ClientDto getClient(final @NotNull Long clientId);

  @Transactional(readOnly = true)
  @NotNull
  Collection<@NotNull ClientDto> getClients();

  @Transactional
  @NotNull
  ClientDto createClient(final @NotNull NewClientDto newClientDto);

  @Transactional
  @NotNull
  ClientDto updateClient(final @NotNull Long clientId, @NotNull final UpdateClientDto updateClientDto);

  @Transactional
  ClientDto deactivateClient(final @NotNull Long clientId);

  @Transactional(readOnly = true)
  @NotNull
  ClientDto getClientByAccountId(@NotNull final UUID accountId);

}
