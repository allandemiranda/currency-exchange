package app.bank.dummy.providers;

import app.bank.dummy.dtos.ClientTransactionDto;
import app.bank.dummy.dtos.NewTransactionDto;
import app.bank.dummy.dtos.TransactionDto;
import app.bank.dummy.entities.Account;
import app.bank.dummy.entities.Client;
import app.bank.dummy.entities.ClientInfo;
import app.bank.dummy.entities.Transaction;
import app.bank.dummy.entities.TransactionCreditInfo;
import app.bank.dummy.entities.TransactionDebitInfo;
import app.bank.dummy.enums.AccountStatus;
import app.bank.dummy.enums.ClientStatus;
import app.bank.dummy.enums.Currency;
import app.bank.dummy.enums.TransactionType;
import app.bank.dummy.exceptions.AccountCloseException;
import app.bank.dummy.exceptions.AccountFondsInsuffisantException;
import app.bank.dummy.exceptions.AccountNotFoundException;
import app.bank.dummy.exceptions.ClientDeactivateException;
import app.bank.dummy.exceptions.TransactionNotFoundException;
import app.bank.dummy.mappers.TransactionMapper;
import app.bank.dummy.repositories.AccountRepository;
import app.bank.dummy.repositories.ClientRepository;
import app.bank.dummy.repositories.TransactionRepository;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.function.Executable;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.util.SerializationUtils;

@ExtendWith(MockitoExtension.class)
class TransactionProviderTest {

  @Mock
  private TransactionRepository transactionRepository;
  @Mock
  private TransactionMapper transactionMapper;
  @Mock
  private AccountRepository accountRepository;
  @Mock
  private ClientRepository clientRepository;
  @InjectMocks
  private TransactionProvider transactionProvider;

  @Test
  void testGetTransactionsShouldReturnTransactionDtoCollection() {
    //given
    final Transaction transaction = Mockito.mock(Transaction.class);
    final TransactionDto transactionDto = Mockito.mock(TransactionDto.class);
    final List<Transaction> transactions = List.of(transaction);
    final List<TransactionDto> transactionDtos = List.of(transactionDto);
    //when
    Mockito.when(transactionRepository.findAll()).thenReturn(transactions);
    Mockito.when(transactionMapper.toDto(transaction)).thenReturn(transactionDto);
    final Collection<TransactionDto> result = transactionProvider.getTransactions();
    //then
    Assertions.assertEquals(transactionDtos, result);
    Mockito.verify(transactionMapper).toDto(transaction);
  }

  @Test
  void testGetClientAccountTransactionsShouldReturnClientTransactionDtoCollection() {
    //given
    final Long clientId = 1L;
    final Client client = Mockito.mock(Client.class);
    final ClientInfo clientInfo = Mockito.mock(ClientInfo.class);
    final UUID accountId = UUID.randomUUID();
    final Account account = Mockito.mock(Account.class);
    final List<Account> accounts = List.of(account);
    final Transaction debitTransaction = Mockito.mock(Transaction.class);
    final List<Transaction> debitTransactions = List.of(debitTransaction);
    final TransactionDebitInfo debitDebitInfo = Mockito.mock(TransactionDebitInfo.class);
    final TransactionCreditInfo debitCreditInfo = Mockito.mock(TransactionCreditInfo.class);
    final Account debitCreditInfoAccount = Mockito.mock(Account.class);
    final double debitDebitInfoTmpBalance = 100d;
    final UUID debitCreditInfoAccountId = UUID.randomUUID();
    final ClientTransactionDto debitClientTransactionDto = Mockito.mock(ClientTransactionDto.class);
    final Transaction creditTransaction = Mockito.mock(Transaction.class);
    final List<Transaction> creditTransactions = List.of(creditTransaction);
    final TransactionCreditInfo creditCreditInfo = Mockito.mock(TransactionCreditInfo.class);
    final double creditCreditInfoTmpBalance = 200d;
    final TransactionDebitInfo creditDebitInfo = Mockito.mock(TransactionDebitInfo.class);
    final Account creditDebitInfoAccount = Mockito.mock(Account.class);
    final UUID creditDebitInfoAccountId = UUID.randomUUID();
    final ClientTransactionDto creditClientTransactionDto = Mockito.mock(ClientTransactionDto.class);
    //when
    Mockito.when(clientRepository.findById(clientId)).thenReturn(Optional.of(client));
    Mockito.when(client.getInfo()).thenReturn(clientInfo);
    Mockito.when(clientInfo.getStatus()).thenReturn(ClientStatus.ACTIVATE);
    Mockito.when(client.getAccounts()).thenReturn(Set.copyOf(accounts));
    Mockito.when(account.getId()).thenReturn(accountId);
    Mockito.when(account.getStatus()).thenReturn(AccountStatus.OPEN);
    Mockito.when(transactionRepository.findByDebitInfo_Account_Id(accountId)).thenReturn(debitTransactions);
    Mockito.when(debitTransaction.getDebitInfo()).thenReturn(debitDebitInfo);
    Mockito.when(debitTransaction.getCreditInfo()).thenReturn(debitCreditInfo);
    Mockito.when(debitCreditInfo.getAccount()).thenReturn(debitCreditInfoAccount);
    Mockito.when(debitDebitInfo.getTmpBalance()).thenReturn(debitDebitInfoTmpBalance);
    Mockito.when(debitCreditInfoAccount.getId()).thenReturn(debitCreditInfoAccountId);
    Mockito.when(transactionMapper.toDto(debitTransaction, debitDebitInfoTmpBalance, TransactionType.DEBIT, debitCreditInfoAccountId)).thenReturn(debitClientTransactionDto);
    Mockito.when(debitClientTransactionDto.dataTime()).thenReturn(LocalDateTime.MAX);
    Mockito.when(transactionRepository.findByCreditInfo_Account_Id(accountId)).thenReturn(creditTransactions);
    Mockito.when(creditTransaction.getCreditInfo()).thenReturn(creditCreditInfo);
    Mockito.when(creditCreditInfo.getTmpBalance()).thenReturn(creditCreditInfoTmpBalance);
    Mockito.when(creditTransaction.getDebitInfo()).thenReturn(creditDebitInfo);
    Mockito.when(creditDebitInfo.getAccount()).thenReturn(creditDebitInfoAccount);
    Mockito.when(creditDebitInfoAccount.getId()).thenReturn(creditDebitInfoAccountId);
    Mockito.when(transactionMapper.toDto(creditTransaction, creditCreditInfoTmpBalance, TransactionType.CREDIT, creditDebitInfoAccountId)).thenReturn(creditClientTransactionDto);
    Mockito.when(creditClientTransactionDto.dataTime()).thenReturn(LocalDateTime.MIN);
    final Collection<ClientTransactionDto> result = transactionProvider.getClientAccountTransactions(clientId, accountId);
    //then
    Assertions.assertEquals(2, result.size());
    Assertions.assertTrue(result.contains(debitClientTransactionDto));
    Assertions.assertTrue(result.contains(creditClientTransactionDto));
  }

  @Test
  void testGetShouldThrowClientDeactivateException() {
    //given
    final Long clientId = 1L;
    final Client client = Mockito.mock(Client.class);
    final ClientInfo clientInfo = Mockito.mock(ClientInfo.class);
    final NewTransactionDto newTransactionDto = Mockito.mock(NewTransactionDto.class);
    //when
    Mockito.when(clientRepository.findById(clientId)).thenReturn(Optional.of(client));
    Mockito.when(client.getInfo()).thenReturn(clientInfo);
    Mockito.when(clientInfo.getStatus()).thenReturn(ClientStatus.DEACTIVATE);
    Mockito.when(newTransactionDto.amount()).thenReturn(Mockito.anyDouble());
    final Executable executableTransactions = () -> transactionProvider.getClientAccountTransactions(clientId, UUID.randomUUID());
    final Executable executableTransaction = () -> transactionProvider.getClientAccountTransaction(clientId, UUID.randomUUID(), UUID.randomUUID());
    final Executable executableCreateClientAccount = () -> transactionProvider.createClientAccountTransaction(clientId, UUID.randomUUID(), newTransactionDto);
    //then
    Assertions.assertThrows(ClientDeactivateException.class, executableTransactions);
    Assertions.assertThrows(ClientDeactivateException.class, executableTransaction);
    Assertions.assertThrows(ClientDeactivateException.class, executableCreateClientAccount);
  }

  @Test
  void testGetShouldThrowAccountNotFoundException() {
    //given
    final Long clientId = 1L;
    final Client client = Mockito.mock(Client.class);
    final ClientInfo clientInfo = Mockito.mock(ClientInfo.class);
    final UUID accountId = UUID.fromString("27305125-a4ed-4973-a4e7-e12382e06943");
    final Account account = Mockito.mock(Account.class);
    final List<Account> accounts = List.of(account);
    final UUID accountIdDiff = UUID.fromString("ce80daf2-373b-4fa7-8fc4-d89920cc20b7");
    final NewTransactionDto newTransactionDto = Mockito.mock(NewTransactionDto.class);
    //when
    Mockito.when(clientRepository.findById(clientId)).thenReturn(Optional.of(client));
    Mockito.when(client.getInfo()).thenReturn(clientInfo);
    Mockito.when(clientInfo.getStatus()).thenReturn(ClientStatus.ACTIVATE);
    Mockito.when(client.getAccounts()).thenReturn(Set.copyOf(accounts));
    Mockito.when(account.getId()).thenReturn(accountId);
    Mockito.when(newTransactionDto.amount()).thenReturn(Mockito.anyDouble());
    final Executable executableTransactions = () -> transactionProvider.getClientAccountTransactions(clientId, accountIdDiff);
    final Executable executableTransaction = () -> transactionProvider.getClientAccountTransaction(clientId, accountIdDiff, UUID.randomUUID());
    final Executable executableCreateClientAccount = () -> transactionProvider.createClientAccountTransaction(clientId, UUID.randomUUID(), newTransactionDto);
    //then
    Assertions.assertThrows(AccountNotFoundException.class, executableTransactions);
    Assertions.assertThrows(AccountNotFoundException.class, executableTransaction);
    Assertions.assertThrows(AccountNotFoundException.class, executableCreateClientAccount);
  }

  @Test
  void testGetShouldThrowAccountCloseException() {
    //given
    final Long clientId = 1L;
    final Client client = Mockito.mock(Client.class);
    final ClientInfo clientInfo = Mockito.mock(ClientInfo.class);
    final UUID accountId = UUID.randomUUID();
    final Account account = Mockito.mock(Account.class);
    final List<Account> accounts = List.of(account);
    final NewTransactionDto newTransactionDto = Mockito.mock(NewTransactionDto.class);
    //when
    Mockito.when(clientRepository.findById(clientId)).thenReturn(Optional.of(client));
    Mockito.when(client.getInfo()).thenReturn(clientInfo);
    Mockito.when(clientInfo.getStatus()).thenReturn(ClientStatus.ACTIVATE);
    Mockito.when(client.getAccounts()).thenReturn(Set.copyOf(accounts));
    Mockito.when(account.getId()).thenReturn(accountId);
    Mockito.when(account.getStatus()).thenReturn(AccountStatus.CLOSE);
    Mockito.when(newTransactionDto.amount()).thenReturn(Mockito.anyDouble());
    final Executable executableTransactions = () -> transactionProvider.getClientAccountTransactions(clientId, accountId);
    final Executable executableTransaction = () -> transactionProvider.getClientAccountTransaction(clientId, accountId, UUID.randomUUID());
    final Executable executableCreateClientAccount = () -> transactionProvider.createClientAccountTransaction(clientId, UUID.randomUUID(), newTransactionDto);
    //then
    Assertions.assertThrows(AccountCloseException.class, executableTransactions);
    Assertions.assertThrows(AccountCloseException.class, executableTransaction);
    Assertions.assertThrows(AccountNotFoundException.class, executableCreateClientAccount);
  }

  @Test
  void testGetClientAccountTransactionShouldReturnDebitClientTransactionDto() {
    //given
    final Long clientId = 1L;
    final Client client = Mockito.mock(Client.class);
    final ClientInfo clientInfo = Mockito.mock(ClientInfo.class);
    final UUID clientAccountId = UUID.randomUUID();
    final Account clientAccount = Mockito.mock(Account.class);
    final List<Account> clientAccounts = List.of(clientAccount);
    final UUID clientAccountTransactionId = UUID.randomUUID();
    final Transaction clientAccountTransaction = Mockito.mock(Transaction.class);
    final TransactionDebitInfo clientAccountTransactionDebitInfo = Mockito.mock(TransactionDebitInfo.class);
    final TransactionCreditInfo clientAccountTransactionCreditInfo = Mockito.mock(TransactionCreditInfo.class);
    final ClientTransactionDto clientTransactionDto = Mockito.mock(ClientTransactionDto.class);
    final double debitTmpBalance = 100d;
    final Account creditAccount = Mockito.mock(Account.class);
    final UUID creditAccountId = UUID.randomUUID();
    //when
    Mockito.when(clientRepository.findById(clientId)).thenReturn(Optional.of(client));
    Mockito.when(client.getInfo()).thenReturn(clientInfo);
    Mockito.when(clientInfo.getStatus()).thenReturn(ClientStatus.ACTIVATE);
    Mockito.when(client.getAccounts()).thenReturn(Set.copyOf(clientAccounts));
    Mockito.when(clientAccount.getId()).thenReturn(clientAccountId);
    Mockito.when(clientAccount.getStatus()).thenReturn(AccountStatus.OPEN);
    Mockito.when(clientAccountTransaction.getDebitInfo()).thenReturn(clientAccountTransactionDebitInfo);
    Mockito.when(clientAccountTransaction.getCreditInfo()).thenReturn(clientAccountTransactionCreditInfo);
    Mockito.when(transactionRepository.findById(clientAccountTransactionId)).thenReturn(Optional.of(clientAccountTransaction));
    Mockito.when(clientAccountTransactionDebitInfo.getAccount()).thenReturn(clientAccount);
    Mockito.when(clientAccountTransactionDebitInfo.getTmpBalance()).thenReturn(debitTmpBalance);
    Mockito.when(creditAccount.getId()).thenReturn(creditAccountId);
    Mockito.when(clientAccountTransactionCreditInfo.getAccount()).thenReturn(creditAccount);
    Mockito.when(transactionMapper.toDto(clientAccountTransaction, debitTmpBalance, TransactionType.DEBIT, creditAccountId)).thenReturn(clientTransactionDto);
    final ClientTransactionDto result = transactionProvider.getClientAccountTransaction(clientId, clientAccountId, clientAccountTransactionId);
    //then
    Assertions.assertEquals(clientTransactionDto, result);
  }

  @Test
  void testGetClientAccountTransactionShouldReturnCreditClientTransactionDto() {
    //given
    final Long clientId = 1L;
    final Client client = Mockito.mock(Client.class);
    final ClientInfo clientInfo = Mockito.mock(ClientInfo.class);
    final UUID clientAccountId = UUID.randomUUID();
    final Account clientAccount = Mockito.mock(Account.class);
    final List<Account> clientAccounts = List.of(clientAccount);
    final UUID clientAccountTransactionId = UUID.randomUUID();
    final Transaction clientAccountTransaction = Mockito.mock(Transaction.class);
    final TransactionDebitInfo clientAccountTransactionDebitInfo = Mockito.mock(TransactionDebitInfo.class);
    final TransactionCreditInfo clientAccountTransactionCreditInfo = Mockito.mock(TransactionCreditInfo.class);
    final ClientTransactionDto clientTransactionDto = Mockito.mock(ClientTransactionDto.class);
    final double creditTmpBalance = 100d;
    final Account debitAccount = Mockito.mock(Account.class);
    final UUID debitAccountId = UUID.randomUUID();
    //when
    Mockito.when(clientRepository.findById(clientId)).thenReturn(Optional.of(client));
    Mockito.when(client.getInfo()).thenReturn(clientInfo);
    Mockito.when(clientInfo.getStatus()).thenReturn(ClientStatus.ACTIVATE);
    Mockito.when(client.getAccounts()).thenReturn(Set.copyOf(clientAccounts));
    Mockito.when(clientAccount.getId()).thenReturn(clientAccountId);
    Mockito.when(clientAccount.getStatus()).thenReturn(AccountStatus.OPEN);
    Mockito.when(clientAccountTransaction.getDebitInfo()).thenReturn(clientAccountTransactionDebitInfo);
    Mockito.when(clientAccountTransaction.getCreditInfo()).thenReturn(clientAccountTransactionCreditInfo);
    Mockito.when(transactionRepository.findById(clientAccountTransactionId)).thenReturn(Optional.of(clientAccountTransaction));
    Mockito.when(clientAccountTransactionCreditInfo.getAccount()).thenReturn(clientAccount);
    Mockito.when(clientAccountTransactionCreditInfo.getTmpBalance()).thenReturn(creditTmpBalance);
    Mockito.when(debitAccount.getId()).thenReturn(debitAccountId);
    Mockito.when(clientAccountTransactionDebitInfo.getAccount()).thenReturn(debitAccount);
    Mockito.when(transactionMapper.toDto(clientAccountTransaction, creditTmpBalance, TransactionType.CREDIT, debitAccountId)).thenReturn(clientTransactionDto);
    final ClientTransactionDto result = transactionProvider.getClientAccountTransaction(clientId, clientAccountId, clientAccountTransactionId);
    //then
    Assertions.assertEquals(clientTransactionDto, result);
  }

  @Test
  void testGetClientAccountTransactionShouldThrowTransactionNotFoundException() {
    //given
    final Long clientId = 1L;
    final Client client = Mockito.mock(Client.class);
    final ClientInfo clientInfo = Mockito.mock(ClientInfo.class);
    final UUID clientAccountId = UUID.randomUUID();
    final Account clientAccount = Mockito.mock(Account.class);
    final List<Account> clientAccounts = List.of(clientAccount);
    final UUID clientAccountTransactionId = UUID.randomUUID();
    final Transaction clientAccountTransaction = Mockito.mock(Transaction.class);
    final TransactionDebitInfo clientAccountTransactionDebitInfo = Mockito.mock(TransactionDebitInfo.class);
    final TransactionCreditInfo clientAccountTransactionCreditInfo = Mockito.mock(TransactionCreditInfo.class);
    final Account fakeAccount = Mockito.mock(Account.class);
    //when
    Mockito.when(clientRepository.findById(clientId)).thenReturn(Optional.of(client));
    Mockito.when(client.getInfo()).thenReturn(clientInfo);
    Mockito.when(clientInfo.getStatus()).thenReturn(ClientStatus.ACTIVATE);
    Mockito.when(client.getAccounts()).thenReturn(Set.copyOf(clientAccounts));
    Mockito.when(clientAccount.getId()).thenReturn(clientAccountId);
    Mockito.when(clientAccount.getStatus()).thenReturn(AccountStatus.OPEN);
    Mockito.when(clientAccountTransaction.getDebitInfo()).thenReturn(clientAccountTransactionDebitInfo);
    Mockito.when(clientAccountTransaction.getCreditInfo()).thenReturn(clientAccountTransactionCreditInfo);
    Mockito.when(transactionRepository.findById(clientAccountTransactionId)).thenReturn(Optional.of(clientAccountTransaction));
    Mockito.when(clientAccountTransactionCreditInfo.getAccount()).thenReturn(fakeAccount);
    final Executable executable = () -> transactionProvider.getClientAccountTransaction(clientId, clientAccountId, clientAccountTransactionId);
    //then
    Assertions.assertThrows(TransactionNotFoundException.class, executable);
  }

  @Test
  void testCreateClientAccountTransactionShouldThrowAccountFondsInsuffisantException() {
    //given
    final NewTransactionDto newTransactionDto = Mockito.mock(NewTransactionDto.class);
    final double amount = 300d;
    final Long clientId = 1L;
    final Client client = Mockito.mock(Client.class);
    final ClientInfo clientInfo = Mockito.mock(ClientInfo.class);
    final UUID debitAccountId = UUID.randomUUID();
    final Account debitAccount = Mockito.spy(Account.class);
    final List<Account> debitAccounts = List.of(debitAccount);
    final double balanceDebitAccount = 200d;
    //when
    Mockito.when(newTransactionDto.amount()).thenReturn(amount);
    Mockito.when(clientRepository.findById(clientId)).thenReturn(Optional.of(client));
    Mockito.when(client.getInfo()).thenReturn(clientInfo);
    Mockito.when(clientInfo.getStatus()).thenReturn(ClientStatus.ACTIVATE);
    Mockito.when(client.getAccounts()).thenReturn(Set.copyOf(debitAccounts));
    Mockito.when(debitAccount.getId()).thenReturn(debitAccountId);
    Mockito.when(debitAccount.getStatus()).thenReturn(AccountStatus.OPEN);
    Mockito.when(debitAccount.getBalance()).thenReturn(balanceDebitAccount);
    final Executable executableCreateClientAccount = () -> transactionProvider.createClientAccountTransaction(clientId, debitAccountId, newTransactionDto);
    //then
    Assertions.assertThrows(AccountFondsInsuffisantException.class, executableCreateClientAccount);
  }

  @Test
  void testCreateClientAccountTransactionShouldThrowAccountNotFoundException() {
    //given
    final double amount = 100d;
    final Long clientId = 1L;
    final Client client = Mockito.mock(Client.class);
    final ClientInfo clientInfo = Mockito.mock(ClientInfo.class);
    final UUID debitAccountId = UUID.randomUUID();
    final Account debitAccount = Mockito.spy(Account.class);
    final List<Account> debitAccounts = List.of(debitAccount);
    final double balanceDebitAccount = 200d;
    final NewTransactionDto newTransactionDto = Mockito.mock(NewTransactionDto.class);
    final UUID creditAccountId = UUID.randomUUID();
    //when
    Mockito.when(newTransactionDto.amount()).thenReturn(amount);
    Mockito.when(newTransactionDto.creditAccountId()).thenReturn(creditAccountId);
    Mockito.when(accountRepository.findById(creditAccountId)).thenReturn(Optional.empty());
    Mockito.when(clientRepository.findById(clientId)).thenReturn(Optional.of(client));
    Mockito.when(client.getInfo()).thenReturn(clientInfo);
    Mockito.when(clientInfo.getStatus()).thenReturn(ClientStatus.ACTIVATE);
    Mockito.when(client.getAccounts()).thenReturn(Set.copyOf(debitAccounts));
    Mockito.when(debitAccount.getId()).thenReturn(debitAccountId);
    Mockito.when(debitAccount.getStatus()).thenReturn(AccountStatus.OPEN);
    Mockito.when(debitAccount.getBalance()).thenReturn(balanceDebitAccount);
    Mockito.when(accountRepository.save(debitAccount)).thenReturn(debitAccount);
    final Executable executableCreateClientAccount = () -> transactionProvider.createClientAccountTransaction(clientId, debitAccountId, newTransactionDto);
    //then
    Assertions.assertThrows(AccountNotFoundException.class, executableCreateClientAccount);
  }

  @Test
  void testCreateClientAccountTransactionShouldThrowAccountCloseException() {
    //given
    final double amount = 100d;
    final Long clientId = 1L;
    final Client client = Mockito.mock(Client.class);
    final ClientInfo clientInfo = Mockito.mock(ClientInfo.class);
    final UUID debitAccountId = UUID.randomUUID();
    final Account debitAccount = Mockito.spy(Account.class);
    final List<Account> debitAccounts = List.of(debitAccount);
    final double balanceDebitAccount = 200d;
    final NewTransactionDto newTransactionDto = Mockito.mock(NewTransactionDto.class);
    final UUID creditAccountId = UUID.randomUUID();
    final Account creditAccount = Mockito.spy(Account.class);
    //when
    Mockito.when(newTransactionDto.amount()).thenReturn(amount);
    Mockito.when(newTransactionDto.creditAccountId()).thenReturn(creditAccountId);
    Mockito.when(accountRepository.findById(creditAccountId)).thenReturn(Optional.of(creditAccount));
    Mockito.when(creditAccount.getStatus()).thenReturn(AccountStatus.CLOSE);
    Mockito.when(clientRepository.findById(clientId)).thenReturn(Optional.of(client));
    Mockito.when(client.getInfo()).thenReturn(clientInfo);
    Mockito.when(clientInfo.getStatus()).thenReturn(ClientStatus.ACTIVATE);
    Mockito.when(client.getAccounts()).thenReturn(Set.copyOf(debitAccounts));
    Mockito.when(debitAccount.getId()).thenReturn(debitAccountId);
    Mockito.when(debitAccount.getStatus()).thenReturn(AccountStatus.OPEN);
    Mockito.when(debitAccount.getBalance()).thenReturn(balanceDebitAccount);
    Mockito.when(accountRepository.save(debitAccount)).thenReturn(debitAccount);
    final Executable executableCreateClientAccount = () -> transactionProvider.createClientAccountTransaction(clientId, debitAccountId, newTransactionDto);
    //then
    Assertions.assertThrows(AccountCloseException.class, executableCreateClientAccount);
  }

  @ParameterizedTest
  @ValueSource(doubles = {100d, 200d})
  void testCreateClientAccountTransactionShouldReturnClientTransactionDto(double amount) {
    //given
    final NewTransactionDto newTransactionDto = Mockito.mock(NewTransactionDto.class);
    final Long clientId = 1L;
    final Client client = Mockito.mock(Client.class);
    final ClientInfo clientInfo = Mockito.mock(ClientInfo.class);
    final UUID debitAccountId = UUID.randomUUID();
    final Account debitAccount = Mockito.spy(Account.class);
    final List<Account> debitAccounts = List.of(debitAccount);
    final double balanceDebitAccount = 200d;
    final Account creditAccount = Mockito.spy(Account.class);
    final UUID creditAccountId = UUID.randomUUID();
    final double creditAccountBalance = 50d;
    final Transaction transaction = Mockito.mock(Transaction.class);
    final ClientTransactionDto clientTransactionDto = Mockito.mock(ClientTransactionDto.class);
    final Currency debitCurrency = Mockito.mock(Currency.class);
    final Currency creditCurrency = Mockito.mock(Currency.class);
    //when
    Mockito.when(debitAccount.getCurrency()).thenReturn(debitCurrency);
    Mockito.when(creditAccount.getCurrency()).thenReturn(creditCurrency);
    Mockito.when(newTransactionDto.amount()).thenReturn(amount);
    Mockito.when(clientRepository.findById(clientId)).thenReturn(Optional.of(client));
    Mockito.when(client.getInfo()).thenReturn(clientInfo);
    Mockito.when(clientInfo.getStatus()).thenReturn(ClientStatus.ACTIVATE);
    Mockito.when(client.getAccounts()).thenReturn(Set.copyOf(debitAccounts));
    Mockito.when(debitAccount.getId()).thenReturn(debitAccountId);
    Mockito.when(debitAccount.getStatus()).thenReturn(AccountStatus.OPEN);
    Mockito.when(debitAccount.getBalance()).thenReturn(balanceDebitAccount);
    Mockito.when(accountRepository.save(debitAccount)).thenReturn(debitAccount);
    Mockito.when(newTransactionDto.creditAccountId()).thenReturn(creditAccountId);
    Mockito.when(creditAccount.getBalance()).thenReturn(creditAccountBalance);
    Mockito.when(accountRepository.findById(creditAccountId)).thenReturn(Optional.of(creditAccount));
    Mockito.when(creditAccount.getStatus()).thenReturn(AccountStatus.OPEN);
    Mockito.when(creditAccount.getId()).thenReturn(creditAccountId);
    Mockito.when(accountRepository.save(creditAccount)).thenReturn(creditAccount);
    Mockito.when(transactionRepository.save(Mockito.any())).thenReturn(transaction);
    Mockito.when(transactionMapper.toDto(transaction, debitAccount.getBalance(), TransactionType.DEBIT, creditAccountId)).thenReturn(clientTransactionDto);
    final ClientTransactionDto result = transactionProvider.createClientAccountTransaction(clientId, debitAccountId, newTransactionDto);
    //then
    final Account creditAccountClone = SerializationUtils.clone(creditAccount);
    final Account debitAccountClone = SerializationUtils.clone(debitAccount);
    Assertions.assertEquals(clientTransactionDto, result);
    Assertions.assertEquals(creditAccountBalance + amount, creditAccountClone.getBalance());
    Assertions.assertEquals(balanceDebitAccount - amount, debitAccountClone.getBalance());
  }
}