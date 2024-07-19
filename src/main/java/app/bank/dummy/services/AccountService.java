package app.bank.dummy.services;

import app.bank.dummy.dtos.AccountDto;
import app.bank.dummy.dtos.ClientAccountDto;
import app.bank.dummy.dtos.NewAccountDto;
import jakarta.validation.constraints.NotNull;
import java.util.Collection;
import java.util.UUID;
import org.springframework.transaction.annotation.Transactional;

public interface AccountService {

  @Transactional(readOnly = true)
  @NotNull
  AccountDto getAccount(final @NotNull UUID accountId);

  @Transactional(readOnly = true)
  @NotNull
  Collection<@NotNull AccountDto> getAccounts();

  @Transactional(readOnly = true)
  @NotNull
  ClientAccountDto getClientAccount(final @NotNull Long clientId, final @NotNull UUID accountId);

  @Transactional
  @NotNull
  ClientAccountDto closeClientAccount(final @NotNull Long clientId, final @NotNull UUID accountId);

  @Transactional(readOnly = true)
  @NotNull
  Collection<@NotNull ClientAccountDto> getClientAccounts(final @NotNull Long clientId);

  @Transactional
  @NotNull
  ClientAccountDto createClientAccount(final @NotNull Long clientId, final @NotNull NewAccountDto newAccountDto);

}
