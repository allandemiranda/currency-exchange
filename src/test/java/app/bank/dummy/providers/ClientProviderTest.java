package app.bank.dummy.providers;

import app.bank.dummy.dtos.ClientDto;
import app.bank.dummy.dtos.NewClientDto;
import app.bank.dummy.dtos.UpdateClientDto;
import app.bank.dummy.entities.Account;
import app.bank.dummy.entities.Client;
import app.bank.dummy.entities.ClientInfo;
import app.bank.dummy.enums.AccountStatus;
import app.bank.dummy.enums.ClientStatus;
import app.bank.dummy.exceptions.AccountNotFoundException;
import app.bank.dummy.exceptions.ClientNotFoundException;
import app.bank.dummy.mappers.ClientMapper;
import app.bank.dummy.repositories.AccountRepository;
import app.bank.dummy.repositories.ClientRepository;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.function.Executable;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class ClientProviderTest {

  @Mock
  private ClientRepository clientRepository;
  @Mock
  private AccountRepository accountRepository;
  @Mock
  private ClientMapper clientMapper;
  @InjectMocks
  private ClientProvider clientProvider;

  @Test
  void testGetClientShouldReturnClientDto() {
    //given
    final Long clientId = 1L;
    final Client client = Mockito.mock(Client.class);
    final ClientDto clientDto = Mockito.mock(ClientDto.class);
    //when
    Mockito.when(clientRepository.findById(clientId)).thenReturn(Optional.of(client));
    Mockito.when(clientMapper.toDto(client)).thenReturn(clientDto);
    final ClientDto result = clientProvider.getClient(clientId);
    //then
    Assertions.assertEquals(clientDto, result);
    Mockito.verify(clientRepository).findById(clientId);
    Mockito.verify(clientMapper).toDto(client);
  }

  @Test
  void testGetClientShouldThrowClientNotFoundException() {
    //given
    final Long clientId = 1L;
    //when
    Mockito.when(clientRepository.findById(clientId)).thenReturn(Optional.empty());
    final Executable executable = () -> clientProvider.getClient(clientId);
    //then
    Assertions.assertThrows(ClientNotFoundException.class, executable);
    Mockito.verify(clientRepository).findById(clientId);
  }

  @Test
  void testGetClientsShouldReturnClientDtoCollection() {
    //given
    final Client client = Mockito.mock(Client.class);
    final ClientDto clientDto = Mockito.mock(ClientDto.class);
    final List<Client> clients = List.of(client);
    final List<ClientDto> clientDtos = List.of(clientDto);
    //when
    Mockito.when(clientRepository.findAll()).thenReturn(clients);
    Mockito.when(clientMapper.toDto(client)).thenReturn(clientDto);
    final Collection<ClientDto> result = clientProvider.getClients();
    //then
    Assertions.assertEquals(clientDtos, result);
    Mockito.verify(clientRepository).findAll();
    Mockito.verify(clientMapper).toDto(client);
  }

  @Test
  void testCreateClientShouldReturnClientDtoActivate() {
    //given
    final NewClientDto newClientDto = Mockito.mock(NewClientDto.class);
    final Client client = Mockito.mock(Client.class);
    final ClientDto clientDto = Mockito.mock(ClientDto.class);
    final ClientInfo clientInfo = Mockito.spy(ClientInfo.class);
    //when
    Mockito.when(client.getInfo()).thenReturn(clientInfo);
    Mockito.when(clientMapper.toEntity(newClientDto)).thenReturn(client);
    Mockito.when(clientRepository.save(client)).thenReturn(client);
    Mockito.when(clientMapper.toDto(client)).thenReturn(clientDto);
    final ClientDto result = clientProvider.createClient(newClientDto);
    //then
    Assertions.assertEquals(clientDto, result);
    Mockito.verify(clientMapper).toEntity(newClientDto);
    Mockito.verify(clientRepository).save(client);
    Mockito.verify(clientMapper).toDto(client);
    Assertions.assertEquals(ClientStatus.ACTIVATE, client.getInfo().getStatus());
  }

  @Test
  void testUpdateClientShouldReturnUpdatedClientDto() {
    //given
    final Long clientId = 1L;
    final UpdateClientDto updateClientDto = Mockito.mock(UpdateClientDto.class);
    final Client client = Mockito.mock(Client.class);
    final Client updatedClient = Mockito.mock(Client.class);
    final ClientDto clientDto = Mockito.mock(ClientDto.class);
    //when
    Mockito.when(clientRepository.findById(clientId)).thenReturn(Optional.of(client));
    Mockito.when(clientMapper.partialUpdate(updateClientDto, client)).thenReturn(updatedClient);
    Mockito.when(clientRepository.save(updatedClient)).thenReturn(updatedClient);
    Mockito.when(clientMapper.toDto(updatedClient)).thenReturn(clientDto);
    final ClientDto result = clientProvider.updateClient(clientId, updateClientDto);
    //then
    Assertions.assertEquals(clientDto, result);
    Mockito.verify(clientRepository).findById(clientId);
    Mockito.verify(clientMapper).partialUpdate(updateClientDto, client);
    Mockito.verify(clientRepository).save(updatedClient);
    Mockito.verify(clientMapper).toDto(updatedClient);
  }

  @Test
  void testUpdateClientShouldThrowClientNotFoundException() {
    //given
    final Long clientId = 1L;
    final UpdateClientDto updateClientDto = Mockito.mock(UpdateClientDto.class);
    //when
    Mockito.when(clientRepository.findById(clientId)).thenReturn(Optional.empty());
    final Executable executable = () -> clientProvider.updateClient(clientId, updateClientDto);
    //then
    Assertions.assertThrows(ClientNotFoundException.class, executable);
    Mockito.verify(clientRepository).findById(clientId);
  }

  @Test
  void testDeactivateClientShouldReturnDeactivatedClientDtoAndAllAccountsClose() {
    //given
    final Long clientId = 1L;
    final Client client = Mockito.spy(Client.class);
    final Account account = Mockito.spy(Account.class);
    final ClientDto clientDto = Mockito.mock(ClientDto.class);
    final List<Account> accounts = List.of(account);
    final ClientInfo clientInfo = Mockito.spy(ClientInfo.class);
    //when
    Mockito.when(client.getInfo()).thenReturn(clientInfo);
    Mockito.when(clientRepository.findById(clientId)).thenReturn(Optional.of(client));
    Mockito.when(client.getAccounts()).thenReturn(Set.copyOf(accounts));
    Mockito.when(clientRepository.save(client)).thenReturn(client);
    Mockito.when(clientMapper.toDto(client)).thenReturn(clientDto);
    final ClientDto result = clientProvider.deactivateClient(clientId);
    //then
    Assertions.assertEquals(clientDto, result);
    Mockito.verify(clientRepository).findById(clientId);
    Mockito.verify(clientRepository).save(client);
    Mockito.verify(clientMapper).toDto(client);
    Assertions.assertEquals(ClientStatus.DEACTIVATE, client.getInfo().getStatus());
    Assertions.assertEquals(AccountStatus.CLOSE, account.getStatus());
  }

  @Test
  void testGetClientByAccountIdShouldReturnClientDto() {
    //given
    final UUID accountId = UUID.randomUUID();
    final Account account = Mockito.mock(Account.class);
    final Client client = Mockito.mock(Client.class);
    final ClientDto clientDto = Mockito.mock(ClientDto.class);
    //when
    Mockito.when(accountRepository.findById(accountId)).thenReturn(Optional.of(account));
    Mockito.when(account.getClient()).thenReturn(client);
    Mockito.when(clientMapper.toDto(client)).thenReturn(clientDto);
    final ClientDto result = clientProvider.getClientByAccountId(accountId);
    //then
    Assertions.assertEquals(clientDto, result);
    Mockito.verify(accountRepository).findById(accountId);
    Mockito.verify(clientMapper).toDto(client);
  }

  @Test
  void testGetClientByAccountIdShouldThrowAccountNotFoundException() {
    //given
    final UUID accountId = UUID.randomUUID();
    //when
    Mockito.when(accountRepository.findById(accountId)).thenReturn(Optional.empty());
    final Executable executable = () -> clientProvider.getClientByAccountId(accountId);
    //then
    Assertions.assertThrows(AccountNotFoundException.class, executable);
    Mockito.verify(accountRepository).findById(accountId);
  }
}