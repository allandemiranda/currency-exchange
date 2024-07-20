package app.bank.dummy.providers;

import app.bank.dummy.dtos.AccountDto;
import app.bank.dummy.dtos.ClientAccountDto;
import app.bank.dummy.dtos.NewAccountDto;
import app.bank.dummy.entities.Account;
import app.bank.dummy.entities.Client;
import app.bank.dummy.enums.AccountStatus;
import app.bank.dummy.exceptions.AccountNotFoundException;
import app.bank.dummy.exceptions.ClientNotFoundException;
import app.bank.dummy.mappers.AccountMapper;
import app.bank.dummy.repositories.AccountRepository;
import app.bank.dummy.repositories.ClientRepository;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Stream;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.function.Executable;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class AccountProviderTest {

  @Mock
  private AccountRepository accountRepository;
  @Mock
  private ClientRepository clientRepository;
  @Mock
  private AccountMapper accountMapper;
  @InjectMocks
  private AccountProvider accountProvider;

  @Test
  void testGetAccountShouldReturnAccountDto() {
    //given
    final UUID accountId = UUID.randomUUID();
    final Account account = Mockito.mock(Account.class);
    final AccountDto accountDto = Mockito.mock(AccountDto.class);
    //when
    Mockito.when(accountRepository.findById(accountId)).thenReturn(Optional.of(account));
    Mockito.when(accountMapper.toDto(account)).thenReturn(accountDto);
    final AccountDto result = accountProvider.getAccount(accountId);
    //then
    Assertions.assertEquals(accountDto, result);
  }

  @Test
  void testGetAccountShouldThrowAccountNotFoundException() {
    //given
    final UUID accountId = UUID.randomUUID();
    //when
    Mockito.when(accountRepository.findById(accountId)).thenReturn(Optional.empty());
    final Executable executable = () -> accountProvider.getAccount(accountId);
    //then
    Assertions.assertThrows(AccountNotFoundException.class, executable);
    Mockito.verify(accountRepository).findById(accountId);
  }

  @Test
  void testGetAccountWithNullInputShouldThrowException() {
    //given
    //when
    final Executable executable = () -> accountProvider.getAccount(null);
    //then
    Assertions.assertThrows(NullPointerException.class, executable);
  }

  @Test
  void testGetClientAccountShouldReturnClientAccountDto() {
    //given
    final Long clientId = 1L;
    final UUID accountId = UUID.randomUUID();
    final Client client = Mockito.mock(Client.class);
    final Account account = Mockito.mock(Account.class);
    final ClientAccountDto clientAccountDto = Mockito.mock(ClientAccountDto.class);

    Mockito.when(clientRepository.findById(clientId)).thenReturn(Optional.of(client));
    Mockito.when(client.getAccounts()).thenReturn(Set.of(account));
    Mockito.when(account.getId()).thenReturn(accountId);
    Mockito.when(accountMapper.toDtoClient(account)).thenReturn(clientAccountDto);
    final ClientAccountDto result = accountProvider.getClientAccount(clientId, accountId);
    //then
    Assertions.assertEquals(clientAccountDto, result);
    Mockito.verify(accountMapper).toDtoClient(account);
  }

  @Test
  void testGetClientAccountShouldThrowClientNotFoundException() {
    //given
    final Long clientId = 1L;
    final UUID accountId = UUID.randomUUID();
    //when
    Mockito.when(clientRepository.findById(clientId)).thenReturn(Optional.empty());
    final Executable executable = () -> accountProvider.getClientAccount(clientId, accountId);
    //then
    Assertions.assertThrows(ClientNotFoundException.class, executable);
    Mockito.verify(clientRepository).findById(clientId);
  }

  @Test
  void testGetClientAccountEmptyShouldThrowAccountNotFoundException() {
    //given
    final Long clientId = 1L;
    final UUID accountId = UUID.randomUUID();
    final Client client = Mockito.mock(Client.class);
    final Set<Account> accounts = Set.of();
    //when
    Mockito.when(clientRepository.findById(clientId)).thenReturn(Optional.of(client));
    Mockito.when(client.getAccounts()).thenReturn(accounts);
    final Executable executable = () -> accountProvider.getClientAccount(clientId, accountId);
    //then
    Assertions.assertThrows(AccountNotFoundException.class, executable);
    Mockito.verify(clientRepository).findById(clientId);
  }

  @Test
  void testGetClientAccountShouldThrowAccountNotFoundException() {
    //given
    final Long clientId = 1L;
    final UUID accountId = UUID.fromString("00ab631a-2d0c-4d59-88d1-6ba7acba2d55");
    final Client client = Mockito.mock(Client.class);
    final Account savedAccount = Mockito.mock(Account.class);
    final UUID savedAccountId = UUID.fromString("f25d2934-490f-4ea5-b7ca-d121d77e7cda");
    final Set<Account> accounts = Set.of(savedAccount);
    //when
    Mockito.when(clientRepository.findById(clientId)).thenReturn(Optional.of(client));
    Mockito.when(client.getAccounts()).thenReturn(accounts);
    Mockito.when(savedAccount.getId()).thenReturn(savedAccountId);
    final Executable executable = () -> accountProvider.getClientAccount(clientId, accountId);
    //then
    Assertions.assertThrows(AccountNotFoundException.class, executable);
    Mockito.verify(clientRepository).findById(clientId);
  }

  @Test
  void testGetClientAccountsWithNullInputsShouldThrowException() {
    //given
    //when
    final Executable executable = () -> accountProvider.getClientAccount(null, UUID.randomUUID());
    final Executable executable1 = () -> accountProvider.getClientAccount(1L, null);
    final Executable executable2 = () -> accountProvider.getClientAccount(null, null);
    //then
    Stream.of(executable, executable1, executable2).forEach(exe -> Assertions.assertThrows(NullPointerException.class, exe));
  }

  @Test
  void testGetAccountsShouldReturnAccountDtoCollection() {
    //given
    final Account account = Mockito.mock(Account.class);
    final AccountDto accountDto = Mockito.mock(AccountDto.class);
    final List<Account> accounts = List.of(account);
    final List<AccountDto> accountDtos = List.of(accountDto);
    //when
    Mockito.when(accountRepository.findAll()).thenReturn(accounts);
    Mockito.when(accountMapper.toDto(account)).thenReturn(accountDto);
    final Collection<AccountDto> result = accountProvider.getAccounts();
    //then
    Assertions.assertEquals(accountDtos, result);
    Mockito.verify(accountMapper).toDto(account);
  }

  @Test
  void testCloseClientAccountShouldReturnClosedClientAccountDto() {
    //given
    final long clientId = 1L;
    final Client client = Mockito.mock(Client.class);
    final Account account = Mockito.spy(Account.class);
    final ClientAccountDto clientAccountDto = Mockito.mock(ClientAccountDto.class);
    final UUID accountId = UUID.randomUUID();
    //when
    Mockito.when(clientRepository.findById(clientId)).thenReturn(Optional.of(client));
    Mockito.when(client.getAccounts()).thenReturn(Set.of(account));
    Mockito.when(account.getId()).thenReturn(accountId);
    Mockito.when(accountRepository.save(account)).thenReturn(account);
    Mockito.when(accountMapper.toDtoClient(account)).thenReturn(clientAccountDto);
    final ClientAccountDto result = accountProvider.closeClientAccount(clientId, accountId);
    //then
    Assertions.assertEquals(clientAccountDto, result);
    Mockito.verify(accountRepository).save(account);
    Mockito.verify(accountMapper).toDtoClient(account);
    Assertions.assertEquals(AccountStatus.CLOSE, account.getStatus());
  }

  @Test
  void testCloseClientAccountShouldThrowAccountNotFoundException() {
    //given
    final Long clientId = 1L;
    final UUID accountId = UUID.randomUUID();
    final Client client = Mockito.mock(Client.class);
    final Set<Account> accounts = Set.of();
    //when
    Mockito.when(clientRepository.findById(clientId)).thenReturn(Optional.of(client));
    Mockito.when(client.getAccounts()).thenReturn(accounts);
    final Executable executable = () -> accountProvider.closeClientAccount(clientId, accountId);
    //then
    Assertions.assertThrows(AccountNotFoundException.class, executable);
    Mockito.verify(clientRepository).findById(clientId);
  }

  @Test
  void testCloseClientAccountWithNullInputsShouldThrowException() {
    //given
    //when
    final Executable executable = () -> accountProvider.closeClientAccount(null, UUID.randomUUID());
    final Executable executable1 = () -> accountProvider.closeClientAccount(1L, null);
    final Executable executable2 = () -> accountProvider.closeClientAccount(null, null);
    //then
    Stream.of(executable, executable1, executable2).forEach(exe -> Assertions.assertThrows(NullPointerException.class, exe));
  }

  @Test
  void testGetClientAccountsShouldReturnClientAccountDtoCollection() {
    //given
    final Long clientId = 1L;
    final Client client = Mockito.mock(Client.class);
    final Account account = Mockito.mock(Account.class);
    final Set<Account> accounts = Set.of(account);
    final ClientAccountDto clientAccountDto = Mockito.mock(ClientAccountDto.class);
    //when
    Mockito.when(clientRepository.findById(clientId)).thenReturn(Optional.of(client));
    Mockito.when(client.getAccounts()).thenReturn(accounts);
    Mockito.when(accountMapper.toDtoClient(account)).thenReturn(clientAccountDto);
    final Collection<ClientAccountDto> result = accountProvider.getClientAccounts(clientId);
    //then
    Assertions.assertEquals(List.of(clientAccountDto), result);
    Mockito.verify(accountMapper).toDtoClient(account);
  }

  @Test
  void testGetClientAccountsWithNullInputShouldThrowException() {
    //given
    //when
    final Executable executable = () -> accountProvider.getClientAccounts(null);
    //then
    Assertions.assertThrows(NullPointerException.class, executable);
  }

  @Test
  void testCreateClientAccountShouldReturnClientAccountDto() {
    //given
    final Long clientId = 1L;
    final NewAccountDto newAccountDto = Mockito.mock(NewAccountDto.class);
    final Client client = Mockito.mock(Client.class);
    final Account account = Mockito.spy(Account.class);
    final ClientAccountDto clientAccountDto = Mockito.mock(ClientAccountDto.class);
    //when
    Mockito.when(clientRepository.findById(clientId)).thenReturn(Optional.of(client));
    Mockito.when(accountMapper.toEntity(newAccountDto)).thenReturn(account);
    Mockito.when(accountRepository.save(account)).thenReturn(account);
    Mockito.when(accountMapper.toDtoClient(account)).thenReturn(clientAccountDto);
    final ClientAccountDto result = accountProvider.createClientAccount(clientId, newAccountDto);
    //then
    Assertions.assertEquals(clientAccountDto, result);
    Mockito.verify(clientRepository).findById(clientId);
    Mockito.verify(accountMapper).toEntity(newAccountDto);
    Mockito.verify(accountRepository).save(account);
    Mockito.verify(accountMapper).toDtoClient(account);
    Assertions.assertEquals(client, account.getClient());
    Assertions.assertEquals(AccountStatus.OPEN, account.getStatus());
  }

  @Test
  void testCreateClientAccountShouldThrowClientNotFoundException() {
    //given
    final Long clientId = 1L;
    final NewAccountDto newAccountDto = Mockito.mock(NewAccountDto.class);
    //when
    Mockito.when(clientRepository.findById(clientId)).thenReturn(Optional.empty());
    final Executable executable = () -> accountProvider.createClientAccount(clientId, newAccountDto);
    //then
    Assertions.assertThrows(ClientNotFoundException.class, executable);
    Mockito.verify(clientRepository).findById(clientId);
  }

  @Test
  void testCreateClientAccountAccountWithNullInputsShouldThrowException() {
    //given
    final NewAccountDto newAccountDto = Mockito.mock(NewAccountDto.class);
    //when
    final Executable executable = () -> accountProvider.createClientAccount(null, newAccountDto);
    final Executable executable1 = () -> accountProvider.closeClientAccount(1L, null);
    final Executable executable2 = () -> accountProvider.closeClientAccount(null, null);
    //then
    Stream.of(executable, executable1, executable2).forEach(exe -> Assertions.assertThrows(NullPointerException.class, exe));
  }
}