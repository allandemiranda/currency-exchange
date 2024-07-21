package app.bank.dummy.services;

import app.bank.dummy.dtos.ClientDto;
import app.bank.dummy.dtos.NewClientDto;
import app.bank.dummy.dtos.UpdateClientDto;
import java.util.Collection;
import java.util.UUID;
import lombok.NonNull;
import org.springframework.transaction.annotation.Transactional;

public interface ClientService {

  @Transactional(readOnly = true)
  @NonNull
  ClientDto getClient(final @NonNull Long clientId);

  @Transactional(readOnly = true)
  @NonNull
  Collection<@NonNull ClientDto> getClients();

  @Transactional
  @NonNull
  ClientDto createClient(final @NonNull NewClientDto newClientDto);

  @Transactional
  @NonNull
  ClientDto updateClient(final @NonNull Long clientId, @NonNull final UpdateClientDto updateClientDto);

  @Transactional
  void deactivateClient(final @NonNull Long clientId);

  @Transactional(readOnly = true)
  @NonNull
  ClientDto getClientByAccountId(@NonNull final UUID accountId);

}
