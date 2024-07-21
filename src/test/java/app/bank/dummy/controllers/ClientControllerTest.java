package app.bank.dummy.controllers;

import app.bank.dummy.assemblers.AccountAssembler;
import app.bank.dummy.assemblers.ClientAssembler;
import app.bank.dummy.assemblers.TransactionAssembler;
import app.bank.dummy.dtos.ClientAccountDto;
import app.bank.dummy.dtos.ClientDto;
import app.bank.dummy.dtos.ClientTransactionDto;
import app.bank.dummy.dtos.NewAccountDto;
import app.bank.dummy.dtos.NewClientDto;
import app.bank.dummy.dtos.NewTransactionDto;
import app.bank.dummy.dtos.UpdateClientDto;
import app.bank.dummy.services.AccountService;
import app.bank.dummy.services.ClientService;
import app.bank.dummy.services.TransactionService;
import java.util.Arrays;
import java.util.Collection;
import java.util.UUID;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.function.Executable;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;

@ExtendWith(MockitoExtension.class)
class ClientControllerTest {

  @Mock
  private ClientService clientService;
  @Mock
  private ClientAssembler clientAssembler;
  @Mock
  private AccountService accountService;
  @Mock
  private AccountAssembler accountAssembler;
  @Mock
  private TransactionService transactionService;
  @Mock
  private TransactionAssembler transactionAssembler;
  @InjectMocks
  private ClientController clientController;

  @Test
  void getClients() {
    //given
    final ClientDto clientDto1 = Mockito.mock(ClientDto.class);
    final ClientDto clientDto2 = Mockito.mock(ClientDto.class);
    final Collection<ClientDto> clientDtos = Arrays.asList(clientDto1, clientDto2);
    final CollectionModel<EntityModel<ClientDto>> expectedCollectionModel = clientAssembler.toCollectionModel(clientDtos);
    //when
    Mockito.when(clientService.getClients()).thenReturn(clientDtos);
    Mockito.when(clientAssembler.toCollectionModel(clientDtos)).thenReturn(expectedCollectionModel);
    final CollectionModel<EntityModel<ClientDto>> result = clientController.getClients();
    //then
    Assertions.assertEquals(expectedCollectionModel, result);
  }

  @Test
  void createClient() {
    //given
    final NewClientDto newClientDto = Mockito.mock(NewClientDto.class);
    final ClientDto clientDto = Mockito.mock(ClientDto.class);
    final Link link = Link.of("/clients/".concat(UUID.randomUUID().toString()));
    final EntityModel<ClientDto> entityModel = EntityModel.of(clientDto, link);
    //when
    Mockito.when(clientService.createClient(newClientDto)).thenReturn(clientDto);
    Mockito.when(clientAssembler.toModel(clientDto)).thenReturn(entityModel);
    final Executable executable = () -> clientController.createClient(newClientDto);
    //then
    Assertions.assertDoesNotThrow(executable);

  }

  @Test
  void getClient() {
    //given
    final Long clientId = 1L;
    final ClientDto clientDto = Mockito.mock(ClientDto.class);
    final EntityModel<ClientDto> expectedEntityModel = EntityModel.of(clientDto);
    //when
    Mockito.when(clientService.getClient(clientId)).thenReturn(clientDto);
    Mockito.when(clientAssembler.toModel(clientDto)).thenReturn(expectedEntityModel);
    final EntityModel<ClientDto> result = clientController.getClient(clientId);
    //then
    Assertions.assertEquals(expectedEntityModel, result);
  }

  @Test
  void updateClient() {
    //given
    final Long clientId = 1L;
    final UpdateClientDto updateClientDto = Mockito.mock(UpdateClientDto.class);
    final ClientDto clientDto = Mockito.mock(ClientDto.class);
    final Link link = Link.of("/clients/".concat(UUID.randomUUID().toString()));
    final EntityModel<ClientDto> entityModel = EntityModel.of(clientDto, link);
    //when
    Mockito.when(clientService.updateClient(clientId, updateClientDto)).thenReturn(clientDto);
    Mockito.when(clientAssembler.toModel(clientDto)).thenReturn(entityModel);
    final Executable executable = () -> clientController.updateClient(clientId, updateClientDto);
    //then
    Assertions.assertDoesNotThrow(executable);
  }

  @Test
  void deleteClient() {
    //given
    final Long clientId = 1L;
    //when
    Mockito.doNothing().when(clientService).deactivateClient(clientId);
    clientController.deleteClient(clientId);
    //then
    Mockito.verify(clientService, Mockito.times(1)).deactivateClient(clientId);
  }

  @Test
  void getClientAccounts() {
    //given
    final Long clientId = 1L;
    final ClientAccountDto clientAccountDto1 = Mockito.mock(ClientAccountDto.class);
    final ClientAccountDto clientAccountDto2 = Mockito.mock(ClientAccountDto.class);
    final Collection<ClientAccountDto> clientAccountDtos = Arrays.asList(clientAccountDto1, clientAccountDto2);
    final CollectionModel<EntityModel<ClientAccountDto>> expectedCollectionModel = CollectionModel.empty();
    //when
    Mockito.when(accountService.getClientAccounts(clientId)).thenReturn(clientAccountDtos);
    Mockito.when(accountAssembler.toCollectionModel(clientAccountDtos, clientId)).thenReturn(expectedCollectionModel);
    final CollectionModel<EntityModel<ClientAccountDto>> result = clientController.getClientAccounts(clientId);
    //then
    Assertions.assertEquals(expectedCollectionModel, result);
  }

  @Test
  void createClientAccount() {
    //given
    final Long clientId = 1L;
    final NewAccountDto newAccountDto = Mockito.mock(NewAccountDto.class);
    final ClientAccountDto clientAccountDto = Mockito.mock(ClientAccountDto.class);
    final Link link = Link.of("/clients/".concat(UUID.randomUUID().toString()));
    final EntityModel<ClientAccountDto> expectedEntityModel = EntityModel.of(clientAccountDto, link);
    //when
    Mockito.when(accountService.createClientAccount(clientId, newAccountDto)).thenReturn(clientAccountDto);
    Mockito.when(accountAssembler.toModel(clientAccountDto, clientId)).thenReturn(expectedEntityModel);
    final Executable executable = () -> clientController.createClientAccount(clientId, newAccountDto);
    //then
    Assertions.assertDoesNotThrow(executable);
  }

  @Test
  void getClientAccount() {
    //given
    final Long clientId = 1L;
    final UUID accountId = UUID.randomUUID();
    final ClientAccountDto clientAccountDto = Mockito.mock(ClientAccountDto.class);
    final EntityModel<ClientAccountDto> expectedEntityModel = EntityModel.of(clientAccountDto);
    //when
    Mockito.when(accountService.getClientAccount(clientId, accountId)).thenReturn(clientAccountDto);
    Mockito.when(accountAssembler.toModel(clientAccountDto, clientId)).thenReturn(expectedEntityModel);
    final EntityModel<ClientAccountDto> result = clientController.getClientAccount(clientId, accountId);
    //then
    Assertions.assertEquals(expectedEntityModel, result);
  }

  @Test
  void closeClientAccount() {
    //given
    final Long clientId = 1L;
    final UUID accountId = UUID.randomUUID();
    final ClientAccountDto clientAccountDto = Mockito.mock(ClientAccountDto.class);
    final Link link = Link.of("/clients/".concat(UUID.randomUUID().toString()));
    final EntityModel<ClientAccountDto> expectedEntityModel = EntityModel.of(clientAccountDto, link);
    //when
    Mockito.when(accountService.closeClientAccount(clientId, accountId)).thenReturn(clientAccountDto);
    Mockito.when(accountAssembler.toModel(clientAccountDto, clientId)).thenReturn(expectedEntityModel);
    final Executable executable = () -> clientController.closeClientAccount(clientId, accountId);
    //then
    Assertions.assertDoesNotThrow(executable);
  }

  @Test
  void getClientAccountTransactions() {
    //given
    final Long clientId = 1L;
    final UUID accountId = UUID.randomUUID();
    final ClientTransactionDto clientTransactionDto1 = Mockito.mock(ClientTransactionDto.class);
    final ClientTransactionDto clientTransactionDto2 = Mockito.mock(ClientTransactionDto.class);
    final Collection<ClientTransactionDto> clientTransactionDtos = Arrays.asList(clientTransactionDto1, clientTransactionDto2);
    final CollectionModel<EntityModel<ClientTransactionDto>> expectedCollectionModel = transactionAssembler.toCollectionModel(clientTransactionDtos, clientId, accountId);
    //when
    Mockito.when(transactionService.getClientAccountTransactions(clientId, accountId)).thenReturn(clientTransactionDtos);
    Mockito.when(transactionAssembler.toCollectionModel(clientTransactionDtos, clientId, accountId)).thenReturn(expectedCollectionModel);
    final CollectionModel<EntityModel<ClientTransactionDto>> result = clientController.getClientAccountTransactions(clientId, accountId);
    //then
    Assertions.assertEquals(expectedCollectionModel, result);
  }

  @Test
  void createClientAccountTransaction() {
    //given
    final Long clientId = 1L;
    final UUID accountId = UUID.randomUUID();
    final NewTransactionDto newTransactionDto = Mockito.mock(NewTransactionDto.class);
    final ClientTransactionDto clientTransactionDto = Mockito.mock(ClientTransactionDto.class);
    final Link link = Link.of("/clients/".concat(UUID.randomUUID().toString()));
    final EntityModel<ClientTransactionDto> expectedEntityModel = EntityModel.of(clientTransactionDto, link);
    //when
    Mockito.when(transactionService.createClientAccountTransaction(clientId, accountId, newTransactionDto)).thenReturn(clientTransactionDto);
    Mockito.when(transactionAssembler.toModel(clientTransactionDto, clientId, accountId)).thenReturn(expectedEntityModel);
    final Executable executable = () -> clientController.createClientAccountTransaction(clientId, accountId, newTransactionDto);
    //then
    Assertions.assertDoesNotThrow(executable);

  }

  @Test
  void getClientAccountTransaction() {
    //given
    final Long clientId = 1L;
    final UUID accountId = UUID.randomUUID();
    final UUID transactionId = UUID.randomUUID();
    final ClientTransactionDto clientTransactionDto = Mockito.mock(ClientTransactionDto.class);
    final EntityModel<ClientTransactionDto> expectedEntityModel = EntityModel.of(clientTransactionDto);
    //when
    Mockito.when(transactionService.getClientAccountTransaction(clientId, accountId, transactionId)).thenReturn(clientTransactionDto);
    Mockito.when(transactionAssembler.toModel(clientTransactionDto, clientId, accountId)).thenReturn(expectedEntityModel);
    final EntityModel<ClientTransactionDto> result = clientController.getClientAccountTransaction(clientId, accountId, transactionId);
    //then
    Assertions.assertEquals(expectedEntityModel, result);
  }
}