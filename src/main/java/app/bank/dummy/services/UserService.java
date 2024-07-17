package app.bank.dummy.services;

import app.bank.dummy.dtos.AccountDto;
import jakarta.validation.constraints.NotNull;
import org.springframework.transaction.annotation.Transactional;

public interface UserService {

  @Transactional(readOnly = true)
  @NotNull
  AccountDto getAccountByUserId(final @NotNull Long id);

}
