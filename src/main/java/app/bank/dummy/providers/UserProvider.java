package app.bank.dummy.providers;

import app.bank.dummy.dtos.AccountDto;
import app.bank.dummy.exceptions.UserNotFoundException;
import app.bank.dummy.mappers.AccountMapper;
import app.bank.dummy.models.Account;
import app.bank.dummy.models.User;
import app.bank.dummy.repositories.UserRepository;
import app.bank.dummy.services.UserService;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Getter(AccessLevel.PRIVATE)
public class UserProvider implements UserService {

  private final UserRepository userRepository;
  private final AccountMapper accountMapper;

  @Override
  public AccountDto getAccountByUserId(final Long id) {
    final User user = this.getUserRepository().findById(id).orElseThrow(() -> new UserNotFoundException(id));
    final Account account = user.getAccount();
    return this.getAccountMapper().toDto(account);
  }
}
