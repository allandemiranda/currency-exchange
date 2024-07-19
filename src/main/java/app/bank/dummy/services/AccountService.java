package app.bank.dummy.services;

import app.bank.dummy.dtos.AccountDto;
import app.bank.dummy.dtos.ClientAccountDto;
import app.bank.dummy.dtos.NewAccountDto;
import java.util.Collection;
import java.util.UUID;
import lombok.NonNull;
import org.springframework.transaction.annotation.Transactional;

public interface AccountService {

  @Transactional(readOnly = true)
  @NonNull
  AccountDto getAccount(final @NonNull UUID accountId);

  @Transactional(readOnly = true)
  @NonNull
  Collection<@NonNull AccountDto> getAccounts();

  @Transactional(readOnly = true)
  @NonNull
  ClientAccountDto getClientAccount(final @NonNull Long clientId, final @NonNull UUID accountId);

  @Transactional
  @NonNull
  ClientAccountDto closeClientAccount(final @NonNull Long clientId, final @NonNull UUID accountId);

  @Transactional(readOnly = true)
  @NonNull
  Collection<@NonNull ClientAccountDto> getClientAccounts(final @NonNull Long clientId);

  @Transactional
  @NonNull
  ClientAccountDto createClientAccount(final @NonNull Long clientId, final @NonNull NewAccountDto newAccountDto);

}
