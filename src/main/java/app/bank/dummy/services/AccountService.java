package app.bank.dummy.services;

import app.bank.dummy.dtos.AccountDto;
import app.bank.dummy.dtos.ClientDto;
import app.bank.dummy.dtos.CurrencyDto;
import app.bank.dummy.dtos.NewAccountDto;
import jakarta.validation.constraints.NotNull;
import java.util.Collection;
import java.util.UUID;
import org.springframework.transaction.annotation.Transactional;

public interface AccountService {

  @Transactional(readOnly = true)
  @NotNull
  AccountDto getAccountById(final @NotNull UUID id);

  @Transactional(readOnly = true)
  @NotNull
  Collection<@NotNull AccountDto> getAllAccounts();

  @Transactional(readOnly = true)
  @NotNull
  Collection<@NotNull AccountDto> getAllAccountsByClientId(final @NotNull Long clientId);

  @Transactional(readOnly = true)
  @NotNull
  ClientDto getClientByAccountId(final @NotNull UUID id);

  @Transactional(readOnly = true)
  @NotNull
  CurrencyDto getCurrencyByAccountId(final @NotNull UUID id);

  @Transactional
  @NotNull
  AccountDto createAccount(final @NotNull NewAccountDto newAccountDto, final @NotNull Long clientId);

  @Transactional
  @NotNull
  AccountDto closeAccount(final @NotNull UUID id);

}
