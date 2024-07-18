package app.bank.dummy.services;

import app.bank.dummy.dtos.AccountDto;
import app.bank.dummy.dtos.NewAccountDto;
import jakarta.validation.constraints.NotNull;
import java.util.Collection;
import java.util.UUID;
import org.springframework.transaction.annotation.Transactional;

public interface AccountService {

  @Transactional(readOnly = true)
  @NotNull
  AccountDto getAccount(final @NotNull UUID id);

  @Transactional(readOnly = true)
  @NotNull
  Collection<@NotNull AccountDto> getOpenAccounts();

  @Transactional
  @NotNull
  AccountDto closeAccount(final @NotNull UUID id);

  @Transactional(readOnly = true)
  @NotNull
  Collection<@NotNull AccountDto> getAccountsByClientId(final @NotNull Long clientId);

  @Transactional
  @NotNull
  AccountDto createAccount(final @NotNull Long clientId, final @NotNull NewAccountDto newAccountDto);

}
